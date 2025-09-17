package com.deone.base.bussoft.core.domain.core.dto.response.user;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Long userId;
    private String email;
    private Integer dni;
    private List<String> roles;
}
