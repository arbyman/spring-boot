package com.bardelorean.crud.service;

import com.bardelorean.crud.model.User;

import java.util.List;

public interface UserService {

	User findByEmail(String email);
	User findByUsername(String username);
	User findById(long id);
	List<User> findAll();
	void deleteById(long id);
	User save(User user);
	User update(User user, long id);
}
