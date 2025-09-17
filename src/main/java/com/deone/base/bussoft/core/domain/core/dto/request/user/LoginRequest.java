package com.deone.base.bussoft.core.domain.core.dto.request.user;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
