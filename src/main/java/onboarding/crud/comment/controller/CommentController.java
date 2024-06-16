package onboarding.crud.comment.controller;

import onboarding.crud.comment.dto.CommentDto;
import onboarding.crud.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/post/{postId}/comments")
public class CommentController {
    //todo:session authorization
    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<CommentDto> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }
    @PostMapping
    public CommentDto createComment(@PathVariable Long postId, @RequestBody CommentDto commentDto) {
        //todo:initialize field
        return commentService.createComment(postId, commentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
