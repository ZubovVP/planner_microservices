package ru.zubov.utils.restTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserRestBuilder {
    @Value("${user.base.url}")
    private String baseUrl;
    
    public boolean existUser(Long id){
       return RestClient.create(baseUrl).get().uri("/id/{id}",  id).accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> response.getStatusCode().is2xxSuccessful());
    }
}
