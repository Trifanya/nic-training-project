package dev.trifanya.swing_crudapp.mybatis.mapper;

import dev.trifanya.swing_crudapp.model.User;
import org.apache.ibatis.annotations.*;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

import java.util.List;
import java.util.Optional;

public interface UserMapper {

    @Select("SELECT * FROM tms_user WHERE id = #{id}")
    Optional<User> findUserById(int id);

    @Select("SELECT * FROM tms_user WHERE email = #{email}")
    Optional<User> findUserByEmail(String email);

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    List<User> findUsersBySelectStatement(SelectStatementProvider selectStatement);

    @Insert("INSERT INTO tms_user (name, surname, position, email, password)" +
            "VALUES (#{name}, #{surname}, #{position}, #{email}, #{password})")
    void saveUser(User userToSave);

    @Update("UPDATE tms_user SET name = #{name}, surname = #{surname}, " +
            "position = #{position}, email = #{email}, password = #{password}")
    void updateUser(User updatedUser);

    @Delete("DELETE FROM tms_user WHERE id = #{id}")
    void deleteUserById(long id);
}
