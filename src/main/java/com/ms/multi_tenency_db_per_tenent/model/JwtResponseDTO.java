package com.ms.multi_tenency_db_per_tenent.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponseDTO {
    private String accessToken;
}
