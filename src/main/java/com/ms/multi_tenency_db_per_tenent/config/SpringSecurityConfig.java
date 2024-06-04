package com.ms.multi_tenency_db_per_tenent.config;

import com.ms.multi_tenency_db_per_tenent.interceptors.JwtAuthFilter;
import com.ms.multi_tenency_db_per_tenent.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(h -> h.disable()).cors(c -> c.disable()).authorizeHttpRequests(req -> req
				.requestMatchers("/token/generate").permitAll()
				.requestMatchers("/company/**").hasAnyRole("ADMIN").requestMatchers("/employee/**").hasAnyRole("ADMIN", "COMPANY").anyRequest().authenticated())
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class);
		//		.httpBasic(Customizer.withDefaults());
        return http.build();
    }

	/*
	For Basic Authentication
    @Autowired
    void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    }

	 */


	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;

	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
