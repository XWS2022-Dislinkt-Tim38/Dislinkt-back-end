package com.example.postservice.dto;

import com.example.postservice.model.Comment;

import java.util.Date;

public class CommentDTO {
    public String id;
    public String userId;
    public String postId;
    public String content;
    public Date dateCreated;
    public Date dateEdited;

    public CommentDTO(){}

    public CommentDTO(String userId, String postId, String content, Date dateCreated, Date dateEdited){
        this.userId = userId;
        this.postId = postId;
        this.content = content;
        this.dateCreated = dateCreated;
        this.dateEdited = dateEdited;
    }

    public CommentDTO(Comment comment){
        this.id = comment.id;
        this.userId = comment.userId;
        this.postId = comment.postId;
        this.content = comment.content;
        this.dateCreated = comment.dateCreated;
        this.dateEdited = comment.dateEdited;
    }
}
