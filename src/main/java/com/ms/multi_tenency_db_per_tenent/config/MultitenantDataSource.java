package com.ms.multi_tenency_db_per_tenent.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

// 2
public class MultitenantDataSource extends AbstractRoutingDataSource {
	@Override
    protected String determineCurrentLookupKey() {
        return TenantContext.getCurrentTenant();
    }
}
