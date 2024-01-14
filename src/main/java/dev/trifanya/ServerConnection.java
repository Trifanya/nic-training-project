package dev.trifanya;


import dev.trifanya.model.TaskDTO;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ServerConnection {
    private static final String HOST = "http://localhost:8080";

    private static final WebClient webClient = WebClient.create(HOST);

    public void fetchDataFromServer() {
        Mono<TaskDTO> result = webClient.get()
                .uri("/tasks/task_id_1").accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TaskDTO.class);

        TaskDTO resultTask = result.blockOptional().get();

        System.out.println(resultTask.getId() + " " + resultTask.getTitle());
    }
}
