package com.bardelorean.crud.service;

import com.bardelorean.crud.model.Role;
import com.bardelorean.crud.model.User;
import com.bardelorean.crud.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
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
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	@Override
	public void update(User user, long id) {
		System.out.println("Передан юзер");
		System.out.println(user);
		System.out.println(user.getId());
		User oldUser = findById(id);
		oldUser.setUsername(user.getUsername());
		oldUser.setLastname(user.getLastname());
		oldUser.setAge(user.getAge());
		oldUser.setRoles(user.getRoles());
		oldUser.setEmail(user.getEmail());
		oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
		save(oldUser);
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
