package ru.zubov.planner_user.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zubov.planner_entity.entity.Task;
import ru.zubov.planner_entity.entity.User;

import java.time.LocalDateTime;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    void deleteByEmail(String email);

    @Query("SELECT u FROM User u where " +
            "(:email is null or :email='' or lower(u.email) like lower(concat('%', :email,'%'))) or" +
            "(:username is null or :username='' or lower(u.username) like lower(concat('%', :username,'%')))"
    )
        // искать по всем переданным параметрам (пустые параметры учитываться не будут)
    Page<User> findByParams(@Param("email") String email,
                            @Param("username") String username,
                            Pageable pageable
    );
}
