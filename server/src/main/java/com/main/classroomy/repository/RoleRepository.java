package com.main.classroomy.repository;

import com.main.classroomy.entity.role.ERole;
import com.main.classroomy.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole roleUser);

    boolean existsByName(ERole eRole);
}
