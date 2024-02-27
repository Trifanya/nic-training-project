package dev.trifanya.server_app.service;

import dev.trifanya.server_app.exception.NotFoundException;
import dev.trifanya.server_app.model.User;
import dev.trifanya.server_app.mybatis.criteria_builder.UserFiltersBuilder;

import dev.trifanya.server_app.repository.UserRepository;
import dev.trifanya.server_app.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import java.util.Map;
import java.util.List;

public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
    private static final String NOT_FOUND_BY_ID_MSG = "Пользователь с указанным ID не найден.";
    private static final String NOT_FOUND_BY_EMAIL_MSG = "Пользователь с указанным email не найден.";

    private final UserValidator userValidator;
    private final UserRepository userRepository;

    public UserService() {
        userValidator = new UserValidator();
        userRepository = new UserRepository();
    }

    public User getUserById(int userId) {
        logger.trace("Вызван метод getUserById().");
        return userRepository.getUserById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BY_ID_MSG));
    }

    public User getUserByEmail(String userEmail) {
        logger.trace("Вызван метод getUserByEmail().");
        return userRepository.getUserByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BY_EMAIL_MSG));
    }

    public List<User> getUsers(Map<String, String> requestParams) {
        logger.trace("Вызван метод getUsers().");
        String sortBy = requestParams.remove("sortBy");
        String sortDir = requestParams.remove("sortDir");
        if (sortBy == null) sortBy = "id";
        if (sortDir == null) sortDir = "ASC";
        SelectStatementProvider selectStatement = UserFiltersBuilder.generateSelectStatement(requestParams, sortBy, sortDir);
        return userRepository.getUserList(selectStatement);
    }

    public void createNewUser(User userToSave) {
        logger.trace("Вызван метод createNewUser().");
        userValidator.validateUser(userToSave);
        userRepository.createNewUser(userToSave);
    }

    public void updateUserInfo(User updatedUser) {
        logger.trace("Вызван метод updateUserInfo().");
        userValidator.validateUser(updatedUser);
        userRepository.updateUserInfo(updatedUser);
    }

    public void deleteUserById(int userToDeleteId) {
        logger.trace("Вызван метод deleteUserById().");
        userRepository.deleteUserById(userToDeleteId);
    }
}
