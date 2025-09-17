package com.deone.base.bussoft.core.domain.core.repo.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deone.base.bussoft.core.commons.models.enums.RoleEnum;
import com.deone.base.bussoft.core.domain.core.entities.Role;


@Repository// Indica a Spring que esta interfaz es un componente de acceso a datos
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Optional<Role> findByName(RoleEnum name);
    boolean existsByName(Role name);
}
