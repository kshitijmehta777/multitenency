package com.ms.multi_tenency_db_per_tenent.service;

import com.ms.multi_tenency_db_per_tenent.config.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ms.multi_tenency_db_per_tenent.dao.CompaniesDAO;
import com.ms.multi_tenency_db_per_tenent.entity.Companies;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private CompaniesDAO companiesDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Companies companies = companiesDAO.findByUserLoginName(username).stream().findFirst()
				.orElseThrow(() -> new UsernameNotFoundException("user not found"));
		return new UserDetailsImpl(companies);
	}
}
