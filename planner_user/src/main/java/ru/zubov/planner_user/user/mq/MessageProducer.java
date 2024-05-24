package ru.zubov.planner_user.user.mq;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(TodoBinding.class)
public class MessageProducer {

    private TodoBinding todoBinding;

    public MessageProducer(TodoBinding todoBinding) {
        this.todoBinding = todoBinding;
    }

    //Отправка сообщения при создании нового пользователя
    public void initUserData(Long id) {
        Message message = MessageBuilder.withPayload(id).build();

        //выбираем канал и отправляем сообщение
        todoBinding.todoOutputChannel().send(message);
    }
}
