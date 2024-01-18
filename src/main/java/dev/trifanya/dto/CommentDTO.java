package dev.trifanya.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Accessors(chain = true)
public class CommentDTO {
    private int id;
    private String text;
    private LocalDateTime createdAt;
    private TaskDTO task;
    private UserDTO author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentDTO)) return false;
        CommentDTO comment = (CommentDTO) o;
        return id == comment.id &&
                Objects.equals(text, comment.text) &&
                Objects.equals(createdAt, comment.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, createdAt);
    }
}
