package ru.zubov.planner_task.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignExceptionHandler implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        return switch (response.status()) {
            case 406 -> new ResponseStatusException(HttpStatusCode.valueOf(406), "error 406");
            case 404 -> new ResponseStatusException(HttpStatusCode.valueOf(404), "error 404");
            default -> null;
        };
    }
}
