package ru.zubov.planner_user.user;

import jakarta.transaction.Transactional;
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

    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    @Transactional
    public void deleteByUserId(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void deleteByUserEmail(String email) {
        repository.deleteByEmail(email);
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Page<User> findByParams(String email, String username, Pageable pageable) {
        return repository.findByParams(email, username, pageable);
    }
}
