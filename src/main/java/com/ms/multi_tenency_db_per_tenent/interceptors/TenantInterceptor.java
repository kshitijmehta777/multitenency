package com.ms.multi_tenency_db_per_tenent.interceptors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.multi_tenency_db_per_tenent.config.MultitenantConfiguration;
import com.ms.multi_tenency_db_per_tenent.config.TenantContext;
import com.ms.multi_tenency_db_per_tenent.config.UserDetailsImpl;
import com.ms.multi_tenency_db_per_tenent.entity.Companies;
import com.ms.multi_tenency_db_per_tenent.enums.CompanyRoles;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    private MultitenantConfiguration configuration;
    public TenantInterceptor(MultitenantConfiguration configuration) {
    	this.configuration = configuration;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("pre handle");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Optional<String> isAdmin = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst();
            if(isAdmin.isPresent() && isAdmin.get().equals(CompanyRoles.ROLE_ADMIN.name())){
                TenantContext.setCurrentTenant("default");
            } else {
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                Companies companies = userDetails.getCompanies();
                configuration.addDataSource(companies);
                TenantContext.setCurrentTenant(companies.getDbName());
            }
        } else {
            TenantContext.setCurrentTenant("default");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TenantContext.setCurrentTenant("default");
    }
}
