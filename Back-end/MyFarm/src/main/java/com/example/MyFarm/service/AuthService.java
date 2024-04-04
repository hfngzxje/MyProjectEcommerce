package com.example.MyFarm.service;

import com.example.MyFarm.dtos.Request.*;
import com.example.MyFarm.dtos.response.AuthResponse;
import com.example.MyFarm.dtos.response.IntrospectResponse;
import com.example.MyFarm.dtos.response.RegisterResponse;
import com.example.MyFarm.entities.User;
import com.example.MyFarm.enums.ErrorCode;
import com.example.MyFarm.exception.AppException;
import com.example.MyFarm.mappers.UserMapper;
import com.example.MyFarm.repository.UserRepository;
import com.example.MyFarm.service.IService.IAuthService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService implements IAuthService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JavaMailSender emailSender;
    @NonFinal
    @org.springframework.beans.factory.annotation.Value("${signer.key}")
    protected String SIGNER_KEY;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    //valid token
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verified && expityTime.after(new Date()))
                .build();
    }

    //login
    private Map<String, Integer> failedLoginAttemptsMap = new HashMap<>();

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var user = userRepository.findByUsername(request.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if(user.isActive()) {
            boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
            int maxAttempts = 4;
            if (!authenticated) {
                int currentFailedAttempts = failedLoginAttemptsMap.getOrDefault(user.getUsername(), 0);
                currentFailedAttempts++;
                failedLoginAttemptsMap.put(user.getUsername(), currentFailedAttempts);
                if (currentFailedAttempts < maxAttempts) {
                    throw new AppException(ErrorCode.INVALID_LOGINRQ);
                } else {
                    user.setActive(false);
                    userRepository.save(user);
                    throw new AppException(ErrorCode.INVALID_ACCBAN);
                }
            } else {
                failedLoginAttemptsMap.remove(user.getUsername());
            }
            var token = generateToken(user);
            return AuthResponse.builder()
                    .token(token)
                    .authenticated(true)
                    .build();
        }else {
            throw new AppException(ErrorCode.INVALID_ACCBAN);
        }
    }

    //register
    @Override
    public RegisterResponse register(RegisterRequest request) {
        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles("CUSTOMER");
        user.setActive(true);
        User savedUser = userRepository.save(user);
        RegisterResponse response = new RegisterResponse();
        BeanUtils.copyProperties(savedUser, response);
        return response;
    }

    @Override
    public String changePassword(ChangePasswordRequest request) {
        var user = userRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new AppException(ErrorCode.INVALID_OLDPASSWORD);
        }else {
            if(!request.getNewPassword().equalsIgnoreCase(request.getReNewPassword())){
                throw new AppException(ErrorCode.NOTMATCH_NEWPASSWORD);
            }else {
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                userRepository.save(user);
            }
        }
        return "Change-password successfully!";
    }

    //gen token
    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .claim("id", user.getUserId())
                .subject(user.getUsername())
                .claim("scope", user.getRoles())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String sendCode(String email) {
        var user = userRepository.findByEmail(email);
        if(user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        String verificationCode = generateCode(8);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Verification Code");
        message.setText("Your verification code is: " + verificationCode +"     Warning : The code lasts for 3 minutes!!");

        user.setCode(verificationCode);
        userRepository.save(user);

        emailSender.send(message);
        return "Send code successfully!";
    }

    @Override
    public String confirmOTP(ConfirmOTPRequest request) {
        var user = userRepository.findByEmail(request.getEmail());
        if(!user.getCode().equalsIgnoreCase(request.getOtp())){

            throw new AppException(ErrorCode.NOTMATCH_OTP);
        }

            if(!request.getNewPassword().equalsIgnoreCase(request.getReNewPassword())){
                throw new AppException(ErrorCode.NOTMATCH_NEWPASSWORD);
            }else {
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                user.setCode("");
                userRepository.save(user);
            }

        return "Change-password successfully!";
    }

    public static String generateCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }



}

