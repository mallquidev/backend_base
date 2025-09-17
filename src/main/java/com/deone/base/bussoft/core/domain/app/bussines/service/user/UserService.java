package com.deone.base.bussoft.core.domain.app.bussines.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{

    @Override
    UserDetails loadUserByUsername(String email);

} 
