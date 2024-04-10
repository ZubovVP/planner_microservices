package ru.zubov.planner_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDiscoveryClient
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"ru.zubov.planner_task"})
@EntityScan(basePackages = {"ru.zubov"})
public class PlannerTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlannerTaskApplication.class, args);
    }

}
