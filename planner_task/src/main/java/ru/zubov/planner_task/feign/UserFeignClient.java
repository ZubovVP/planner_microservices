package ru.zubov.planner_task.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "planner-user")
public interface UserFeignClient {

    @Retryable(retryFor = {RuntimeException.class}, maxAttempts = 3, backoff = @Backoff(3000))
    @GetMapping("/user/id/{id}")
    ResponseEntity<?> findById(@PathVariable("id") Long id);
}
