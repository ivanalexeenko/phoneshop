package com.es.core.dao;

import com.es.core.model.comment.Comment;
import com.es.core.model.comment.CommentStatus;

import java.util.List;
import java.util.Optional;

public interface CommentDao {
    void saveComment(Comment comment);

    void updateCommentStatus(Long commentId, CommentStatus status);

    List<Comment> getComments();

    List<Comment> getComments(Long id);
}
