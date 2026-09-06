package com.echotech.auth.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Cloud SQL Java Connector datasource, matching Google's Hikari + socket factory
 * setup: https://docs.cloud.google.com/sql/docs/mysql/connect-connectors#java
 *
 * Required environment variables:
 *   INSTANCE_CONNECTION_NAME  project:region:instance
 *   DB_USER
 *   DB_PASS
 *   DB_NAME
 *
 * Optional:
 *   INSTANCE_UNIX_SOCKET      Unix socket path (Cloud Run / GCE)
 *   PRIVATE_IP                if set, prefer private IP only
 *
 * Uses Application Default Credentials. Locally run:
 *   gcloud auth application-default login
 */
@Configuration
@Profile("!local & !test")
public class CloudSqlDataSourceConfig {

    @Bean
    public DataSource dataSource() {
        String instanceConnectionName = requiredEnv("INSTANCE_CONNECTION_NAME");
        String dbUser = requiredEnv("DB_USER");
        String dbPass = requiredEnv("DB_PASS");
        String dbName = requiredEnv("DB_NAME");
        String instanceUnixSocket = System.getenv("INSTANCE_UNIX_SOCKET");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(String.format("jdbc:mysql:///%s", dbName));
        config.setUsername(dbUser);
        config.setPassword(dbPass);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
        config.addDataSourceProperty("cloudSqlInstance", instanceConnectionName);

        if (instanceUnixSocket != null && !instanceUnixSocket.isBlank()) {
            config.addDataSourceProperty("unixSocketPath", instanceUnixSocket);
        }

        if (System.getenv("PRIVATE_IP") != null && !System.getenv("PRIVATE_IP").isBlank()) {
            config.addDataSourceProperty("ipTypes", "PRIVATE");
        } else {
            config.addDataSourceProperty("ipTypes", "PUBLIC,PRIVATE");
        }

        // Lazy refresh avoids background CPU use on Cloud Run / Cloud Functions.
        config.addDataSourceProperty("cloudSqlRefreshStrategy", "lazy");

        return new HikariDataSource(config);
    }

    private static String requiredEnv(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing required environment variable " + name
                            + ". Set INSTANCE_CONNECTION_NAME, DB_USER, DB_PASS, and DB_NAME "
                            + "for Cloud SQL, or run with --spring.profiles.active=local.");
        }
        return value;
    }
}
