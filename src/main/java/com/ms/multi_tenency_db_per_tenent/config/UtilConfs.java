package com.ms.multi_tenency_db_per_tenent.config;

import com.ms.multi_tenency_db_per_tenent.entity.Companies;
import com.ms.multi_tenency_db_per_tenent.model.CompaniesDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UtilConfs {

    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(Companies.class, CompaniesDTO.class).addMappings(mappings -> {
            mappings.map(src -> src.getCompanyName(), CompaniesDTO::setCompanyName);
            mappings.map(src -> src.getUserLoginName(), CompaniesDTO::setLoginName);
        });
        return modelMapper;
    }
    
    @Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
