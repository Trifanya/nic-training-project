package dev.trifanya.server_app.service;

import dev.trifanya.server_app.model.User;
import dev.trifanya.server_app.mybatis.mapper.UserMapper;
import dev.trifanya.server_app.exception.NotFoundException;
import dev.trifanya.server_app.mybatis.criteria_builder.UserFiltersBuilder;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.util.Map;
import java.util.List;
import java.io.Reader;
import java.util.ArrayList;
import java.io.IOException;

public class UserService {
    private final String NOT_FOUND_BY_ID_MSG = "Пользователь с указанным ID не найден.";
    private final String NOT_FOUND_BY_EMAIL_MSG = "Пользователь с указанным email не найден.";

    private UserMapper userMapper;
    private SqlSessionFactory sessionFactory;

    public UserService() {
        try (Reader reader = Resources.getResourceAsReader("mybatis.xml")) {
            sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int userId) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            userMapper = session.getMapper(UserMapper.class);
            return userMapper.findUserById(userId)
                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_BY_ID_MSG));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserByEmail(String userEmail) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            userMapper = session.getMapper(UserMapper.class);
            return userMapper.findUserByEmail(userEmail)
                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_BY_EMAIL_MSG));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> getUsers(Map<String, String> filters, String sortBy, String sortDir) {
        if (sortBy == null) sortBy = "id";
        if (sortDir == null) sortDir = "ASC";
        try (SqlSession session = sessionFactory.openSession(true)) {
            userMapper = session.getMapper(UserMapper.class);
            return userMapper.findUsersBySelectStatement(UserFiltersBuilder.generateSelectStatement(filters, sortBy, sortDir));
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void createNewUser(User userToSave) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            userMapper = session.getMapper(UserMapper.class);
            userMapper.saveUser(userToSave);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUserInfo(User updatedUser) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            userMapper = session.getMapper(UserMapper.class);
            userMapper.updateUser(updatedUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUserById(int userToDeleteId) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            userMapper = session.getMapper(UserMapper.class);
            userMapper.deleteUserById(userToDeleteId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
