package onboarding.crud.comment.service;

import onboarding.crud.comment.dto.CommentDto;
import onboarding.crud.comment.entity.Comment;
import onboarding.crud.comment.repository.CommentRepository;
import onboarding.crud.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public List<CommentDto> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CommentDto createComment(Long postId, CommentDto commentDto) {
        return postRepository.findById(postId).map(post -> {
            Comment comment = new Comment();
            comment.setContent(commentDto.getContent());
            comment.setAuthor(commentDto.getAuthor());
            comment.setPost(post);
            Comment savedComment = commentRepository.save(comment);
            return convertToDto(savedComment);
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
