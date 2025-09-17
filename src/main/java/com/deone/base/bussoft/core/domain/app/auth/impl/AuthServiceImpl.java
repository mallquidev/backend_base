package com.deone.base.bussoft.core.domain.app.auth.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deone.base.bussoft.core.commons.exception.Conflict;
import com.deone.base.bussoft.core.commons.exception.EmailAlreadyExistsException;
import com.deone.base.bussoft.core.commons.exception.ErrorCode;
import com.deone.base.bussoft.core.commons.exception.NotFound;
import com.deone.base.bussoft.core.commons.libs.auth.jwt.JwtUtil;
import com.deone.base.bussoft.core.commons.models.enums.RoleEnum;
import com.deone.base.bussoft.core.domain.app.auth.AuthService;
import com.deone.base.bussoft.core.domain.core.dto.request.user.LoginRequest;
import com.deone.base.bussoft.core.domain.core.dto.request.user.RegisterUserRequest;
import com.deone.base.bussoft.core.domain.core.dto.response.user.AuthResponse;
import com.deone.base.bussoft.core.domain.core.entities.Role;
import com.deone.base.bussoft.core.domain.core.entities.RoleUser;
import com.deone.base.bussoft.core.domain.core.entities.User;
import com.deone.base.bussoft.core.domain.core.repo.role.RoleRepository;
import com.deone.base.bussoft.core.domain.core.repo.role_user.RoleUserRepository;
import com.deone.base.bussoft.core.domain.core.repo.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleUserRepository roleUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse register(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        //Buscamos rol USER
        Role userRole = roleRepository.findByName(RoleEnum.USER)
            .orElseThrow(()->new Conflict("Rol USER no encontrado"));

        User user = new User();
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setUserName(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(hashedPassword);
        user.setDni(request.getDni());
        user.setStatus(1);
        user.setCreateAt(LocalDateTime.now());
        //guardamos el usuario
        User savedUser = userRepository.save(user);

        //Asignar rol
        RoleUser roleUser = new RoleUser();
        roleUser.setUser(savedUser);
        roleUser.setRole(userRole);
        roleUser.setIsDeleted(false);
        roleUserRepository.save(roleUser);

        //Autenticar automaticamente
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = jwtUtil.generateToken(authentication);

        List<String> roles = List.of(userRole.getName().name());

        return new AuthResponse(
            token, 
            savedUser.getUserId(), 
            savedUser.getEmail(), 
            savedUser.getDni(),
            roles
        );

    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        //buscar al usuario en la bd
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(()-> new NotFound(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));

        //Obtener TODO los roles
        List<String> roles = roleUserRepository.findAllByUserAndIsDeletedFalse(user)
            .stream()
            .map(ru -> ru.getRole().getName().name())
            .toList();
        
        if (roles.isEmpty()) {
            throw new Conflict("Usuario sin roles asignado");
        }

        String token = jwtUtil.generateToken(authentication);

        return new AuthResponse(
            token,
            user.getUserId(),
            user.getEmail(),
            user.getDni(),
            roles    
        );
        

    }

}
