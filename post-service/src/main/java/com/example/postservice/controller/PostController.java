package com.example.postservice.controller;

import com.example.postservice.dto.CommentDTO;
import com.example.postservice.dto.PostDTO;
import com.example.postservice.dto.UserDTO;
import com.example.postservice.model.Comment;
import com.example.postservice.service.PostService;
import com.example.postservice.service.common.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping(value = "/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("* Controller is working *", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {

        List<PostDTO> posts = postService.getAllPosts();

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/public")
    public ResponseEntity<List<PostDTO>> getAllPublicPosts() {

        List<PostDTO> posts = postService.loadAllPublicPosts();

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/feed/{idUser}")
    public ResponseEntity<List<PostDTO>> getFeed(@PathVariable String idUser) {

        List<PostDTO> posts = postService.getFeed(idUser);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/owner/{idUser}")
    public ResponseEntity<List<PostDTO>> getOwnerPosts(@PathVariable String idUser) {

        List<PostDTO> posts = postService.getAllPostsByOwner(idUser);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{postId}")
    public ResponseEntity<Object> getUserByPost(@PathVariable String postId){
        PostDTO postDTO = postService.getPost(postId);
        UserDTO userDTO = userFeignClient.getUser(postDTO.ownerId);
        if(userDTO != null)
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
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

    @PutMapping(value = "/like/{postId}")
    public ResponseEntity<Object> likePost(@PathVariable String postId, @RequestParam String userId){
        boolean status = postService.likePost(postId, userId);
        if(status)
            return new ResponseEntity<>("Post successfully liked!", HttpStatus.OK);
        else
            return new ResponseEntity<>("There was an error while like operation!", HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/dislike/{postId}")
    public ResponseEntity<Object> dislikePost(@PathVariable String postId, @RequestParam String userId){
        boolean status = postService.dislikePost(postId, userId);
        if(status)
            return new ResponseEntity<>("Post successfully disliked!", HttpStatus.OK);
        else
            return new ResponseEntity<>("There was an error while dislike operation!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/comment")
    public  ResponseEntity<Object> addComment(@RequestBody CommentDTO newCommentDTO){
        CommentDTO comment = postService.addComment(newCommentDTO);
        return new ResponseEntity<>(Objects.requireNonNullElse(comment, "Can not add comment!"), HttpStatus.OK);
    }

    @GetMapping(value = "/search/{ownerId}/{search}")
    public ResponseEntity<List<PostDTO>> getSearchedPosts(@PathVariable String ownerId, @PathVariable String search) {

        List<PostDTO> posts = postService.getSearchedPosts(ownerId, search);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

}
