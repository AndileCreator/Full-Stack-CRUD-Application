package com.example.demo.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "Full name is required!")
        @Size(max = 50 , message = "Full name must not exceed 50 characters")
        String userName,

        @NotBlank(message = "Email is required!")
        @Email(message = "Enter valid email.")
        @Size(max = 50, message = "Email must not exceed 50 characters")
        String email
) {
}
