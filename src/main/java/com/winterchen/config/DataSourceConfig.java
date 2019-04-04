package com.winterchen.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Value("${spring.datasource.secondary.url}")
    String url;
    @Value("${spring.datasource.secondary.username}")
    String username;
    @Value("${spring.datasource.secondary.password}")
    String password;
    @Value("${spring.datasource.secondary.driver-class-name}")
    String driverClass;

    @Bean(name = "secondaryDataSource")
    @Qualifier("secondaryDataSource")
//    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource secondaryDataSource() {
        System.out.print(url);
        DataSource ds = DataSourceBuilder.create().
                username(username).
                password(password).
                url(url).
                driverClassName(driverClass).
                build();
        return ds;
    }

    @Bean(name="secondJdbcTemplate")
    public JdbcTemplate secondJdbcTemplate(){
        JdbcTemplate jdbcTemplate=new JdbcTemplate();
        jdbcTemplate.setDataSource(secondaryDataSource());
        return  jdbcTemplate;
    }
}