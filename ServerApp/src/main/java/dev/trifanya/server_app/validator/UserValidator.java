package dev.trifanya.server_app.validator;

import dev.trifanya.server_app.exception.InvalidDataException;
import dev.trifanya.server_app.model.User;
import dev.trifanya.server_app.repository.UserRepository;
import dev.trifanya.server_app.exception.AlreadyExistException;

import java.util.Optional;

public class UserValidator {
    private final UserRepository userRepository;

    public UserValidator() {
        this.userRepository = new UserRepository();
    }

    public void validateUser(User user) {
        if (user.getName().isEmpty()) throw new InvalidDataException("Необходимо указать имя пользователя.");
        if (user.getSurname().isEmpty()) throw new InvalidDataException("Необходимо указать фамилию пользователя.");
        if (user.getPosition().isEmpty()) throw new InvalidDataException("Необходимо указать должность пользователя.");
        if (user.getEmail().isEmpty()) throw new InvalidDataException("Необходимо указать email пользователя.");
        if (user.getPassword().isEmpty()) throw new InvalidDataException("Необходимо указать пароль пользователя.");

        Optional<User> foundUser = userRepository.getUserByEmail(user.getEmail());
        if (foundUser.isPresent() && foundUser.get().getId() != user.getId()) {
            throw new AlreadyExistException("Пользователь с указанным email уже зарегистрирован.");
        }
    }
}
