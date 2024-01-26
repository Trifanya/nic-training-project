package dev.trifanya.service;

import dev.trifanya.exception.NotFoundException;
import dev.trifanya.model.Comment;
import dev.trifanya.mybatis.mapper.CommentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class CommentService {
    private final String NOT_FOUND_MSG = "Комментарий с указанным ID не найден.";

    private CommentMapper commentMapper;

    public CommentService()  {
        try (Reader reader = Resources.getResourceAsReader("mybatis.xml")) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();
            //session.getConfiguration().addMapper(CommentMapper.class);
            commentMapper = session.getMapper(CommentMapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Comment getCommentById(int commentId) {
        return commentMapper.findCommentById(commentId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MSG));
    }

    public List<Comment> getAllComments() {
        return commentMapper.findAllComments();
    }

    public List<Comment> getAllCommentsByTaskId(int taskId) {
        return commentMapper.findAllCommentsByTaskId(taskId);
    }

    public void createNewComment(Comment commentToSave) {
        commentMapper.saveComment(commentToSave);
    }

    public void updateCommentInfo(Comment updatedComment) {
        commentMapper.updateComment(updatedComment);
    }

    public void deletaCommentById(int commentToDeleteId) {
        commentMapper.deleteCommentById(commentToDeleteId);
    }
}
