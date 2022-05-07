package com.example.postservice.dto;

import com.example.postservice.model.Post;

import java.util.Date;
import java.util.List;

public class PostDTO {

    public String id;
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

    public PostDTO(String id, String ownerId, String title, String content, Date datePosted, Date dateEdited,
                   String image, String link, List<String> likes, List<String> dislikes) {
        this.id = id;
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

    public PostDTO(Post post){
        this.id = post.id;
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
