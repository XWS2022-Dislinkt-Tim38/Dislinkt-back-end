package com.example.postservice.service;

import com.example.postservice.dto.CommentDTO;
import com.example.postservice.dto.PostDTO;
import com.example.postservice.model.Comment;
import com.example.postservice.model.Post;
import com.example.postservice.repository.CommentRepository;
import com.example.postservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public interface PostService {

     PostDTO getPost(String id);
     List<PostDTO> getAllPosts();
     List<PostDTO> getAllPostsByOwner(String ownerId);
     PostDTO addPost(PostDTO newPostDTO);
     boolean updatePost(PostDTO updatePostDTO);
     boolean likePost(String postId, String userId);
     boolean dislikePost(String postId, String userId);
     CommentDTO addComment(CommentDTO newCommentDTO);
     boolean deletePost(String id);
     List<PostDTO> loadAllPublicPosts();
     List<PostDTO> getFeed(String userId);

     List<PostDTO> getSearchedPosts(String ownerId, String search);

}
