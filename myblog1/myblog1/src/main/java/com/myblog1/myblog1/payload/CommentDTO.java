package com.myblog1.myblog1.payload;

import com.myblog1.myblog1.entities.Post;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDTO {
    private long id;

    @NotEmpty(message = "It is Mandatory")
    @Size(min = 4, message = "Post name should have at least 4 character")
    private String name;

    @NotEmpty(message = "It is Mandatory")
    @Email
    private String email;

    @NotEmpty(message = "It is Mandatory")
    @Size(min = 10, message = "Post name should have at least 10 character")
    private String body;

    private Post post;

}
