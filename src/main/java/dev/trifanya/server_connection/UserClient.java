package dev.trifanya.server_connection;

import dev.trifanya.dto.UserDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class UserClient {
    private static final String HOST = "http://localhost:8080";
    private static final WebClient webClient = WebClient.create(HOST);

    public List<UserDTO> getAllUsers() {
        return webClient.get()
                .uri("/users/all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(UserDTO.class)
                .collectList().block();
    }
}
