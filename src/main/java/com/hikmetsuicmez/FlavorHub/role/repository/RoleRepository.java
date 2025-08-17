package com.hikmetsuicmez.FlavorHub.role.repository;

import com.hikmetsuicmez.FlavorHub.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String name);
}
