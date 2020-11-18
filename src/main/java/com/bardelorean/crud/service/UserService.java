package com.bardelorean.crud.service;

import com.bardelorean.crud.model.Role;
import com.bardelorean.crud.model.User;
import com.bardelorean.crud.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User findById(long id) {
		return userRepository
				.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException(String
						.format("No user found with id '%d'.", id)));
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public void deleteById(long id) {
		userRepository.deleteById(id);
	}

	public void save(User user) {
		user.setRoles(getRoles(user.getIsAdmin()));
		userRepository.save(user);
	}

	private Set<Role> getRoles(String admin) {
		Set<Role> roles = new HashSet<>();
		if (admin != null) {
			roles.add(new Role("ROLE_ADMIN"));
		}
		roles.add(new Role("ROLE_USER"));
		return roles;
	}
}
