package com.bardelorean.crud.controller;

import com.bardelorean.crud.model.User;
import com.bardelorean.crud.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

	private final UserService userService;

	public AdminRestController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/users/{id}")
	public User getUser(@PathVariable("id") long id) {
		return userService.findById(id);
	}

	@GetMapping(value = "/users")
	public List<User> getAllUsers() {
		return userService.findAll();
	}

	@PostMapping(value = "/users")
	public User addUser(@ModelAttribute User user) {
		return userService.save(user);
	}

	@PutMapping(value = "/users/{id}")
	public User updateUser(@ModelAttribute User user, @PathVariable("id") long id) {
		return userService.update(user, id);
	}

	@DeleteMapping(value = "/users/{id}")
	public void deleteUser(@PathVariable("id") long id) {
		userService.deleteById(id);
	}
}