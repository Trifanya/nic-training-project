package dev.trifanya.server_app.repository;

import dev.trifanya.server_app.model.Task;
import dev.trifanya.server_app.service.UserService;
import dev.trifanya.server_app.mybatis.mapper.TaskMapper;

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
import java.time.LocalDateTime;

public class TaskRepository {
    private static final Logger logger = LogManager.getLogger(UserService.class);

    private TaskMapper taskMapper;
    private SqlSessionFactory sessionFactory;

    public TaskRepository() {
        try (Reader reader = Resources.getResourceAsReader("mybatis.xml")) {
            sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException exception) {
            logger.error("Произошла ошибка при попытке инициализировать объект SqlSessionFactory.", exception);
        }
    }

    public Optional<Task> getTaskById(int taskId) {
        logger.trace("Вызван метод getTaskById().");
        try (SqlSession session = sessionFactory.openSession(true)) {
            taskMapper = session.getMapper(TaskMapper.class);
            return taskMapper.findTaskById(taskId);
        }
    }

    public List<Task> getTaskList(SelectStatementProvider selectStatement) {
        logger.trace("Вызван метод getTasks().");
        try (SqlSession session = sessionFactory.openSession(true)) {
            taskMapper = session.getMapper(TaskMapper.class);
            return taskMapper.findTasksBySelectStatement(selectStatement);
        }
    }

    public void createNewTask(Task taskToSave) {
        logger.trace("Вызван метод createNewTask().");
        try (SqlSession session = sessionFactory.openSession(true)) {
            taskMapper = session.getMapper(TaskMapper.class);
            taskMapper.saveTask(taskToSave.setCreatedAt(LocalDateTime.now()));
        }
    }

    public void updateTaskInfo(Task updatedTask) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            taskMapper = session.getMapper(TaskMapper.class);
            taskMapper.updateTask(updatedTask);
        }
    }

    public void deleteTaskById(int taskToDeleteId) {
        logger.trace("Вызван метод deleteTaskById().");
        try (SqlSession session = sessionFactory.openSession(true)) {
            taskMapper = session.getMapper(TaskMapper.class);
            taskMapper.deleteTaskById(taskToDeleteId);
        }
    }
}
