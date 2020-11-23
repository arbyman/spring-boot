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
	public String showAllUsers(@ModelAttribute("user") User user, Model model, Principal principal) {
		User currentUser = userService.findByUsername(principal.getName());
		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		model.addAttribute("current", currentUser);
		return "index";
	}

	@GetMapping(value = "/show/{id}")
	public String showUser(@PathVariable("id") long id, Model model) {
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return "show";
	}

	@DeleteMapping(value = "/delete/{id}")
	public String deleteUserById(@PathVariable("id") long id) {
		userService.deleteById(id);
		return "redirect:/admin";
	}

	@GetMapping(value = "/new")
	public String add(@ModelAttribute("user") User user) {
		return "new";
	}

	@PostMapping(value = "/new")
	public String addUser(@ModelAttribute("user") User user) {
		System.out.println(user.getRoles());
		userService.save(user);
		return "redirect:/admin";
	}

	@GetMapping(value = "/edit/{id}")
	public String editUser(@PathVariable("id") long id, Model model) {
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return "edit";
	}

	@PatchMapping(value = "/edit/{id}")
	public String updateUser(@PathVariable("id") long id, @ModelAttribute("user") User user) {
		System.out.println("Получен юзер");
		System.out.println(user);
		userService.update(user, id);
		return "redirect:/admin";
	}
}