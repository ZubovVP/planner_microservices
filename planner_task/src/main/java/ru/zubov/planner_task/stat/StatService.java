package ru.zubov.planner_task.stat;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zubov.planner_entity.entity.Stat;

@Service
@AllArgsConstructor
@Transactional
public class StatService {
    private final StatRepository repository;

    public Stat findStat(Long userId) {
        return repository.findByUserId(userId);
    }
}
