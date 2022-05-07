package com.example.postservice.service;

import com.example.postservice.dto.PostDTO;
import com.example.postservice.model.Post;
import com.example.postservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostDTO getPost(String id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null)
            return new PostDTO(post);
        else
            return null;

    }

    public List<PostDTO> getAllPosts() {

        List<Post> posts = postRepository.findAll();
        List<PostDTO> postsDTO = new ArrayList<>();

        for (Post post : posts) {
            postsDTO.add(new PostDTO(post));
        }

        return postsDTO;
    }

    public List<PostDTO> getAllPostsByOwner(String ownerId) {

        List<Post> posts = postRepository.findAll();
        List<PostDTO> postsDTO = new ArrayList<>();

        for (Post post : posts) {
            if(post.ownerId.equals(ownerId))
             postsDTO.add(new PostDTO(post));
        }

        return postsDTO;
    }

    public PostDTO addPost(PostDTO newPostDTO) {

        Post newPost = new Post(newPostDTO);
        postRepository.save(newPost);
        return new PostDTO(newPost);
    }

    public boolean updatePost(PostDTO updatePostDTO) {
        boolean status = postRepository.existsById(updatePostDTO.id);

        if (status) {
            Post postToUpdate = postRepository.findById(updatePostDTO.id).orElse(null);
            assert postToUpdate != null;
            postToUpdate.dateEdited = new Date();
            postToUpdate.title = updatePostDTO.title;
            postToUpdate.content = updatePostDTO.content;
            postToUpdate.image = updatePostDTO.image;
            postToUpdate.link = updatePostDTO.link;
            postRepository.save(postToUpdate);
        }

        return status;

    }

    public boolean likePost(String postId, String userId) {
        boolean status = postRepository.existsById(postId);
        Post post = postRepository.findById(postId).orElse(null);
        if(status){
            if(!post.likes.contains(userId)) {
                if(post.dislikes.contains(userId)){
                    post.dislikes.remove(userId);
                }
                post.likes.add(userId);
                postRepository.save(post);
            }
            else {
                post.likes.remove(userId);
                postRepository.save(post);
            }
        }

        return status;
    }

    public boolean dislikePost(String postId, String userId) {
        boolean status = postRepository.existsById(postId);
        Post post = postRepository.findById(postId).orElse(null);
        if(status){
            if(!post.dislikes.contains(userId)) {
                if(post.likes.contains(userId)){
                    post.likes.remove(userId);
                }
                post.dislikes.add(userId);
                postRepository.save(post);
            }
            else{
                post.dislikes.remove(userId);
                postRepository.save(post);
            }
        }

        return status;
    }

    public boolean unreactOnPost(String postId, String userId) {
        boolean status = postRepository.existsById(postId);
        Post post = postRepository.findById(postId).orElse(null);
        if(status){
            if(post.dislikes.contains(userId)) {
                post.dislikes.remove(userId);
            }
            if(post.likes.contains(userId)){
                post.likes.remove(userId);
            }

            postRepository.save(post);
        }

        return status;
    }

    public boolean deletePost(String id) {
        boolean status = postRepository.existsById(id);
        if (status)
            postRepository.deleteById(id);

        return status;

    }

}
