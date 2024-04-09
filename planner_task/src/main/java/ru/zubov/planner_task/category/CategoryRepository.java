package ru.zubov.planner_task.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zubov.planner_entity.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Поиск категорий пользователя (по email) сортировка по названию
     *
     * @param userId - userId пользователя
     * @return лист списка категорий в отсортированном виде
     */
    List<Category> findByUserIdOrderByTitleAsc(Long userId);

    @Query("SELECT c FROM Category c WHERE " +
            "(:title IS NULL OR :title='' OR LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND c.userId=:userId " +
            "ORDER BY c.title ASC")
    List<Category> findByTitle(@Param("title") String title, @Param("userId") Long userId);
}