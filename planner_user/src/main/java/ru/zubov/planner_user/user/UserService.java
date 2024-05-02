package ru.zubov.planner_user.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.zubov.planner_entity.entity.User;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public void deleteByUserId(Long id) {
        repository.deleteById(id);
    }

    public void deleteByUserEmail(String email) {
        repository.deleteByEmail(email);
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Page<User> findByParams(String email, String username, Pageable pageable) {
        return repository.findByParams(email, username, pageable);
    }
}
