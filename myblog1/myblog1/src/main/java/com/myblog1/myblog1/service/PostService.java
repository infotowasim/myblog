package com.myblog1.myblog1.service;

import com.myblog1.myblog1.payload.PostDTO;
import com.myblog1.myblog1.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO);

//    List<PostDTO> getAllPosts();
//    List<PostDTO> getAllPosts(int pageNo, int pageSize);
//List<PostDTO> getAllPosts(int pageNo, int pageSize, String sortBy);
//    List<PostDTO> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDTO getPostById(long id);

    PostDTO updatePost(PostDTO postDTO, long id);

    void deletePost(long id);


}
