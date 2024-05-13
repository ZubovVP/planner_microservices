package ru.zubov.utils.restTemplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.zubov.planner_entity.entity.User;

@ConditionalOnProperty(name = "user.base.url")
@Component
public class UserRestBuilder {
    @Value("${user.base.url}")
    private String baseUrl;

    public boolean existUser(Long id) {
        return RestClient.create(baseUrl).get().uri("/id/{id}", id).accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> response.getStatusCode().is2xxSuccessful());
    }

    public Flux<User> existUserAsync(Long id) {
        return WebClient.create(baseUrl)
                .get()
                .uri("/id/{id}", id)
                .retrieve()
                .bodyToFlux(User.class);

    }
}
