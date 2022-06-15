package com.example.postservice.controller;

import com.example.postservice.dto.CommentDTO;
import com.example.postservice.dto.PostDTO;
import com.example.postservice.dto.UserDTO;
import com.example.postservice.model.Comment;
import com.example.postservice.service.PostService;
import com.example.postservice.service.common.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(PostController.class);
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
        logger.info("GET REQUEST /post");
        List<PostDTO> posts = postService.getAllPosts();

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/public")
    public ResponseEntity<List<PostDTO>> getAllPublicPosts() {
        logger.info("GET REQUEST /post/public");
        List<PostDTO> posts = postService.loadAllPublicPosts();

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/feed/{idUser}")
    public ResponseEntity<List<PostDTO>> getFeed(@PathVariable String idUser) {
        logger.info("GET REQUEST /post/feed/{idUser}");
        List<PostDTO> posts = postService.getFeed(idUser);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/owner/{idUser}")
    public ResponseEntity<List<PostDTO>> getOwnerPosts(@PathVariable String idUser) {
        logger.info("GET REQUEST /post/owner/{idUser}");
        List<PostDTO> posts = postService.getAllPostsByOwner(idUser);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{postId}")
    public ResponseEntity<Object> getUserByPost(@PathVariable String postId){
        logger.info("GET REQUEST /user/{postId}");
        PostDTO postDTO = postService.getPost(postId);
        UserDTO userDTO = userFeignClient.getUser(postDTO.ownerId);
        if(userDTO != null)
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{ownerId}")
    public ResponseEntity<Object> getUserById(@PathVariable String ownerId){
        logger.info("(feign) GET REQUEST /post/{ownerId}");
        List<PostDTO> postsDTO = postService.getAllPostsByOwner(ownerId);
        if(postsDTO != null)
            return new ResponseEntity<>(postsDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>("No posts found!", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Object> addPost(@RequestBody PostDTO newPostDTO){
        logger.info("POST REQUEST /post");
        PostDTO post = postService.addPost(newPostDTO);
        return new ResponseEntity<>(Objects.requireNonNullElse(post, "Can not add post!"), HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<Object> updatePost(@RequestBody PostDTO editPostDTO){
        logger.info("PUT REQUEST /post");
        boolean status = postService.updatePost(editPostDTO);
        if(status)
            return new ResponseEntity<>("Post successfully updated!", HttpStatus.OK);
        else
            return new ResponseEntity<>("There was an error while updating!", HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String id){
        logger.info("DELETE REQUEST /post/{id}");
        boolean status = postService.deletePost(id);
        if(status)
            return new ResponseEntity<>("Post successfully deleted!", HttpStatus.OK);
        else
            return new ResponseEntity<>("Post not found!", HttpStatus.NOT_FOUND);

    }

    @PutMapping(value = "/like/{postId}/{userId}")
    public ResponseEntity<Object> likePost(@PathVariable String postId, @PathVariable String userId){
        logger.info("PUT REQUEST /post/like/{postId}/{userId}");
        boolean status = postService.likePost(postId, userId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping(value = "/dislike/{postId}/{userId}")
    public ResponseEntity<Object> dislikePost(@PathVariable String postId, @PathVariable String userId){
        logger.info("PUT REQUEST /post/dislike/{postId}/{userId}");
        boolean status = postService.dislikePost(postId, userId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping(value = "/comment")
    public  ResponseEntity<Object> addComment(@RequestBody CommentDTO newCommentDTO){
        logger.info("POST REQUEST /post/comment");
        CommentDTO comment = postService.addComment(newCommentDTO);
        return new ResponseEntity<>(Objects.requireNonNullElse(comment, "Can not add comment!"), HttpStatus.OK);
    }

    @GetMapping(value = "/search/{ownerId}/{search}")
    public ResponseEntity<List<PostDTO>> getSearchedPosts(@PathVariable String ownerId, @PathVariable String search) {
        logger.info("GET REQUEST /post/search/{ownerId}/{search}");
        List<PostDTO> posts = postService.getSearchedPosts(ownerId, search);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

}
