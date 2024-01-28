package dev.trifanya.validator;

import dev.trifanya.model.Task;
import dev.trifanya.model.User;

public class CommentValidator {
    public void validateNewComment(Task task, User commentAuthor/*int commentAuthorId*/) {
        if (!task.getAuthor().equals(commentAuthor) && !task.getPerformer().equals(commentAuthor)) {
            throw new UnavailableActionException("Комментарий к задаче может опубликовать только ее автор или исполнитель.");
        }
    }

    public void validateUpdatedComment(User commentAuthor, User commentModifier) {
        if (!commentAuthor.equals(commentModifier)) {
            throw new UnavailableActionException("Комментарий к задаче может редактировать только автор комментария.");
        }
    }

    public void validateDelete(User commentAuthor, User commentDeleter) {
        if (!commentAuthor.equals(commentDeleter)) {
            throw new UnavailableActionException("Комментарий может удалить только автор комментария.");
        }
    }
}
