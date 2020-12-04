package com.bardelorean.crud.repository;

import com.bardelorean.crud.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByRole(String role);
}
