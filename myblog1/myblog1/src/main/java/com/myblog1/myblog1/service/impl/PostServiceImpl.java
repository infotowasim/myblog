package com.myblog1.myblog1.service.impl;

import com.myblog1.myblog1.entities.Post;
import com.myblog1.myblog1.exception.ResourceNotFoundException;
import com.myblog1.myblog1.payload.PostDTO;
import com.myblog1.myblog1.payload.PostResponse;
import com.myblog1.myblog1.repository.PostRepository;
import com.myblog1.myblog1.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    //@Autowired
    private PostRepository postRepository;
    private ModelMapper mapper;
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper){
        this.postRepository=postRepository;
        this.mapper=mapper;
    }

    //DTO to Entity
    Post mapToEntity(PostDTO postDTO){

        Post post = mapper.map(postDTO, Post.class);

//        Post post=new Post();
//        post.setTitle(postDTO.getTitle());
//        post.setDescription(postDTO.getDescription());
//        post.setContent(postDTO.getContent());

        return post;
    }

    //Entity to DTO
    PostDTO mapToDTO(Post post){

        PostDTO postDTO = mapper.map(post, PostDTO.class);

//        PostDTO postDTO=new PostDTO();
//        postDTO.setId(post.getId());
//        postDTO.setTitle(post.getTitle());
//        postDTO.setDescription(post.getDescription());
//        postDTO.setContent(post.getContent());

        return postDTO;
    }



    @Override
    public PostDTO createPost(PostDTO postDTO) { // DTO

        //DTO to Entity
        Post post=mapToEntity(postDTO); //Converted DTO to ENTITY
        Post newPost = postRepository.save(post); //save into the DATABASE

        //Entity to DTO
        PostDTO newPostDTO = mapToDTO(newPost); // Again converted into DTO

        return newPostDTO; // Return  DTO in postman
    }

//    @Override
//    public List<PostDTO> getAllPosts() {
//        List<Post> postList = postRepository.findAll();
//        List<PostDTO> postDTOList = postList.stream().map(s -> mapToDTO(s)).collect(Collectors.toList());
//
//        return postDTOList;
//    }



//    @Override
//    public List<PostDTO> getAllPosts(int pageNo, int pageSize){
//
//        //Pagination
//        Pageable pageable = PageRequest.of(pageNo, pageSize);
//
//        //normal way
//        Page<Post> postPage = postRepository.findAll(pageable);
//
//        List<Post> postList = postPage.getContent();
//
//        List<PostDTO> postDTOList = postList.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
//
//
//        return postDTOList;
//    }



//    @Override
//    public List<PostDTO> getAllPosts(int pageNo, int pageSize, String sortBy){
//
//
//
//        Pageable pageable = PageRequest.of(pageNo,pageSize,Sort.by(sortBy));
//        Page<Post> postPage = postRepository.findAll(pageable);
//        List<Post> postList = postPage.getContent();
//
//        List<PostDTO> postDTOList = postList.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
//
//        return postDTOList;
//    }


//    @Override
//    public List<PostDTO> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir){
//
//        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//
//        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
//        Page<Post> postPage = postRepository.findAll(pageable);
//        List<Post> postList = postPage.getContent();
//
//        List<PostDTO> postDTOList = postList.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
//        return postDTOList;
//    }


    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir){

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Post> postPage = postRepository.findAll(pageable);
        List<Post> postList = postPage.getContent(); //getContent(): it helps us to convert to list.

        //Response Return concepts will be started from here
        List<PostDTO> postDTOList = postList.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        //Object Creation of PostResponse Class
        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDTOList);
        postResponse.setPageNo(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setLast(postPage.isLast());

        return postResponse;
    }




    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        PostDTO postDTO = mapToDTO(post);

        return postDTO;
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post newPost = postRepository.save(post);

        PostDTO postDTO1 = mapToDTO(newPost);


        return postDTO1;
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));

       // postRepository.deleteById(id);
        postRepository.delete(post); // both are same but last one is good for work or practices.
    }




}
