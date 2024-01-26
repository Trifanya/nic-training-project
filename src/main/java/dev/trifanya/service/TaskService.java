package dev.trifanya.service;

import dev.trifanya.exception.NotFoundException;
import dev.trifanya.model.Task;
import dev.trifanya.mybatis.mapper.TaskMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

public class TaskService {
    private final String NOT_FOUND_MSG = "Задача с указанным ID не найдена.";

    private TaskMapper taskMapper;

    public TaskService() {
        try (Reader reader = Resources.getResourceAsReader("mybatis.xml")) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();
            //session.getConfiguration().addMapper(TaskMapper.class);
            taskMapper = session.getMapper(TaskMapper.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Task getTaskById(int taskId) {
        return taskMapper.findTaskById(taskId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MSG));
    }

    public List<Task> getAllTasks() {
        System.out.println("Method getAllTasks() was invoked...");
        return taskMapper.findAllTasks();
    }

    public List<Task> getFilteredTasks(Map<String, String> filters) {
        return taskMapper.findTasksBySelectStatement(TaskFiltersBuilder.generateSelectStatement(filters));
    }

    public void createNewTask(Task taskToSave) {
        taskMapper.saveTask(taskToSave);
    }

    public void updateTaskInfo(Task updatedTask) {
        taskMapper.updateTask(updatedTask);
    }

    public void deleteTaskById(int taskToDeleteId) {
        taskMapper.deleteTaskById(taskToDeleteId);
    }
}
