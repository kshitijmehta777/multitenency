package com.ms.multi_tenency_db_per_tenent.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.multi_tenency_db_per_tenent.entity.Empolyees;

public interface EmployeeDAO extends JpaRepository<Empolyees, Integer> {

}
