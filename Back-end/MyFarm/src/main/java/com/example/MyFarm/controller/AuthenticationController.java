package com.example.MyFarm.controller;

import com.example.MyFarm.dtos.Request.*;
import com.example.MyFarm.dtos.response.ApiResponse;
import com.example.MyFarm.dtos.response.AuthResponse;
import com.example.MyFarm.dtos.response.IntrospectResponse;
import com.example.MyFarm.dtos.response.RegisterResponse;
import com.example.MyFarm.service.AuthService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//@SecurityRequirement(name = "Authorization")
public class AuthenticationController {
    AuthService authService;
    @PostMapping("/login")
    ApiResponse<AuthResponse> login(@RequestBody AuthRequest request){
        var result = authService.authenticate(request);
        return ApiResponse.<AuthResponse>builder()
                .massage("Login Successfully!")
                .result(result).build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> login(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result).build();
    }

    @PostMapping("/register")
    ApiResponse<RegisterResponse> register(@RequestBody @Valid RegisterRequest request){
                return ApiResponse.<RegisterResponse>builder()
                        .result(authService.register(request))
                        .massage("Register successfully!")
                        .build();
    }

    @PostMapping("/change-password")
    ApiResponse<String> changePassword(@RequestBody ChangePasswordRequest request){
        return ApiResponse.<String>builder()
                .result(authService.changePassword(request))
                .build();
    }

    @PostMapping("send-code")
    ApiResponse<String> sendCode(@RequestParam String email){
        return ApiResponse.<String>builder()
                .result(authService.sendCode(email))
                .build();
    }

    @PostMapping("confirm-otp-change-password")
    ApiResponse<String> confirmOTP(@RequestBody ConfirmOTPRequest request){
        return ApiResponse.<String>builder()
                .result(authService.confirmOTP(request))
                .build();
    }
}
