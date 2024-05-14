package ru.zubov.planner_task.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "planner-user")
public interface UserFeignClient {

    @GetMapping("/user/id/{id}")
     ResponseEntity<?> findById(@PathVariable("id") Long id);
}
