package ru.zubov.planner_task.stat;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.zubov.planner_entity.entity.Stat;

@RestController
@AllArgsConstructor
public class StatController {
    private final StatService statService;

    @GetMapping("/stat")
    public ResponseEntity<?> findByUser(@RequestParam("userId") Long userId) {
        if (userId == null) {
            return new ResponseEntity<>("missed param: userId", HttpStatus.NOT_ACCEPTABLE);
        }
        Stat stat = statService.findStat(userId);
        return stat != null ? ResponseEntity.ok(statService.findStat(userId)) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
