package com.example.admin.domain.dto;

import lombok.Data;

@Data
public class UserView {
    private int userId;
    private String username;
    private String email;
    private String fullname;
}
