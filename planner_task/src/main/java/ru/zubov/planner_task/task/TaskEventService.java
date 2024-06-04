package ru.zubov.planner_task.task;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TaskEventService {

    @KafkaListener(topics = "java-test")
    public void kafkaListener(Long userId) {
        System.out.println("User created id = " + userId);

    }
}
