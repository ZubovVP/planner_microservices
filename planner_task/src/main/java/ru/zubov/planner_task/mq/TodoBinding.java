package ru.zubov.planner_task.mq;

import org.springframework.messaging.MessageChannel;

//Интерфейс который нужен для работы mq
public interface TodoBinding {
    String OUTPUT_CHANNEL = "todoOutputChannel";

    @Input(OUTPUT_CHANNEL)
    MessageChannel todoOutputChannel();
}
