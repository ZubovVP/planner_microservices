package ru.zubov.planner_task.stat;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.zubov.planner_entity.entity.Stat;

@Repository
public interface StatRepository extends CrudRepository<Stat, Long> {

    Stat findByUserId(Long userId);
}
