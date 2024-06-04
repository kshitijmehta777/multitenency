package com.ms.multi_tenency_db_per_tenent.model;

import lombok.Data;

@Data
public class CompaniesDTO {
    private Integer companyId;
    private String companyName;
    private String loginName;
}
