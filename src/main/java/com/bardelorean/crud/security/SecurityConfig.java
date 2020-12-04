package com.bardelorean.crud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;
	private final LoginSuccessHandler loginSuccessHandler;
	private final PasswordEncoder passwordEncoder;

	public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, LoginSuccessHandler loginSuccessHandler, PasswordEncoder passwordEncoder) {
		this.userDetailsService = userDetailsService;
		this.loginSuccessHandler = loginSuccessHandler;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.csrf()
				.disable()
				.authorizeRequests()
					.antMatchers("/resources/**").permitAll()
					.antMatchers("/bootstrap/**").permitAll()
					.antMatchers("/popper/**").permitAll()
					.antMatchers("/jquery/**").permitAll()
//					.antMatchers("/init").permitAll()
					.antMatchers("/admin/**").access("hasRole('ADMIN')")
					.antMatchers("/user/**").access("hasAnyRole('ADMIN', 'USER')")
					.anyRequest().authenticated()
				.and()
					.formLogin()
					.loginPage("/login")
					.permitAll()
					.usernameParameter("j_email")
					.passwordParameter("j_password")
					.successHandler(loginSuccessHandler)
				.and()
					.logout()
					.permitAll()
					.logoutSuccessUrl("/");
	}

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

}