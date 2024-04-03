package com.example.MyFarm;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SecurityScheme(
		name = "Authorization",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
@OpenAPIDefinition(info = @Info(title = "Library Apis",version = "V.1.0",description = "Documentation for Library Management apis"))
@SpringBootApplication
public class MyFarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyFarmApplication.class, args);
	}

}