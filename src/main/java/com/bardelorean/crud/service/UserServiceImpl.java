package com.bardelorean.crud.service;

import com.bardelorean.crud.model.User;
import com.bardelorean.crud.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User update(User user, long id) {
		User oldUser = findById(id);
		oldUser.setUsername(user.getUsername());
		oldUser.setLastname(user.getLastname());
		oldUser.setAge(user.getAge());
		oldUser.setRoles(user.getRoles());
		oldUser.setEmail(user.getEmail());
		oldUser.setPassword(user.getPassword());
		return save(oldUser);
	}
}
