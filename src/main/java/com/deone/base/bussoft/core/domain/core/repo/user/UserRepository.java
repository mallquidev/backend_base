package com.deone.base.bussoft.core.domain.core.repo.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deone.base.bussoft.core.domain.core.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    
    boolean existsByDni(Integer dni);
    Optional<User> findByDni(Integer dni);

}
