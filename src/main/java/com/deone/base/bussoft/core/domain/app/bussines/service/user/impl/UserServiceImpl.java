package com.deone.base.bussoft.core.domain.app.bussines.service.user.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.deone.base.bussoft.core.domain.app.bussines.service.user.UserService;
import com.deone.base.bussoft.core.domain.core.entities.RoleUser;
import com.deone.base.bussoft.core.domain.core.entities.User;
import com.deone.base.bussoft.core.domain.core.repo.role_user.RoleUserRepository;
import com.deone.base.bussoft.core.domain.core.repo.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //Solo campos final o @NonNull sin inicializar
public class UserServiceImpl implements UserService{
    
    private final UserRepository userRepository;
    private final RoleUserRepository roleUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

            //Cargar roles
            List<RoleUser> roles = roleUserRepository.findAllByUserAndIsDeletedFalse(user);

            //mapear roles a authorites
            List<GrantedAuthority> authorities = roles.stream()
                .map(ru -> new SimpleGrantedAuthority("ROLE_"+ru.getRole().getName().name()))
                .collect(Collectors.toList());

            //devolver un UserDetails que spring security pueda usar
            return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();

    }

    
}
