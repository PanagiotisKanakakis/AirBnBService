package com.main;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("${entityPath}")
@EnableJpaRepositories("${repositoryPath}")
@ComponentScan("${componentScanPath}")
@ImportResource("${configurationFileClassPath}")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        new SpringApplicationBuilder(Application.class).web(false).run(args);
    }
}
