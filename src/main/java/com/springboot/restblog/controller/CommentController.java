package com.springboot.restblog.controller;

import com.springboot.restblog.model.entity.CommentEntity;
import com.springboot.restblog.model.payload.CommentDTO;
import com.springboot.restblog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post/{postId}/comments")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping()
    public ResponseEntity<CommentDTO> createComment(@PathVariable(name = "postId") Integer idPost,
                                                    @RequestBody CommentDTO commentDTO) {
        CommentDTO commentResponse = commentService.saveComment(idPost, commentDTO);

        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(name = "id") Integer id,
                                                    @PathVariable(name = "postId") Integer idPost,
                                                    @RequestBody CommentDTO commentDTO) {
        commentDTO.setId(id);
        CommentDTO commentUpdate = commentService.saveComment(idPost, commentDTO);

        return new ResponseEntity<>(commentUpdate, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentByid(@PathVariable(name = "id") Integer id,
                                                     @PathVariable(name = "postId") Integer idPost) {
        CommentDTO commentResponse = commentService.getById(id, idPost);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @GetMapping()
    public List<CommentDTO> listCommentsByPostId(@PathVariable(name = "postId") Integer idPost) {
        List<CommentDTO> listResponse = commentService.getCommentsByPostId(idPost);
        return listResponse;
    }
}
