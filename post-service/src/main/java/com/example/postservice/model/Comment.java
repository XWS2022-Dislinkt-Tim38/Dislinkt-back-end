package com.example.postservice.model;

import com.example.postservice.dto.CommentDTO;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Comment {

    public String id;
    public String userId;
    public String username;
    public String postId;
    public String content;
    public Date dateCreated;
    public Date dateEdited;

    public Comment(){}

    public Comment(String userId, String postId, String content, Date dateCreated, Date dateEdited){
        this.userId = userId;
        this.postId = postId;
        this.content = content;
        this.dateCreated = dateCreated;
        this.dateEdited = dateEdited;
    }

    public Comment(CommentDTO commentDTO){
        String id = java.util.UUID.randomUUID().toString();
        this.id = "comment_" + id;
        this.userId = commentDTO.userId;
        this.postId = commentDTO.postId;
        this.content = commentDTO.content;
        this.dateCreated = commentDTO.dateCreated;
        this.dateEdited = commentDTO.dateEdited;
    }

}
