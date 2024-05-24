package ru.zubov.planner_task.mq;

import org.springframework.messaging.MessageChannel;

//Интерфейс который нужен для работы mq
public interface TodoBinding {
    String INPUT_CHANNEL = "todoOutputChannel";

    @Input(OUTPUT_CHANNEL)
    MessageChannel todoOutputChannel();
}
