package com.bardelorean.crud.controller;

import com.bardelorean.crud.model.User;
import com.bardelorean.crud.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
	private final UserService userService;


	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/user/{id}")
	@PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
	public String getUser(@PathVariable("id") long id, Model model) {
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return "user";
	}

	@GetMapping
	public String index() {
		return "redirect:/login";
	}

	@GetMapping(value = "/login")
	public String login() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null
				&& authentication.isAuthenticated()
				&& !(authentication instanceof AnonymousAuthenticationToken)) {
			User user = (User) authentication.getPrincipal();
			long id = user.getId();
			return "redirect:/user/" + id;
		}
		return "login";
	}
}
