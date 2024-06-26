package ru.zubov.planner_task.category;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zubov.planner_entity.entity.Category;
import ru.zubov.planner_task.feign.UserFeignClient;
import ru.zubov.utils.restTemplate.UserRestBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    private CategoryService categoryService;
    //Можно использовать сервис для проверки наличия юзера через RestClient
    private UserRestBuilder userRestBuilder;
    //Можно использовать сервис для проверки наличия юзера через Feign
    private UserFeignClient userFeignClient;

    @GetMapping("/all")
    public ResponseEntity<?> findByUserId(@RequestParam("userId") Long userId) {
        if (userFeignClient.findById(userId) != null) {
            return ResponseEntity.ok(categoryService.findAll(userId));
        } else {
            return new ResponseEntity<>("don't found user by id", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Category category) {
        if (category.getId() != null && category.getId() != 0) {
            return new ResponseEntity<>("id param must be NULL", HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null || category.getTitle().trim().isEmpty()) {
            return new ResponseEntity<>("missed param : title", HttpStatus.NOT_ACCEPTABLE);
        }
        if (userFeignClient.findById(category.getUserId()) != null) {
            return new ResponseEntity<>("don't found user by id", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(categoryService.add(category));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            categoryService.delete(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>("id " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Category category) {
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity<>("id param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null || category.getTitle().trim().isEmpty()) {
            return new ResponseEntity<>("missed param : title", HttpStatus.NOT_ACCEPTABLE);
        }
        if (userFeignClient.findById(category.getUserId()) != null) {
            return new ResponseEntity<>("don't found user by id", HttpStatus.NOT_ACCEPTABLE);
        }
        categoryService.update(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@ModelAttribute CategorySearchValues categorySearchValues) {
        if (categorySearchValues.getUserId() == null || categorySearchValues.getUserId() == 0) {
            return new ResponseEntity<>("missed param: userId", HttpStatus.NOT_ACCEPTABLE);
        }
        if (userRestBuilder.existUser(categorySearchValues.getUserId())) {
            return new ResponseEntity<>("don't found user by id", HttpStatus.NOT_ACCEPTABLE);
        }
        List<Category> list = categoryService.findByTitle(categorySearchValues.getTitle(), categorySearchValues.getUserId());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/id")
    public ResponseEntity<?> search(@RequestParam Long id) {
        if (id == null) {
            return new ResponseEntity<>("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        Optional<Category> category = categoryService.findById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
