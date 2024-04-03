package com.example.MyFarm.controller;

import com.example.MyFarm.dtos.Request.AuthRequest;
import com.example.MyFarm.dtos.Request.IntrospectRequest;
import com.example.MyFarm.dtos.response.ApiResponse;
import com.example.MyFarm.dtos.response.AuthResponse;
import com.example.MyFarm.dtos.response.IntrospectResponse;
import com.example.MyFarm.service.AuthService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
                .result(result).build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> login(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result).build();
    }
}
