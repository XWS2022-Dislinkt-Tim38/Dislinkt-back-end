package com.example.postservice.controller;

import com.example.postservice.dto.PostDTO;
import com.example.postservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping(value = "/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("* Controller is working *", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllUsers() {

        List<PostDTO> posts = postService.getAllPosts();

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/{ownerId}")
    public ResponseEntity<Object> getUserById(@PathVariable String ownerId){

        List<PostDTO> postsDTO = postService.getAllPostsByOwner(ownerId);
        if(postsDTO != null)
            return new ResponseEntity<>(postsDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>("No posts found!", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Object> addPost(@RequestBody PostDTO newPostDTO){
        PostDTO post = postService.addPost(newPostDTO);
        return new ResponseEntity<>(Objects.requireNonNullElse(post, "Can not add post!"), HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<Object> updatePost(@RequestBody PostDTO editPostDTO){
        boolean status = postService.updatePost(editPostDTO);
        if(status)
            return new ResponseEntity<>("Post successfully updated!", HttpStatus.OK);
        else
            return new ResponseEntity<>("There was an error while updating!", HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String id){
        boolean status = postService.deletePost(id);
        if(status)
            return new ResponseEntity<>("Post successfully deleted!", HttpStatus.OK);
        else
            return new ResponseEntity<>("Post not found!", HttpStatus.NOT_FOUND);

    }
}
