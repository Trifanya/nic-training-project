package dev.trifanya.server_connection;

import dev.trifanya.model.TaskDTO;
import javafx.concurrent.Task;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class TaskClient {
    private static final String HOST = "http://localhost:8080";
    private static final WebClient webClient = WebClient.create(HOST);

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
        Mono<TaskDTO> taskMono = Mono.just(newTask);

        Mono<ResponseEntity<String>> response = webClient.post()
                .uri("/tasks/new")
                .body(taskMono, TaskDTO.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);

        System.out.println(response.block());
    }

    public void updateTaskInfo(TaskDTO updatedTask) {
        Mono<TaskDTO> taskMono = Mono.just(updatedTask);

        Mono<ResponseEntity<String>> response = webClient.patch()
                .uri("/tasks/update")
                .body(taskMono, TaskDTO.class)
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
