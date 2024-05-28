package com.example.AcmeBank;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DatabaseInitializer {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @PostConstruct
    public void initializeDatabase() throws IOException {
        if (ddlAuto.equals("update")) {
            String checkForReload = "SELECT COUNT(*) FROM ACCOUNT";
            int count = jdbcTemplate.queryForObject(checkForReload, Integer.class);
            if (count == 0) {
                String query = "INSERT INTO account (account_number, balance) VALUES " +
                        "('12345678', 1000000)," +
                        "('88888888', 1000000);";

                jdbcTemplate.execute(query);
            }
        }
    }
}