package ru.zubov.planner_task.priority;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zubov.planner_entity.entity.Priority;
import ru.zubov.utils.restTemplate.UserRestBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/priority")
@AllArgsConstructor
public class PriorityController {
    private final PriorityService priorityService;
    private final UserRestBuilder userRestBuilder;

    @GetMapping("/all")
    public ResponseEntity<List<Priority>> findById(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(priorityService.findAll(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Priority priority) {

        if (priority.getId() != null && priority.getId() != 0) {
            return new ResponseEntity<>("id param must be NULL", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getTitle() == null || priority.getTitle().trim().isEmpty()) {
            return new ResponseEntity<>("missed param : title", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getColor() == null || priority.getColor().trim().isEmpty()) {
            return new ResponseEntity<>("missed param : color", HttpStatus.NOT_ACCEPTABLE);
        }
        if (userRestBuilder.existUser(priority.getUserId())) {
            return ResponseEntity.ok(priorityService.add(priority));
        } else {
            return new ResponseEntity<>("don't found user by id", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            priorityService.delete(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>("id " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Priority priority) {
        if (priority.getId() == null || priority.getId() == 0) {
            return new ResponseEntity<>("id param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getTitle() == null && priority.getTitle().trim().isEmpty()) {
            return new ResponseEntity<>("missed param : title", HttpStatus.NOT_ACCEPTABLE);
        }
        if (userRestBuilder.existUser(priority.getUserId())) {
            priorityService.update(priority);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("don't found user by id", HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@ModelAttribute PrioritySearchValues prioritySearchValues) {
        if (userRestBuilder.existUser(prioritySearchValues.getUserId())) {
            List<Priority> list = priorityService.findByTitle(prioritySearchValues.getTitle(), prioritySearchValues.getUserId());
            return ResponseEntity.ok(list);
        } else {
            return new ResponseEntity<>("don't found user by id", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/id")
    public ResponseEntity<?> search(@RequestParam Long id) {
        if (id == null) {
            return new ResponseEntity<>("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        Optional<Priority> priority = priorityService.findById(id);

        return priority.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
