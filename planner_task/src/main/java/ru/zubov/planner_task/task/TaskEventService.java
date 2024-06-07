package ru.zubov.planner_task.task;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.zubov.planner_entity.entity.User;

@Service
public class TaskEventService {

    @KafkaListener(topics = "java-test")
    public void kafkaListener(ConsumerRecord<String, User> record) {

        System.out.println("User created id = " + record.value().getId());
        System.out.println("Topic = " + record.topic());

    }
}
