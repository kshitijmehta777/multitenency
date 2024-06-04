package com.ms.multi_tenency_db_per_tenent.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import com.ms.multi_tenency_db_per_tenent.entity.Companies;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class MultitenantConfiguration {

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.url}")
	private String dataSourceUrl;

	@Value("${spring.datasource.username}")
	private String dataSourceUserName;

	@Value("${spring.datasource.password}")
	private String dataSourcePassword;

	Map<Object, Object> resolvedDataSources = new HashMap<>();

	private DataSource generateDataSource(Companies companies) {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setUsername(companies.getDbUserName());
		hikariConfig.setPassword(companies.getDbUserPassword());
		hikariConfig.setJdbcUrl(String.format("jdbc:mysql://%s:3306/%s?createDatabaseIfNotExist=true",
				companies.getDbHost(), companies.getDbName()));
		hikariConfig.setDriverClassName(driverClassName);
		return new HikariDataSource(hikariConfig);
	}

	public void addDataSource(Companies companies) {
		if (!resolvedDataSources.containsKey(companies.getDbName())) {
			DataSource ds = generateDataSource(companies);
			resolvedDataSources.put(companies.getDbName(), ds);
			flywayMigration(ds);
			try (GenericApplicationContext ac = new GenericApplicationContext()) {
				ac.registerBean("abstractDataSource", DataSource.class, () -> dataSource());
				ac.refresh();
			} catch (BeansException | IllegalStateException e) {
				e.printStackTrace();
			}
		}
	}

	@Bean("abstractDataSource")
	DataSource dataSource() {
		resolvedDataSources.put("default", getDefaultDataSource());
		AbstractRoutingDataSource routingDataSource = new MultitenantDataSource();
		routingDataSource.setDefaultTargetDataSource(resolvedDataSources.get("default"));
		routingDataSource.setTargetDataSources(resolvedDataSources);
		routingDataSource.afterPropertiesSet();
		return routingDataSource;
	}

	private DataSource getDefaultDataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setUsername(dataSourceUserName);
		hikariConfig.setPassword(dataSourcePassword);
		hikariConfig.setJdbcUrl(dataSourceUrl);
		hikariConfig.setDriverClassName(driverClassName);
		return new HikariDataSource(hikariConfig);
	}

	public void flywayMigration(DataSource dataSource) {
		Flyway flyway = Flyway.configure().dataSource(dataSource).baselineOnMigrate(true).load();
		flyway.migrate();
	}

}
