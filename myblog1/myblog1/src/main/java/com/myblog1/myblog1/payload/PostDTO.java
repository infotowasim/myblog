package com.myblog1.myblog1.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDTO {
    private long id;

    //Post title should not be null or empty.
    @NotEmpty(message = "It is Mandatory")
    @Size(min = 2, message = "Post title should have at least 2 character")
    private String title;

    //Post description should not be null or empty.
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 character")
    private String description;

    //Post content should not be null or empty.
    @NotEmpty
    @Size(min = 10, message = "Post content should have at least 10 character")
    private String content;
}
