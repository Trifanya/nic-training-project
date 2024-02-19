package dev.trifanya.swing_crudapp.mybatis.mapper;

import dev.trifanya.swing_crudapp.model.Task;
import dev.trifanya.swing_crudapp.model.User;
import org.apache.ibatis.annotations.*;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

import java.util.List;
import java.util.Optional;

public interface TaskMapper {
    String FIND_USER_BY_ID_METHOD_NAME = "dev.trifanya.swing_crudapp.mybatis.mapper.UserMapper.findUserById";

    @Select("SELECT * FROM task")
    @Results(id="TaskResult", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "status", column = "status"),
            @Result(property = "priority", column = "priority"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "deadline", column = "deadline"),
            @Result(property = "author", javaType = User.class,
                    column = "author_id", one=@One(select = FIND_USER_BY_ID_METHOD_NAME)),
            @Result(property = "performer", javaType = User.class,
                    column = "performer_id", one=@One(select = FIND_USER_BY_ID_METHOD_NAME))
    })
    List<Task> findAllTasks();

    @Select("SELECT * FROM task WHERE author_id = #{authorId}")
    @ResultMap("TaskResult")
    List<Task> findTasksByAuthorId(long authorId);

    @Select("SELECT * FROM task WHERE performer_id = #{performerId}")
    @ResultMap("TaskResult")
    List<Task> findTasksByPerformerId(long performerId);

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @ResultMap("TaskResult")
    List<Task> findTasksBySelectStatement(SelectStatementProvider selectStatement);

    @Select("SELECT * FROM task WHERE id = #{id}")
    @ResultMap("TaskResult")
    Optional<Task> findTaskById(long id);

    @Insert("INSERT INTO task (title, description, status, priority, created_at, deadline, author_id, performer_id)" +
            "VALUES (#{title}, #{description}, #{status}, #{priority}, #{createdAt}, #{deadline}, #{author.id}, #{performer.id})")
    void saveTask(Task taskToSave);

    @Update("UPDATE task SET title = #{title}, description = #{description}, status = #{status}, " +
            "priority = #{priority}, created_at = #{createdAt}, deadline = #{deadline}, " +
            "author_id = #{author.id}, performer_id = #{performer.id} WHERE id = #{id}")
    void updateTask(Task updatedTask);

    @Delete("DELETE FROM task WHERE id = #{id}")
    void deleteTaskById(int id);
}
