package com.bardelorean.crud.service;

import com.bardelorean.crud.model.Role;
import com.bardelorean.crud.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
	private final RoleRepository roleRepository;

	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public void save(Role role) {
		roleRepository.save(role);
	}

	@Override
	public Role findByRole(String role) {
		return roleRepository.findByRole(role);
	}
}
