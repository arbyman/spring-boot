package com.bardelorean.crud.controller;

import com.bardelorean.crud.model.User;
import com.bardelorean.crud.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

	final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/user/{id}")
	@PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
	public String getUser(@PathVariable("id") long id, Model model) {
		User user = userRepository.findById(id).orElseThrow();
		model.addAttribute("user", user);
		return "user";
	}

	@GetMapping
	public String index() {
//		Role roleAdmin = new Role("ROLE_ADMIN");
//		Role roleUser = new Role("ROLE_USER");
//		Set<Role> roles1 = new HashSet<>();
//		Set<Role> roles2 = new HashSet<>();
//		roles1.add(roleUser);
//		roles2.add(roleUser);
//		roles2.add(roleAdmin);
//		User user1 = new User("Mike", (byte) 23, "pass", roles1);
//		User user2 = new User("Jack", (byte) 28, "pass", roles2);
//		userRepository.save(user1);
//		userRepository.save(user2);
//		System.out.println("Добавлены Юзеры");
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
