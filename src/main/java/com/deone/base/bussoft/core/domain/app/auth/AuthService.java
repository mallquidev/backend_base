package com.deone.base.bussoft.core.domain.app.auth;

import com.deone.base.bussoft.core.domain.core.dto.request.user.LoginRequest;
import com.deone.base.bussoft.core.domain.core.dto.request.user.RegisterUserRequest;
import com.deone.base.bussoft.core.domain.core.dto.response.user.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterUserRequest request);
    AuthResponse login(LoginRequest request);
}
