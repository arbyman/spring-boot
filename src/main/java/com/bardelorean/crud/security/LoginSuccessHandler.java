package com.bardelorean.crud.security;

import com.bardelorean.crud.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
										HttpServletResponse httpServletResponse,
										Authentication authentication) throws IOException {
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		if (roles.contains("ROLE_ADMIN")) {
			httpServletResponse.sendRedirect("/admin");
		} else if (roles.contains("ROLE_USER")) {
			User user = (User) authentication.getPrincipal();
			Long id = user.getId();
			httpServletResponse.sendRedirect("/user/" + id);
		}
	}
}
