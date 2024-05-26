package ru.zubov.planner_task.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {


    @RabbitListener(queues = {"knfQueue"})
    public void initTestData(Long userId) {
        System.out.println("User " + userId + " created!");
    }

}
