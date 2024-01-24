package dev.trifanya.server_connection;

import dev.trifanya.dto.TaskDTO;
import javafx.concurrent.Task;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class TaskClient {
    private static final String HOST = "http://localhost:8080";
    private static final WebClient webClient = WebClient.create(HOST);
    private RestTemplate restTemplate = new RestTemplate();

    public TaskDTO getTaskById(int taskId) {
        return webClient.get()
                .uri("/tasks/task_id_" + taskId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TaskDTO.class)
                .blockOptional()
                .get();
    }

    public List<TaskDTO> getAllTasks() {
        return webClient.get()
                .uri("/tasks/all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(TaskDTO.class)
                .collectList().block();
    }

/*    public List<TaskDTO> getAllTasks() {
        ParameterizedTypeReference<List<TaskDTO>> typeRef = new ParameterizedTypeReference<List<TaskDTO>>() {};
        ResponseEntity<List<TaskDTO>> response = restTemplate.exchange(HOST + "/tasks/all", HttpMethod.GET, null, typeRef);
        return response.getBody();
    }*/

    public List<TaskDTO> getTasksByAuthorId(int authorId) {
        return webClient.get()
                .uri("/tasks/author_id_" + authorId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(TaskDTO.class)
                .collectList().block();
    }

    public List<TaskDTO> getTasksByPerformerId(int performerId) {
        return webClient.get()
                .uri("/tasks/performer_id_" + performerId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(TaskDTO.class)
                .collectList().block();
    }

    public void createNewTask(TaskDTO newTask) {
        Mono<ResponseEntity<String>> response = webClient.post()
                .uri("/tasks/new")
                .bodyValue(newTask)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);
        System.out.println(response.block());
    }

/*    public void createNewTask(TaskDTO newTask) {
        HttpEntity<TaskDTO> request = new HttpEntity<>(newTask);
        ResponseEntity<String> response = restTemplate
                .exchange(HOST + "/tasks/new", HttpMethod.POST, request, String.class);
        System.out.println(response);
    }*/

    public void updateTaskInfo(TaskDTO updatedTask) {
        Mono<ResponseEntity<String>> response = webClient.patch()
                .uri("/tasks/update")
                .bodyValue(updatedTask)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);
        System.out.println(response.block());
    }

    public void deleteTask(int taskToDeleteId) {
        Mono<ResponseEntity<String>> response = webClient.delete()
                .uri("/tasks/" + taskToDeleteId + "/delete")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);
        System.out.println(response.block());
    }
}
