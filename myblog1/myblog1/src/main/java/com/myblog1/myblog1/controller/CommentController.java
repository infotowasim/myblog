package com.myblog1.myblog1.controller;

import com.myblog1.myblog1.payload.CommentDTO;
import com.myblog1.myblog1.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/posts")
public class CommentController {


    private CommentService commentService;

    public CommentController (CommentService commentService){
        this.commentService=commentService;
    }


//    // http://localhost:8080/api/posts/1/comments
//
//    @PostMapping("/{postId}/comments")
//    public ResponseEntity<CommentDTO> createComment(@PathVariable("postId") long postId, @RequestBody CommentDTO commentDTO){
//
//        CommentDTO commentDTO1 = commentService.createComment(postId, commentDTO);
//        return new ResponseEntity<>(commentDTO1, HttpStatus.CREATED);
//
//    }


    // http://localhost:8080/api/posts/1/comments

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Object> createComment(@Valid @PathVariable("postId") long postId, @RequestBody CommentDTO commentDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        CommentDTO commentDTO1 = commentService.createComment(postId, commentDTO);
        return new ResponseEntity<>(commentDTO1, HttpStatus.CREATED);

    }


    // Get Comments by Post Id
    // http://localhost:8080/api/posts/1/comments

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable("postId") long postId){
        List<CommentDTO> commentDTOList = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(commentDTOList,HttpStatus.OK);
    }


    // Get Comment by Id
    // http://localhost:8080/api/posts/1/comments/1

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable("postId") long postId, @PathVariable("commentId") long commentId){

        CommentDTO commentById = commentService.getCommentById(postId, commentId);

        return new ResponseEntity<>(commentById,HttpStatus.OK);
    }


    // Update Comment
    // http://localhost:8080/api/posts/1/comments/1
    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable("postId") long postId, @PathVariable("commentId") long commentId, @RequestBody CommentDTO commentDTO){

        CommentDTO commentDTO1 = commentService.updateComment(postId, commentId, commentDTO);
        return new ResponseEntity<>(commentDTO1,HttpStatus.OK);
    }


    // Delete Comment
    // http://localhost:8080/api/posts/1/comments/1
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId, @PathVariable("commentId") long commentId){
        commentService.deleteComment(postId,commentId);
//        return ResponseEntity.ok("Comment Deleted Successfully");
        return new ResponseEntity<>("Comment Deleted Successfully",HttpStatus.OK);
    }
}
