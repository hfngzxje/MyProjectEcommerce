package com.example.MyFarm.dtos.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePasswordRequest {
    Long id;
    String oldPassword;
    String newPassword;
    String reNewPassword;
}
