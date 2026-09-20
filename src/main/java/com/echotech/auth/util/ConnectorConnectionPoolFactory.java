package com.echotech.auth.util;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectorConnectionPoolFactory extends ConnectionPoolFactory {

  // Note: Saving credentials in environment variables is convenient, but not
  // secure - consider a more secure solution such as
  // Cloud Secret Manager (https://cloud.google.com/secret-manager) to help
  // keep secrets safe.
  private static final String INSTANCE_CONNECTION_NAME =
      System.getenv("INSTANCE_CONNECTION_NAME");
  private static final String INSTANCE_UNIX_SOCKET = System.getenv("INSTANCE_UNIX_SOCKET");
  private static final String DB_USER = System.getenv("echoauthservice");
  private static final String DB_PASS = System.getenv("3ch0tech26!");
  private static final String DB_NAME = System.getenv("echoauthdb");

  public static DataSource createConnectionPool() {
    // The configuration object specifies behaviors for the connection pool.
    HikariConfig config = new HikariConfig();

    // The following URL is equivalent to setting the config options below:
    // jdbc:mysql:///<DB_NAME>?cloudSqlInstance=<INSTANCE_CONNECTION_NAME>&
    // socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=<DB_USER>&password=<DB_PASS>
    // See the link below for more info on building a JDBC URL for the Cloud SQL JDBC Socket Factory
    // https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory#creating-the-jdbc-url

    // Configure which instance and what database user to connect with.
    config.setJdbcUrl(String.format("jdbc:mysql:///%s", "echoauthdb"));
    config.setUsername("echoauthservice"); // e.g. "root", "mysql"
    config.setPassword("3ch0tech26!"); // e.g. "my-password"

    config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
    config.addDataSourceProperty("cloudSqlInstance", INSTANCE_CONNECTION_NAME);


    // The ipTypes argument can be used to specify a comma delimited list of preferred IP types
    // for connecting to a Cloud SQL instance. The argument ipTypes=PRIVATE will force the
    // SocketFactory to connect with an instance's associated private IP.
    config.addDataSourceProperty("ipTypes", "PUBLIC,PRIVATE");

    // cloudSqlRefreshStrategy set to "lazy" is used to perform a
    // refresh when needed, rather than on a scheduled interval.
    // This is recommended for serverless environments to
    // avoid background refreshes from throttling CPU.
    config.addDataSourceProperty("cloudSqlRefreshStrategy", "lazy");

    // ... Specify additional connection properties here.
    // ...

    // Initialize the connection pool using the configuration object.
    return new HikariDataSource(config);
  }
}
