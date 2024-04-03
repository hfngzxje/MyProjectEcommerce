package com.example.MyFarm.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "\"User\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    Long userId;

    @Column(name = "FirstName")
    String firstName;

    @Column(name = "LastName")
    String lastName;

    @Column(name = "Dob")
    LocalDate dob;

    @Column(name = "Username")
    String username;

    @Column(name = "Email")
    String email;

    @Column(name = "Phonenumber")
    String phoneNumber;

    @Column(name = "Password")
    String password;

    @Column(name = "Role")
    String roles;

    @Column(name = "Code")
    String code;

}
