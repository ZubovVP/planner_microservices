package ru.zubov.planner_user.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zubov.planner_entity.entity.User;

import java.time.LocalDateTime;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    public static final String ID_COLUMN = "id"; // имя столбца id

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody User user) {
        if (user.getId() != null && user.getId() != 0) {
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
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>("userId = " + userId + " not found in DB", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/deleteByEmail")
    public ResponseEntity<?> deleteByEmail(@RequestBody String email) {
        if (isNotBlank(email)) {
            return new ResponseEntity<>("missed param: email", HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            userService.deleteByUserEmail(email);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>("email = " + email + " not found in DB", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        if (id == null || id == 0) {
            return new ResponseEntity<>("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        User user;
        try {
            user = userService.findById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>("id = " + id + " not found in DB", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable("email") String email) {
        if (email == null || isBlank(email)) {
            return new ResponseEntity<>("missed param: email", HttpStatus.NOT_ACCEPTABLE);
        }
        User user = null;
        try {
            user = userService.findByEmail(email);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>("email = " + email + " not found in DB", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<User>> search(@RequestBody UserSearchValues userSearchValues) {
        String email = isNotBlank(userSearchValues.getEmail()) ? userSearchValues.getEmail() : null;
        String userName = isNotBlank(userSearchValues.getUsername()) ? userSearchValues.getUsername() : null;

        if (email == null || email.trim().isEmpty()) {
            return new ResponseEntity("missed param: email", HttpStatus.NOT_ACCEPTABLE);
        }
        if (userName == null || userName.trim().isEmpty()) {
            return new ResponseEntity("missed param: username", HttpStatus.NOT_ACCEPTABLE);
        }

        String sortColumn = userSearchValues.getSortColumn() != null ? userSearchValues.getSortColumn() : null;
        String sortDirection = userSearchValues.getSortDirection() != null ? userSearchValues.getSortDirection() : null;

        Integer pageNumber = userSearchValues.getPageNumber() != null ? userSearchValues.getPageNumber() : null;
        Integer pageSize = userSearchValues.getPageSize() != null ? userSearchValues.getPageSize() : null;

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

        Page<User> byParams = userService.findByParams(email, userName, pageRequest);
        return ResponseEntity.ok(byParams);
    }
}
