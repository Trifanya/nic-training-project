package dev.trifanya.service;

import dev.trifanya.exception.NotFoundException;
import dev.trifanya.model.Task;
import dev.trifanya.mybatis.mapper.TaskMapper;
import dev.trifanya.mybatis.criteria_builder.TaskFiltersBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskService {
    private SqlSessionFactory sessionFactory;
    private TaskMapper taskMapper;

    private final String NOT_FOUND_MSG = "Задача с указанным ID не найдена.";

    public TaskService() {
        try (Reader reader = Resources.getResourceAsReader("mybatis.xml")) {
            sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Task getTaskById(int taskId) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            taskMapper = session.getMapper(TaskMapper.class);
            return taskMapper.findTaskById(taskId)
                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_MSG));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Task> getTasks(Map<String, String> filters, String sortBy, String sortDir) {
        if (sortBy == null) sortBy = "id";
        if (sortDir == null) sortDir = "ASC";
        try (SqlSession session = sessionFactory.openSession(true)) {
            taskMapper = session.getMapper(TaskMapper.class);
            return taskMapper.findTasksBySelectStatement(TaskFiltersBuilder.generateSelectStatement(filters, sortBy, sortDir));
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void createNewTask(Task taskToSave) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            taskMapper = session.getMapper(TaskMapper.class);
            taskMapper.saveTask(taskToSave.setCreatedAt(LocalDateTime.now()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTaskInfo(Task updatedTask) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            taskMapper = session.getMapper(TaskMapper.class);
            Task taskToUpdate = taskMapper.findTaskById(updatedTask.getId()).get();
            updatedTask.setCreatedAt(taskToUpdate.getCreatedAt());
            taskMapper.updateTask(updatedTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTaskById(int taskToDeleteId) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            taskMapper = session.getMapper(TaskMapper.class);
            taskMapper.deleteTaskById(taskToDeleteId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
