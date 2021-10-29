package nl.bvsit.fileindatabaseexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class FileInDatabaseExampleApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(FileInDatabaseExampleApplication.class);
        Properties properties = new Properties();
        //Enable H2 console on http://localhost:8080/h2-console JDBC URL: jdbc:h2:mem:db
        properties.put("spring.h2.console.enabled", true);
        springApplication.setDefaultProperties(properties);
        springApplication.run(args);
    }

}
