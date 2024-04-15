package ru.zubov.planner_task.priority;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zubov.planner_entity.entity.Priority;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PriorityService {
    private final PriorityRepository priorityRepository;

    public Priority add(Priority priority) {
        return priorityRepository.save(priority);
    }

    public void update(Priority priority) {
        priorityRepository.save(priority);
    }

    public void delete(Long id) {
        Priority priority = new Priority();
        priority.setId(id);
        priorityRepository.delete(priority);
    }

    public Optional<Priority> findById(Long id) {
        return priorityRepository.findById(id);
    }

    public List<Priority> findAll(Long userId) {
        return priorityRepository.findAllByUserIdOrderByIdAsc(userId);
    }

    public List<Priority> findByTitle(String title, Long userId) {
        return priorityRepository.findByTitle(title, userId);
    }
}
