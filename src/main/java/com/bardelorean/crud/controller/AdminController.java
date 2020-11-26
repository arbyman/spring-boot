package com.bardelorean.crud.controller;

import com.bardelorean.crud.model.User;
import com.bardelorean.crud.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final UserService userService;

	public AdminController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String admin(@ModelAttribute("user") User user, Model model, Principal principal) {
		User currentUser = userService.findByUsername(principal.getName());
		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		model.addAttribute("current", currentUser);
		return "index";
	}
}