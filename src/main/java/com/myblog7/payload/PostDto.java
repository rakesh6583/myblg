package com.myblog7.payload;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {


    private Long id;

    @NotEmpty
    @Size(min=2, message="Post Character should be at least 2 Character")
    private String title;

    @NotEmpty
    @Size(min=4, message="Post Description should be at least 4 Character")
    private String description;

    @NotEmpty
    @Size(min=5, message="Post Content should be at least 5 Character")//max=10 also we can write
    private String content;
}
