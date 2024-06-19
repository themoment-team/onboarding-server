package onboarding.crud.post.dto;

import lombok.Getter;
import lombok.Setter;
import onboarding.crud.comment.dto.CommentDto;

import java.util.List;

@Getter
@Setter
public class PostWithCommentsDto {
    private PostDto post;
    private List<CommentDto> comments;
}
