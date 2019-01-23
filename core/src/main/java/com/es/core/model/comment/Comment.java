package com.es.core.model.comment;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Comment {
    private Long id;
    private Long phoneId;
    private CommentStatus status;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String comment;

    @NotNull
    @Size(min = 1, max = 5)
    private Integer rating;

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public void setStatus(CommentStatus status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public Integer getRating() {
        return rating;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public CommentStatus getStatus() {
        return status;
    }
}