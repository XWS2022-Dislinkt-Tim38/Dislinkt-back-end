package com.example.postservice.dto;

import com.example.postservice.model.Post;

import java.util.Date;
import java.util.List;

public class PostDTO {

    public String id;
    public String username;
    public String ownerId;
    public String title;
    public String content;

    public String image;

    public String link;

    public List<String> likes;

    public List<String> dislikes;
    public Date datePosted;
    public Date dateEdited;

    public PostDTO() {}
    public PostDTO(Post post){
        this.id = post.id;
        this.username = post.username;
        this.ownerId = post.ownerId;
        this.title = post.title;
        this.content = post.content;
        this.datePosted = post.datePosted;
        this.dateEdited = post.dateEdited;
        this.image = post.image;
        this.link = post.link;
        this.likes = post.likes;
        this.dislikes = post.dislikes;
    }

}
