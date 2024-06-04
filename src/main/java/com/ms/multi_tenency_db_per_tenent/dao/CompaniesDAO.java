package com.ms.multi_tenency_db_per_tenent.dao;

import com.ms.multi_tenency_db_per_tenent.entity.Companies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CompaniesDAO extends JpaRepository<Companies, Integer> {
    List<Companies> findByCompanyName(String companyName);

    List<Companies> findByUserLoginName(String userName);
}
