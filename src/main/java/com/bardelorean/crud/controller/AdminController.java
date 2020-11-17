package com.bardelorean.crud.controller;

import com.bardelorean.crud.model.Role;
import com.bardelorean.crud.model.User;
import com.bardelorean.crud.service.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final UserRepository userRepository;

	public AdminController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping
	public String showAllUsers(Model model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		return "index";
	}

	@GetMapping(value = "/show/{id}")
	public String showUser(@PathVariable("id") long id, Model model) {
		User user = userRepository.findById(id).orElseThrow();
		model.addAttribute("user", user);
		return "show";
	}

	@DeleteMapping(value = "/delete/{id}")
	public String deleteUserById(@PathVariable long id) {
		userRepository.deleteById(id);
		return "redirect:/admin";
	}

	@GetMapping(value = "/new")
	public String add(@ModelAttribute("user") User user) {
		return "new";
	}

	@PostMapping(value = "/new")
	public String addUser(@ModelAttribute("user") User user) {
		user.setRoles(getRoles(user.getIsAdmin()));
		userRepository.save(user);
		return "redirect:/admin";
	}

	@GetMapping(value = "/edit/{id}")
	public String editUser(@PathVariable("id") long id, Model model) {
		User user = userRepository.findById(id).orElseThrow();
		model.addAttribute("user", user);
		return "edit";
	}

	@PatchMapping(value = "/edit/{id}")
	public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") long id) {
		user.setRoles(getRoles(user.getIsAdmin()));
		userRepository.save(user);
		return "redirect:/admin";
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