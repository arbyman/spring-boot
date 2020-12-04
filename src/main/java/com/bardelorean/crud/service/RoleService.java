package com.bardelorean.crud.service;

import com.bardelorean.crud.model.Role;

public interface RoleService  {
	void save(Role role);
	Role findByRole(String role);
}

