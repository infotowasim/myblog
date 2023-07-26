package com.myblog1.myblog1.service.impl;

import com.myblog1.myblog1.entities.Comment;
import com.myblog1.myblog1.entities.Post;
import com.myblog1.myblog1.exception.BlogApiException;
import com.myblog1.myblog1.exception.ResourceNotFoundException;
import com.myblog1.myblog1.payload.CommentDTO;
import com.myblog1.myblog1.repository.CommentRepository;
import com.myblog1.myblog1.repository.PostRepository;
import com.myblog1.myblog1.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper mapper;


    // DTO to Entity
    private Comment mapToEntity(CommentDTO commentDTO){

        Comment comment = mapper.map(commentDTO, Comment.class);

//        Comment comment = new Comment();
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getBody());
//        comment.setBody(commentDTO.getBody());

        return comment;
    }


    //ENTITY to DTO
    private CommentDTO mapToDTO(Comment comment){

        CommentDTO commentDTO = mapper.map(comment, CommentDTO.class);

//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setId(comment.getId());
//        commentDTO.setName(comment.getName());
//        commentDTO.setEmail(comment.getEmail());
//        commentDTO.setBody(comment.getBody());
////        commentDTO.setPost(comment.getPost()); //Update Comment , U should remove this line when u want to do Update Comment.
//
////        commentDTO.setPost(comment.getPost()); // Get Comments By PostId korar somai hobe ata.

        return commentDTO;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {

        // Retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));

        //DTO to ENTITY
        Comment comment = mapToEntity(commentDTO);

        // Set Post to comment entity
        comment.setPost(post); // this post you save the comment
        Comment newComment = commentRepository.save(comment);

        //ENTITY to DTO or Comment Entity to DTO.
        CommentDTO commentDTO1 = mapToDTO(newComment);
        return commentDTO1;
    }





    // Get Comments By PostId
    @Override
    public List<CommentDTO> getCommentsByPostId(long postId){

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        //Retrieve Comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);

        // Convert ListOfComment Entity to ListOfComment DTO'S
        List<CommentDTO> commentDTOList = comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());

        return commentDTOList;
    }



    @Override
    public CommentDTO getCommentById(long postId, long commentId){

        //Retrieve Post Entity by Id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        //Retrieve Comment Entity by Id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));


        if (comment.getPost().getId()!=post.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapToDTO(comment);
    }



    //Update Comment
    @Override
    public CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO){

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

       if (comment.getPost().getId()!=post.getId()){
           throw new BlogApiException(HttpStatus.BAD_REQUEST, "Post not matching with comment");
       }


        //DTO to Entity
        comment.setId(commentId);
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());

        // save to DB
        Comment newComment = commentRepository.save(comment);

        // Entity to DTO
        CommentDTO commentDTO1 = mapToDTO(newComment);


        return commentDTO1;
    }


    // Delete Comment
    @Override
    public void deleteComment(long postId, long commentId){

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

        if (comment.getPost().getId()!=post.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Post not matching with comment");
        }

        commentRepository.deleteById(commentId);

    }
}
