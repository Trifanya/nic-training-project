package dev.trifanya.swing_crudapp.validator;

import dev.trifanya.swing_crudapp.model.User;
import dev.trifanya.swing_crudapp.exception.AlreadyExistException;
import dev.trifanya.swing_crudapp.exception.PasswordMismatchException;
import dev.trifanya.swing_crudapp.service.UserService;

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
