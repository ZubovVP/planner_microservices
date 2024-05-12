package ru.zubov.planner_task.stat;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.zubov.planner_entity.entity.Stat;
import ru.zubov.utils.restTemplate.UserRestBuilder;

@RestController
@AllArgsConstructor
public class StatController {
    private StatService statService;
    private UserRestBuilder userRestBuilder;

    @GetMapping("/stat")
    public ResponseEntity<?> findByUser(@RequestParam("userId") Long userId) {
        if (userRestBuilder.existUser(userId)) {
            Stat stat = statService.findStat(userId);
            return stat != null ? ResponseEntity.ok(statService.findStat(userId)) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("don't found user by id", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
