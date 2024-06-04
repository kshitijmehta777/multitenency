package com.ms.multi_tenency_db_per_tenent.entity;

import java.io.Serializable;

import com.ms.multi_tenency_db_per_tenent.enums.CompanyRoles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Companies implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer companyId;
	@Column(unique = true)
	private String companyName;
	@Column(unique = true)
	private String userLoginName;
	private String userPassword;
	@Enumerated(EnumType.STRING)
	private CompanyRoles companyRoles;
	private String dbName;
	private String dbUserName;
	private String dbUserPassword;
	private String dbHost;
}
