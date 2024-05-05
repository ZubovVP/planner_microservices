package ru.zubov.planner_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDiscoveryClient
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"ru.zubov.planner_user"})
@ComponentScan(basePackages = {"ru.zubov"})
public class PlannerUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlannerUserApplication.class, args);
    }

}
