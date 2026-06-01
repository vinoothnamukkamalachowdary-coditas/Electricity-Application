package com.example.demo_Electricity_App.Service;

import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;

@Service
public class FlywayService {
    public void migrate(
            String schema,
            String url,
            String username,
            String password) {

        Flyway flyway =
                Flyway.configure()
                        .dataSource(url, username, password)
                        .schemas(schema)
                        .defaultSchema(schema)
                        .validateMigrationNaming(false)
                        .load();

        flyway.migrate();
    }
}
