package com.ms.multi_tenency_db_per_tenent.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.multi_tenency_db_per_tenent.dao.EmployeeDAO;
import com.ms.multi_tenency_db_per_tenent.entity.Empolyees;

@RestController
@RequestMapping("/employee")
public class EmployeeApis {
	
	@Autowired
	private EmployeeDAO employeeDAO;
	
	@GetMapping("/getEmps")
	public List<Empolyees> getEmps() {
		return employeeDAO.findAll();
	}
	
	@PostMapping("/addEmps")
	public Empolyees saveEmps(@RequestBody Empolyees empolyees) {
		return employeeDAO.save(empolyees);
	}

}
