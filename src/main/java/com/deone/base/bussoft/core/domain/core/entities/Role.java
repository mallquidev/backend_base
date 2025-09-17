package com.deone.base.bussoft.core.domain.core.entities;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.deone.base.bussoft.core.commons.models.enums.RoleEnum;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum name;

    private String description;

    @Column(name = "delete_at")
    private LocalDateTime deletedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<RoleUser> roleUsers = new HashSet<>();
}