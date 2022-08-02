package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.service.CommentService;

import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> save(@RequestBody AdsComment commentDTO) {
        return ResponseEntity.ok(commentService.createComment(commentDTO));
    }

    @GetMapping("/{pk}")
    public ResponseEntity<AdsComment> findByPk(@PathVariable(value = "pk") Integer pk) {
        AdsComment commentDTO = commentService.findCommentByPk(pk);
        if (commentDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(commentDTO);
    }

    @GetMapping
    public ResponseEntity<List<AdsComment>> findAllComments() {
        return ResponseEntity.ok(commentService.findAllComments());
    }

    @DeleteMapping("/{pk}")
    public ResponseEntity deleteByPk(@PathVariable(value = "pk") Integer pk) {
        commentService.deleteCommentByPk(pk);
        return ResponseEntity.ok().build();
    }


}
