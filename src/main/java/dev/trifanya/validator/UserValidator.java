package dev.trifanya.validator;

import dev.trifanya.exception.AlreadyExistException;
import dev.trifanya.exception.PasswordMismatchException;
import dev.trifanya.model.User;
import dev.trifanya.service.UserService;

public class UserValidator {
    private final UserService userService;

    public UserValidator() {
        this.userService = new UserService();
    }

    public void validateUser(User user, String passwordConfirmation) {
        User foundUser = userService.getUserByEmail(user.getEmail());
        if (foundUser != null && foundUser.getId() != user.getId()) {
            throw new AlreadyExistException("Пользователь с указанным email уже зарегистрирован.");
        } else if (!user.getPassword().equals(passwordConfirmation)) {
            throw new PasswordMismatchException("Пароли не совпадают.");
        }
    }
}
