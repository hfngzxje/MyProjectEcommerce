package com.example.MyFarm.mappers;

import com.example.MyFarm.dtos.Request.RegisterRequest;
import com.example.MyFarm.dtos.response.RegisterResponse;
import com.example.MyFarm.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegisterRequest request);
    RegisterResponse toUserResponse(User user);
}
