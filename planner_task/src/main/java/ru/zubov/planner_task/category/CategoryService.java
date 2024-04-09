package ru.zubov.planner_task.category;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zubov.planner_entity.entity.Category;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category add(Category category) {
        return categoryRepository.save(category);
    }

    public void update(Category category) {
        categoryRepository.save(category);
    }

    public void delete(Long id) {
        Category category = new Category();
        category.setId(id);
        categoryRepository.delete(category);
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public List<Category> findAll(Long userId) {
        return categoryRepository.findByUserIdOrderByTitleAsc(userId);
    }

    public List<Category> findByTitle(String title, Long userId) {
        return categoryRepository.findByTitle(title, userId);
    }
}
