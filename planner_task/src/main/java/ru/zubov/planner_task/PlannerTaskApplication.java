package ru.zubov.planner_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PlannerTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlannerTaskApplication.class, args);
    }

}
