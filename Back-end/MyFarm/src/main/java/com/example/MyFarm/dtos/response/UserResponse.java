package com.example.MyFarm.dtos.response;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long userId;
    String firstName;
    String lastName;
    LocalDate dob;
    String username;
    String email;
    String phoneNumber;
    Set<String> roles;
}
