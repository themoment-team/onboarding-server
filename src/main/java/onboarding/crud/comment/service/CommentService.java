package onboarding.crud.comment.service;

import onboarding.crud.comment.dto.CommentDto;
import onboarding.crud.comment.entity.Comment;
import onboarding.crud.comment.repository.CommentRepository;
import onboarding.crud.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public Comment createComment(Long postId, Comment comment) {
        return postRepository.findById(postId).map(post -> {
            comment.setPost(post);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new IllegalArgumentException("Post not found"));
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    private CommentDto convertToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setAuthor(comment.getAuthor());
        return commentDto;
    }

}
