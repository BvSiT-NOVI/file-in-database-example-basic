package nl.bvsit.fileindatabaseexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Formatter;

@Configuration
public class ConfigDatasource {
    @Bean
    //@Profile("test")
    //NB:overrules setting the DataSource in properties file!
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("sa");
        System.out.println("Setting up dataSource using ConfigDatasource. dataSource.password="+ dataSource.getPassword());
        return dataSource;
    }
}

