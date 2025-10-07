package com.example.soho_bank.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String email;
    private String token;
    private Long userId;
}
