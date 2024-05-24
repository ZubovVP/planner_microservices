package ru.zubov.planner_task.mq;

import org.springframework.stereotype.Component;

@Component
@EnableBinding(TodoBinding.class)
public class MessageConsumer {

    @StreamListener(target = TodoBinding.INPUT_CHANNEL)
    public void initTestData(Long userId) {
        System.out.println("User " + userId + " created!");

    }

}
