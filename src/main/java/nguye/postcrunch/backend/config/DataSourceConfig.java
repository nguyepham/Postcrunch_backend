package nguye.postcrunch.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  DataSource dataSource() {

    String password = "secret";

    return DataSourceBuilder
        .create()
        .url("jdbc:mysql://localhost:3306/postcrunch")
        .username("root")
        .password(password)
        .build();
  }
}