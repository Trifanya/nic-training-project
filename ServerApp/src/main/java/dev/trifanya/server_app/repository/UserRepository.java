package dev.trifanya.server_app.repository;

import dev.trifanya.server_app.model.User;
import dev.trifanya.server_app.service.UserService;
import dev.trifanya.server_app.mybatis.mapper.UserMapper;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import java.util.List;
import java.io.Reader;
import java.util.Optional;
import java.io.IOException;

public class UserRepository {
    private static final Logger logger = LogManager.getLogger(UserService.class);

    private UserMapper userMapper;
    private SqlSessionFactory sessionFactory;

    public UserRepository() {
        try (Reader reader = Resources.getResourceAsReader("mybatis.xml")) {
            sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException exception) {
            logger.error("Произошла ошибка при попытке инициализировать объект SqlSessionFactory.", exception);
        }
    }

    public Optional<User> getUserById(int userId) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            userMapper = session.getMapper(UserMapper.class);
            return userMapper.findUserById(userId);
        }
    }

    public Optional<User> getUserByEmail(String userEmail) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            userMapper = session.getMapper(UserMapper.class);
            return userMapper.findUserByEmail(userEmail);
        }
    }

    public List<User> getUserList(SelectStatementProvider selectStatement) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            userMapper = session.getMapper(UserMapper.class);
            return userMapper.findUsersBySelectStatement(selectStatement);
        }
    }

    public void createNewUser(User userToSave) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            userMapper = session.getMapper(UserMapper.class);
            userMapper.saveUser(userToSave);
        }
    }

    public void updateUserInfo(User updatedUser) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            userMapper = session.getMapper(UserMapper.class);
            userMapper.updateUser(updatedUser);
        }
    }

    public void deleteUserById(int userToDeleteId) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            userMapper = session.getMapper(UserMapper.class);
            userMapper.deleteUserById(userToDeleteId);
        }
    }
}
