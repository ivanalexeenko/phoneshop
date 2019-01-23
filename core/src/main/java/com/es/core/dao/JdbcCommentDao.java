package com.es.core.dao;

import com.es.core.model.comment.Comment;
import com.es.core.model.comment.CommentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcCommentDao implements CommentDao {
    private static final String COMMENTS_TABLE_NAME = "comments";
    private static final String[] commentFieldNames = {"id", "phoneId", "name", "rating", "comment", "status"};
    private static final String SELECT_COMMENTS_QUERY = "select * from comments";
    private static final String SELECT_COMMENTS_WITH_ID_QUERY = "select * from comments where id = ?";
    private static final String UPDATE_STATUS_QUERY = "update comments set status = ? where id = ?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcCommentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveComment(Comment comment) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName(COMMENTS_TABLE_NAME);
        Object[] values = invokeCommentGetters(comment);
        Map<String, Object> parameters = new HashMap<>();
        putCommentParameters(comment, parameters, values);
        simpleJdbcInsert.execute(parameters);
    }

    @Override
    public void updateCommentStatus(Long commentId, CommentStatus status) {
        jdbcTemplate.update(UPDATE_STATUS_QUERY, status, commentId);
    }

    @Override
    public List<Comment> getComments() {
        return jdbcTemplate.query(SELECT_COMMENTS_QUERY, new Object[]{}, new BeanPropertyRowMapper<>(Comment.class));
    }

    @Override
    public List<Comment> getComments(Long id) {
        return jdbcTemplate.query(SELECT_COMMENTS_WITH_ID_QUERY, new Object[]{id}, new BeanPropertyRowMapper<>(Comment.class));
    }

    private Object[] invokeCommentGetters(Comment comment) {
        Object[] fieldValues = new Object[commentFieldNames.length];
        fieldValues[0] = comment.getId();
        fieldValues[1] = comment.getPhoneId();
        fieldValues[2] = comment.getName();
        fieldValues[3] = comment.getRating();
        fieldValues[4] = comment.getComment();
        fieldValues[5] = comment.getStatus();
        return fieldValues;
    }

    private void putCommentParameters(Comment comment, Map<String, Object> parameters, Object[] values) {
        for (int i = 0; i < values.length; i++) {
            parameters.put(commentFieldNames[i], values[i]);
        }
    }
}
