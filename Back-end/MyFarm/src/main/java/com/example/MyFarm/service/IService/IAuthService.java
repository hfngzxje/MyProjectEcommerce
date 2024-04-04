package com.example.MyFarm.service.IService;

import com.example.MyFarm.dtos.Request.AuthRequest;
import com.example.MyFarm.dtos.Request.ChangePasswordRequest;
import com.example.MyFarm.dtos.Request.ConfirmOTPRequest;
import com.example.MyFarm.dtos.Request.RegisterRequest;
import com.example.MyFarm.dtos.response.AuthResponse;
import com.example.MyFarm.dtos.response.RegisterResponse;

public interface IAuthService {
    public AuthResponse authenticate(AuthRequest request);
    public RegisterResponse register(RegisterRequest request);
    String changePassword(ChangePasswordRequest request);
    String sendCode(String email);

    String confirmOTP(ConfirmOTPRequest request);
}
