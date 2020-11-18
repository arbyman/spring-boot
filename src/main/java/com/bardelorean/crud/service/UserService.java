package com.bardelorean.crud.service;

import com.bardelorean.crud.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

	User findByUsername(String username);
	User findById(long id);
	List<User> findAll();
	void deleteById(long id);
	void save(User user);

}
