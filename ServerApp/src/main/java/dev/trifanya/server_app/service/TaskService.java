package dev.trifanya.server_app.service;

import dev.trifanya.server_app.ServerApp;
import dev.trifanya.server_app.model.Task;
import dev.trifanya.server_app.mybatis.mapper.TaskMapper;
import dev.trifanya.server_app.exception.NotFoundException;
import dev.trifanya.server_app.mybatis.criteria_builder.TaskFiltersBuilder;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.List;
import java.io.Reader;
import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class TaskService {
    private static final Logger logger = ServerApp.logger;

    private TaskMapper taskMapper;
    private SqlSessionFactory sessionFactory;

    private final String NOT_FOUND_MSG = "Задача с указанным ID не найдена.";

    public TaskService() {
        try (Reader reader = Resources.getResourceAsReader("mybatis.xml")) {
            sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Task getTaskById(int taskId) {
        logger.trace("TaskService: Вызван метод getTaskById().");
        try (SqlSession session = sessionFactory.openSession(true)) {
            taskMapper = session.getMapper(TaskMapper.class);
            return taskMapper.findTaskById(taskId)
                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_MSG));
        } catch (Exception e) {
            logger.error("UserService: Произошла ошибка при попытке получить задачу из БД по id.");
            e.printStackTrace();
            return null;
        }
    }

    public List<Task> getTasks(Map<String, String> filters, String sortBy, String sortDir) {
        logger.trace("TaskService: Вызван метод getTasks().");
        if (sortBy == null) sortBy = "id";
        if (sortDir == null) sortDir = "ASC";
        try (SqlSession session = sessionFactory.openSession(true)) {
            taskMapper = session.getMapper(TaskMapper.class);
            return taskMapper.findTasksBySelectStatement(TaskFiltersBuilder.generateSelectStatement(filters, sortBy, sortDir));
        } catch (Exception e) {
            logger.error("UserService: Произошла ошибка при попытке получить список задач из БД.");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void createNewTask(Task taskToSave) {
        logger.trace("TaskService: Вызван метод createNewTask().");
        try (SqlSession session = sessionFactory.openSession(true)) {
            taskMapper = session.getMapper(TaskMapper.class);
            taskMapper.saveTask(taskToSave.setCreatedAt(LocalDateTime.now()));
        } catch (Exception e) {
            logger.error("UserService: Произошла ошибка при попытке получить записать новую задачу в БД.");
            e.printStackTrace();
        }
    }

    public void updateTaskInfo(Task updatedTask) {
        logger.trace("TaskService: Вызван метод updateTaskInfo().");
        try (SqlSession session = sessionFactory.openSession(true)) {
            taskMapper = session.getMapper(TaskMapper.class);
            Task taskToUpdate = taskMapper.findTaskById(updatedTask.getId()).get();
            updatedTask.setCreatedAt(taskToUpdate.getCreatedAt());
            taskMapper.updateTask(updatedTask);
        } catch (Exception e) {
            logger.error("UserService: Произошла ошибка при попытке обновить данные о задаче в БД.");
            e.printStackTrace();
        }
    }

    public void deleteTaskById(int taskToDeleteId) {
        logger.trace("TaskService: Вызван метод deleteTaskById().");
        try (SqlSession session = sessionFactory.openSession(true)) {
            taskMapper = session.getMapper(TaskMapper.class);
            taskMapper.deleteTaskById(taskToDeleteId);
        } catch (Exception e) {
            logger.error("UserService: Произошла ошибка при попытке удалить задачу из БД.");
            e.printStackTrace();
        }
    }
}
