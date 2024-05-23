package ru.zubov.planner_user.user.mq;

import org.springframework.messaging.MessageChannel;
import org.springframework.cloud.stream.annotation.*;

//Интерфейс который нужен для работы mq
public interface TodoBinding {
    String OUTPUT_CHANNEL = "todoOutputChannel";

    @Output(OUTPUT_CHANNEL)
    MessageChannel todoOutputChannel();
}
