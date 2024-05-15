package ru.zubov.planner_task.feign;

import com.google.common.io.CharStreams;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.antlr.v4.runtime.CharStream;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

@Component
public class FeignExceptionHandler implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        return switch (response.status()) {
            case 406 -> new ResponseStatusException(HttpStatusCode.valueOf(406), readMessage(response));
            case 404 -> new ResponseStatusException(HttpStatusCode.valueOf(404), readMessage(response));
            default -> null;
        };
    }

    private String readMessage(Response response) {
        String message;
        Reader reader = null;

        try {
            reader = response.body().asReader(Charset.defaultCharset());
            message = CharStreams.toString(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return message;
    }
}
