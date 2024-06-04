package com.ms.multi_tenency_db_per_tenent.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(h -> h.disable()).cors(c -> c.disable())
				.authorizeHttpRequests(req -> req
						.requestMatchers("/company/**").hasAnyRole("ADMIN")
						.requestMatchers("/employee/**").hasAnyRole("ADMIN","COMPANY")
						.anyRequest().authenticated()).httpBasic(Customizer.withDefaults());
		return http.build();
	}
	
	@Autowired
	void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
	}
}
