package com.es.core.service;

import com.es.core.dao.CommentDao;
import com.es.core.model.comment.Comment;
import com.es.core.model.comment.CommentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public void addComment(Comment comment) {
        commentDao.saveComment(comment);
    }

    @Override
    public void updateStatus(Long commentId, CommentStatus status) {
        commentDao.updateCommentStatus(commentId, status);
    }

    @Override
    public List<Comment> getComments() {
        return commentDao.getComments();
    }

    @Override
    public List<Comment> getComments(Long id) {
        return commentDao.getComments(id);
    }
}
