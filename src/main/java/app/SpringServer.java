package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = "app.repository")
@ComponentScan(basePackages = "app.controllers")
@ComponentScan(basePackages = "app.authentication")
public class SpringServer extends SpringBootServletInitializer {

    /**
     * Class that starts the client.
     * @param args arguments of the main method.
     */
    public static void main(String[] args) {

        SpringApplication.run(SpringServer.class, args);

    }
}