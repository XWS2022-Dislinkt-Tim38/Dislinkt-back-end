package com.example.postservice.model;

import com.example.postservice.dto.PostDTO;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
public class Post {

    public String id;

    public String username;
    public String ownerId;
    public String title;
    public String content;
    public String image;

    public String link;
    public Date datePosted;
    public Date dateEdited;

    public List<String> likes;

    public List<String> dislikes;

    public Post() {}

    public Post(PostDTO postDTO){
        String id = java.util.UUID.randomUUID().toString();
        this.id = "post_" + id;
        this.ownerId = postDTO.ownerId;
        this.username = postDTO.username;
        this.title = postDTO.title;
        this.content = postDTO.content;
        this.datePosted = postDTO.datePosted;
        this.dateEdited = postDTO.dateEdited;
        this.image = postDTO.image;
        this.link  = postDTO.link;
        this.likes = postDTO.likes;
        this.dislikes = postDTO.dislikes;
    }
}
