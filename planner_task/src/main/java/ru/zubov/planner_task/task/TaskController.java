package ru.zubov.planner_task.task;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zubov.planner_entity.entity.Task;
import ru.zubov.utils.restTemplate.UserRestBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController {
    private TaskService taskService;
    private UserRestBuilder userRestBuilder;
    public static final String ID_COLUMN = "id"; // имя столбца id

    @PutMapping("/add")
    public ResponseEntity<?> add(@RequestBody Task task) {
        if (task.getId() != null && task.getId() != 0) {
            return new ResponseEntity<>("id param must be NULL", HttpStatus.NOT_ACCEPTABLE);
        }

        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            return new ResponseEntity<>("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        if (userRestBuilder.existUser(task.getUserId())) {
            return ResponseEntity.ok(taskService.add(task));
        } else {
            return new ResponseEntity<>("don't found user by id", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll(@RequestParam Long userId) {
        if (userRestBuilder.existUser(userId)) {
            List<Task> result = taskService.findAll(userId);
            return ResponseEntity.ok(result);
        } else {
            return new ResponseEntity<>("don't found user by id", HttpStatus.NOT_ACCEPTABLE);
        }


    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Task task) {
        if (task.getId() == null || task.getId() == 0) {
            return new ResponseEntity<>("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            return new ResponseEntity<>("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        if (userRestBuilder.existUser(task.getUserId())) {
            taskService.update(task);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("don't found user by id", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            taskService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<?> findById(@RequestParam Long id) {
        Task task;
        try {
            task = taskService.findById(id).orElse(null);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity<>("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(task);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestBody TaskSearchValues taskSearchValues) {
        String title = taskSearchValues.getTitle() != null ? taskSearchValues.getTitle() : null;

        // конвертируем Boolean в Integer
        boolean completed = false;
        if (taskSearchValues.getCompleted() != null) {
            completed = taskSearchValues.getCompleted() != 0;
        }

        Long priorityId = taskSearchValues.getPriorityId() != null ? taskSearchValues.getPriorityId() : null;
        Long categoryId = taskSearchValues.getCategoryId() != null ? taskSearchValues.getCategoryId() : null;

        String sortColumn = taskSearchValues.getSortColumn() != null ? taskSearchValues.getSortColumn() : null;
        String sortDirection = taskSearchValues.getSortDirection() != null ? taskSearchValues.getSortDirection() : null;

        Integer pageNumber = taskSearchValues.getPageNumber() != null ? taskSearchValues.getPageNumber() : null;
        Integer pageSize = taskSearchValues.getPageSize() != null ? taskSearchValues.getPageSize() : null;

        Long userId = taskSearchValues.getUserId() != null ? taskSearchValues.getUserId() : null; // для показа задач только этого пользователя

        if (!userRestBuilder.existUser(userId)) {
            return new ResponseEntity<>("don't found user by id", HttpStatus.NOT_ACCEPTABLE);
        }

        LocalDateTime dateFrom = null;
        LocalDateTime dateTo = null;

        if (taskSearchValues.getDateFrom() != null) {
            dateFrom = taskSearchValues.getDateFrom();
        }

        if (taskSearchValues.getDateTo() != null) {
            dateTo = taskSearchValues.getDateTo(); // записываем конечную дату
        }

        // направление сортировки
        Sort.Direction direction = sortDirection == null || sortDirection.trim().isEmpty() || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        /* Вторым полем для сортировки добавляем id, чтобы всегда сохранялся строгий порядок.
            Например, если у 2-х задач одинаковое значение приоритета и мы сортируем по этому полю.
            Порядок следования этих 2-х записей после выполнения запроса может каждый раз меняться, т.к. не указано второе поле сортировки.
            Поэтому и используем ID - тогда все записи с одинаковым значением приоритета будут следовать в одном порядке по ID.
         */

        // объект сортировки, который содержит столбец и направление
        Sort sort = Sort.by(direction, sortColumn, ID_COLUMN);

        // объект постраничности
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        // результат запроса с постраничным выводом
        Page<Task> result = taskService.findByParams(title, completed, priorityId, categoryId, userId, dateFrom, dateTo, pageRequest);

        // результат запроса
        return ResponseEntity.ok(result);
    }
}
