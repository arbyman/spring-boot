package com.bardelorean.crud.controller;

import com.bardelorean.crud.model.Role;
import com.bardelorean.crud.model.User;
import com.bardelorean.crud.service.RoleService;
import com.bardelorean.crud.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {
	private final UserService userService;
	private final RoleService roleService;

	public UserController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping("/user")
	public String getUser(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("current", user);
		return "user";
	}

//	@GetMapping("/init")
//	public String initUsers() {
//		Role adminRole = new Role("ROLE_ADMIN");
//		Role userRole = new Role("ROLE_USER");
//		roleService.save(adminRole);
//		roleService.save(userRole);
//		Set<Role> roles = new HashSet<>();
//		roles.add(roleService.findByRole("ROLE_ADMIN"));
//		roles.add(roleService.findByRole("ROLE_USER"));
//		User admin = new User("admin", "adminov", "admin@mail.ru", (byte) 33, "pass", roles);
//		userService.save(admin);
//		return "redirect:/";
//	}

	@GetMapping("/")
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
			return "redirect:/user";
		}
		return "login";
	}
}
