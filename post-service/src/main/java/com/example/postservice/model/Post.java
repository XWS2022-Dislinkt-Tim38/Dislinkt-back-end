package com.example.postservice.model;

import com.example.postservice.dto.PostDTO;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
public class Post {

    public String id;
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

    public Post(String ownerId, String title, String content, Date datePosted, Date dateEdited, String image,
                String link, List<String> likes, List<String> dislikes) {
        this.ownerId = ownerId;
        this.title = title;
        this.content = content;
        this.datePosted = datePosted;
        this.dateEdited = dateEdited;
        this.image = image;
        this.link = link;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public Post(PostDTO postDTO){
        String id = java.util.UUID.randomUUID().toString();
        this.id = "post_" + id;
        this.ownerId = postDTO.ownerId;
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
