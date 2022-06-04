package com.example.postservice.service.implementation;

import com.example.postservice.dto.CommentDTO;
import com.example.postservice.dto.PostDTO;
import com.example.postservice.dto.UserDTO;
import com.example.postservice.model.Comment;
import com.example.postservice.model.Post;
import com.example.postservice.repository.CommentRepository;
import com.example.postservice.repository.PostRepository;
import com.example.postservice.service.PostService;
import com.example.postservice.service.common.UserFeignClient;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserFeignClient userFeignClient;


    @Override
    public PostDTO getPost(String id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null)
            return new PostDTO(post);
        else
            return null;
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postsDTO = new ArrayList<>();

        for (Post post : posts) {
            postsDTO.add(new PostDTO(post));
        }

        return postsDTO;
    }

    @Override
    public List<PostDTO> getAllPostsByOwner(String ownerId) {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postsDTO = new ArrayList<>();

        for (Post post : posts) {
            if(post.ownerId.equals(ownerId))
                postsDTO.add(new PostDTO(post));
        }

        return postsDTO;
    }

    @Override
    public PostDTO addPost(PostDTO newPostDTO) {
        Post newPost = new Post(newPostDTO);
        postRepository.save(newPost);
        return new PostDTO(newPost);
    }

    @Override
    public boolean updatePost(PostDTO updatePostDTO) {
        boolean status = postRepository.existsById(updatePostDTO.id);

        if (status) {
            Post postToUpdate = postRepository.findById(updatePostDTO.id).orElse(null);
            assert postToUpdate != null;
            postToUpdate.dateEdited = new Date();
            postToUpdate.username = updatePostDTO.username;
            postToUpdate.title = updatePostDTO.title;
            postToUpdate.content = updatePostDTO.content;
            postToUpdate.image = updatePostDTO.image;
            postToUpdate.link = updatePostDTO.link;
            postRepository.save(postToUpdate);
        }

        return status;
    }

    @Override
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

    @Override
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

    @Override
    public CommentDTO addComment(CommentDTO newCommentDTO) {
        Comment newComment = new Comment(newCommentDTO);
        commentRepository.save(newComment);
        return new CommentDTO(newComment);
    }

    @Override
    public boolean deletePost(String id) {
        boolean status = postRepository.existsById(id);
        if (status)
            postRepository.deleteById(id);

        return status;
    }

    @Override
    public List<PostDTO> loadAllPublicPosts() {
        List<Post> allPosts = postRepository.findAll();
        List<PostDTO> publicPosts = new ArrayList<>();

        for(Post post : allPosts){
            UserDTO owner = userFeignClient.getUser(post.ownerId);
            if(owner.isPublic){
                publicPosts.add(new PostDTO(post));
            }
        }

        return publicPosts;
    }

    @Override
    public List<PostDTO> getFeed(String userId) {
        List<PostDTO> feed = new ArrayList<>();
        UserDTO owner = userFeignClient.getUser(userId);

        for(String followingUserId : owner.following)
        {
            List<PostDTO> followingPosts = getAllPostsByOwner(followingUserId);
            feed.addAll(followingPosts);
        }

        return feed;
    }
}
