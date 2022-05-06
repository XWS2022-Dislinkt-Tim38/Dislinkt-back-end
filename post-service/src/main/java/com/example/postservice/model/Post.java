package com.example.postservice.model;

import com.example.postservice.dto.PostDTO;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Post {

    public String id;
    public String ownerId;
    public String title;
    public String content;
    public Date datePosted;
    public Date dateEdited;

    public Post() {}

    public Post(String ownerId, String title, String content, Date datePosted, Date dateEdited) {
        this.ownerId = ownerId;
        this.title = title;
        this.content = content;
        this.datePosted = datePosted;
        this.dateEdited = dateEdited;
    }

    public Post(PostDTO postDTO){
        String id = java.util.UUID.randomUUID().toString();
        this.id = "post_" + id;
        this.ownerId = postDTO.ownerId;
        this.title = postDTO.title;
        this.content = postDTO.content;
        this.datePosted = new Date();
        this.dateEdited = new Date();
    }
}
