package dev.trifanya.server_app.validator;

import dev.trifanya.server_app.model.User;
import dev.trifanya.server_app.service.UserService;
import dev.trifanya.server_app.exception.AlreadyExistException;

public class UserValidator {
    private final UserService userService;

    public UserValidator() {
        this.userService = new UserService();
    }

    public void validateUser(User user) {
        User foundUser = userService.getUserByEmail(user.getEmail());
        if (foundUser != null && foundUser.getId() != user.getId()) {
            throw new AlreadyExistException("Пользователь с указанным email уже зарегистрирован.");
        }
    }
}
