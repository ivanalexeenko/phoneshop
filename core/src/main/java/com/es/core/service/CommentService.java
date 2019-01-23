package com.es.core.service;

import com.es.core.model.comment.Comment;
import com.es.core.model.comment.CommentStatus;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);

    void updateStatus(Long commentId, CommentStatus status);

    List<Comment> getComments();

    List<Comment> getComments(Long id);
}
