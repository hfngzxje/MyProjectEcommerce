package com.example.MyFarm.service.IService;

import com.example.MyFarm.dtos.Request.AuthRequest;
import com.example.MyFarm.dtos.response.AuthResponse;

public interface IAuthService {
    public AuthResponse authenticate(AuthRequest request);
}
