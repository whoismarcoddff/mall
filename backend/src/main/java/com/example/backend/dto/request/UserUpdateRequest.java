package com.example.backend.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    @NotBlank
    private String email;
    private String username;
    private String password;
    private String role;
}
