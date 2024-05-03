package ru.zubov.planner_user.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zubov.planner_entity.entity.User;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody User user) {
        if (user.getId() != null || user.getId() != 0) {
            return new ResponseEntity<>("id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return new ResponseEntity<>("missed param: email", HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return new ResponseEntity<>("missed param: password", HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return new ResponseEntity<>("missed param: username", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody User user) {
        if (user.getId() == null || user.getId() == 0) {
            return new ResponseEntity<>("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return new ResponseEntity<>("missed param: email", HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return new ResponseEntity<>("missed param: password", HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return new ResponseEntity<>("missed param: username", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping("/deleteById")
    public ResponseEntity<?> deleteById(@RequestBody Long userId) {
        if (userId == null || userId == 0) {
            return new ResponseEntity<>("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            userService.deleteByUserId(userId);
        } catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity<>("userId = " + userId + " not found in DB", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
