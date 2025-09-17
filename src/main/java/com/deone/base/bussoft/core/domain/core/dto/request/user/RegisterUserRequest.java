package com.deone.base.bussoft.core.domain.core.dto.request.user;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String username;
    private String email;
    private Integer dni;
    private String password;
}
