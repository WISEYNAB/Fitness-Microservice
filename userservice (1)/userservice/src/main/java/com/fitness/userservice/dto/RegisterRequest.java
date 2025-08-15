package com.fitness.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "mail cant be empty")
    @Email(message = "enter a valid email format")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 6,message = "password should be atleast 6 characters")
    private String password;
    private String firstname;
    private String lastname;


}
