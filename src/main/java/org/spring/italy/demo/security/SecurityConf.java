package org.spring.italy.demo.security;

import org.spring.italy.demo.serv.UserServ;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConf {

	@Bean
	public SecurityFilterChain getFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests()
				.requestMatchers(HttpMethod.GET, "/pizza/*").hasAuthority("USER")
//				.requestMatchers(HttpMethod.POST, "/user", "/user/**").hasAuthority("USER")
				.requestMatchers("/pizze/**").hasAuthority("ADMIN")
				.requestMatchers("/").hasAnyAuthority("USER", "ADMIN")	
//				.requestMatchers("/**").permitAll()
			.and().formLogin()
			.and().logout()
		;

		return http.build();
	}
	
	@Bean
	public UserDetailsService getuseDetailsService() {
		
		return new UserServ();
	}
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider getAuthProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setUserDetailsService(getuseDetailsService());
		provider.setPasswordEncoder(getPasswordEncoder());
		
		return provider;
	}
}	
