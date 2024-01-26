package dev.trifanya.service;

import dev.trifanya.model.User;
import dev.trifanya.mybatis.mapper.UserMapper;
import dev.trifanya.exception.NotFoundException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;
import java.util.List;
import java.io.IOException;

public class UserService {
    private final String NOT_FOUND_BY_ID_MSG = "Пользователь с указанным ID не найден.";
    private final String NOT_FOUND_BY_EMAIL_MSG = "Пользователь с указанным email не найден.";

    private UserMapper userMapper;

    public UserService() {
        try (Reader reader = Resources.getResourceAsReader("mybatis.xml")) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();
            //session.getConfiguration().addMapper(UserMapper.class);
            userMapper = session.getMapper(UserMapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int userId) {
        return userMapper.findUserById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BY_ID_MSG));
    }

    public User getUserByEmail(String userEmail) {
        return userMapper.findUserByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BY_EMAIL_MSG));
    }

    public List<User> getAllUsers() {
        return userMapper.findAllUsers();
    }

    public void createNewUser(User userToSave) {
        userMapper.saveUser(userToSave);
    }

    public void updateUserInfo(User updatedUser) {
        userMapper.updateUser(updatedUser);
    }

    public void deletaUserById(int userToDeleteId) {
        userMapper.deleteUserById(userToDeleteId);
    }
}
