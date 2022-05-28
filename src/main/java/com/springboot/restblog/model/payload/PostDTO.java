package com.springboot.restblog.model.payload;

import com.springboot.restblog.model.entity.CommentEntity;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDTO {

    private Integer id;

    @NotEmpty
    @Size(min = 2, max = 200, message = "Title must be minium 2 characters and maximum 200 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Description must be minium 10 characters")
    private String description;

    @NotEmpty(message = "Content should not be null or empty")
    private String content;

    private Set<CommentDTO> comments;
}
