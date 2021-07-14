package components.configures;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("components")

public class SpringConfig {

    @Bean
    @Scope("singleton")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5709/money_management_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("Pautina111");

        return dataSource;
    }
    @Bean
    @Scope("singleton")
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }
}