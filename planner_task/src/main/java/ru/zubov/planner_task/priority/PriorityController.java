package ru.zubov.planner_task.priority;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zubov.planner_entity.entity.Priority;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
@RequestMapping("/priority")
@RequiredArgsConstructor
public class PriorityController {
    private final PriorityService priorityService;

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
        return ResponseEntity.ok(priorityService.add(priority));
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
        priorityService.update(priority);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@ModelAttribute PrioritySearchValues prioritySearchValues) {
        if (prioritySearchValues.getUserId() == null) {
            return new ResponseEntity<>("missed param: email", HttpStatus.NOT_ACCEPTABLE);
        }
        List<Priority> list = priorityService.findByTitle(prioritySearchValues.getTitle(), prioritySearchValues.getUserId());
        return ResponseEntity.ok(list);
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
