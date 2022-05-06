package com.example.postservice.dto;

import com.example.postservice.model.Post;

import java.util.Date;

public class PostDTO {

    public String id;
    public String ownerId;
    public String title;
    public String content;
    public Date datePosted;
    public Date dateEdited;

    public PostDTO() {}

    public PostDTO(String id, String ownerId, String title, String content, Date datePosted, Date dateEdited) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.content = content;
        this.datePosted = datePosted;
        this.dateEdited = dateEdited;
    }

    public PostDTO(Post post){
        this.id = post.id;
        this.ownerId = post.ownerId;
        this.title = post.title;
        this.content = post.content;
        this.datePosted = post.datePosted;
        this.dateEdited = post.dateEdited;
    }

}
