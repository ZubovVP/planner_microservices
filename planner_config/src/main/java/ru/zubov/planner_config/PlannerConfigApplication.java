package ru.zubov.planner_config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class PlannerConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlannerConfigApplication.class, args);
    }

}
