package com.ms.multi_tenency_db_per_tenent.api;

import java.util.List;

import com.ms.multi_tenency_db_per_tenent.enums.CompanyRoles;
import com.ms.multi_tenency_db_per_tenent.model.CompaniesDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ms.multi_tenency_db_per_tenent.dao.CompaniesDAO;
import com.ms.multi_tenency_db_per_tenent.entity.Companies;

@RestController
@RequestMapping("/company")
public class CompanyApis {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CompaniesDAO companiesDAO;

    @GetMapping("/getAllCompany")
    public List<CompaniesDTO> getAllCompany() {
        return companiesDAO.findAll().stream().map(c -> modelMapper.map(companiesDAO.save(c), CompaniesDTO.class)).toList();
    }

    @GetMapping("/getCompany/{companyName}")
    public List<CompaniesDTO> getCompany(@PathVariable("companyName") String companyName) {
        return companiesDAO.findByCompanyName(companyName).stream().map(c -> modelMapper.map(companiesDAO.save(c), CompaniesDTO.class)).toList();
    }

    @PostMapping("/saveCompany")
    public CompaniesDTO saveCompany(@RequestBody Companies companies) {
        String password = passwordEncoder.encode(companies.getUserPassword());
        companies.setUserPassword(password);
        companies.setDbName(companies.getCompanyName().toLowerCase().replace(" ", "_"));
        companies.setCompanyRoles(CompanyRoles.ROLE_COMPANY);
        companies.setDbHost("localhost");
        companies.setDbUserName("root");
        companies.setDbUserPassword("root");
        return modelMapper.map(companiesDAO.save(companies), CompaniesDTO.class);
    }
}
