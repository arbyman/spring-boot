package com.bardelorean.crud.controller;

import com.bardelorean.crud.model.User;
import com.bardelorean.crud.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

	private final UserService userService;

	public AdminRestController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "get/{id}")
	public User getUser(@PathVariable("id") long id) {
		return userService.findById(id);
	}
}