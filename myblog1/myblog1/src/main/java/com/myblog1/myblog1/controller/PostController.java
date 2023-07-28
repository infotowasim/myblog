package com.myblog1.myblog1.controller;

import com.myblog1.myblog1.payload.PostDTO;
import com.myblog1.myblog1.payload.PostResponse;
import com.myblog1.myblog1.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {


    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


//    //Create Post
//    // http://localhost:8080/api/posts
//    // Validation
//    @PostMapping
//    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO, BindingResult bindingResult){
//        PostDTO dto = postService.createPost(postDTO);
//        return new ResponseEntity<>(dto, HttpStatus.CREATED);
//    }


    //Create Post
    // http://localhost:8080/api/posts
    // Validation
    // 4th step of Security Configure
    // the user with the role ADMIN can access this. User can't access this.
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDTO postDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PostDTO dto = postService.createPost(postDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

//    //Get all posts
//    // http://localhost:8080/api/posts
//    @GetMapping
//    public ResponseEntity<List<PostDTO>> getAllPosts(){
//        List<PostDTO> allPosts = postService.getAllPosts();
//        // return new ResponseEntity<>(allPosts,HttpStatus.OK);
//        return ResponseEntity.ok(allPosts);
//    }


//    // http://localhost:8080/api/posts?pageNo=0&pageSize=10
//    @GetMapping
//    public ResponseEntity<List<PostDTO>> getAllPosts(  // Pagination
//         @RequestParam(value = "pageNo", defaultValue = "0", required = false)  int pageNo,
//         @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
//    ){
//        List<PostDTO> postDTOList = postService.getAllPosts(pageNo, pageSize);
//        return ResponseEntity.ok(postDTOList);
//    }


//    //Pagination with Sorting:- sorting will be happened only for String value like Alphabets.
//    // http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortby=title
//    @GetMapping
//    public ResponseEntity<List<PostDTO>> getAllPosts(
//            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
//            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
//            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
//    ){
//      return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy)) ;
//
//    }


    //Pagination with sorting with Ascending order and get total pageNo, total page size etc.
    // http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortby=title&sortdir=asc
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        PostResponse postDTOList = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(postDTOList);
    }


    //Get post by id
    // http://localhost:8080/api/posts/1
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable("id") long id) {
        PostDTO postDTO = postService.getPostById(id);
        //  return new ResponseEntity<>(postDTO,HttpStatus.OK);
        return ResponseEntity.ok(postDTO);
    }

    //Update post
    // http://localhost:8080/api/posts/2
    // 4th step of Security Configure.
    // Only ADMIN can update the record not USER.
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable("id") long id) {
        PostDTO postDTO1 = postService.updatePost(postDTO, id);
        // return new ResponseEntity<>(postDTO1,HttpStatus.OK);
        return ResponseEntity.ok(postDTO1);
    }


    //Delete Post
    // http://localhost:8080/api/posts/3
    // 4th step of Security Configure.
    // Only ADMIN can Delete the record not USER.
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("post entity deleted successfully");
    }


}
