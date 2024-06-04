package com.ms.multi_tenency_db_per_tenent.config;

import java.util.Collection;
import java.util.List;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ms.multi_tenency_db_per_tenent.entity.Companies;

@Getter
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private Companies companies;

	public UserDetailsImpl(Companies companies) {
		this.companies = companies;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(companies.getCompanyRoles().toString()));
	}

	@Override
	public String getPassword() {
		return companies.getUserPassword();
	}

	@Override
	public String getUsername() {
		return companies.getUserLoginName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
