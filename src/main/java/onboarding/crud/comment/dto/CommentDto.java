package onboarding.crud.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String content;
    private String author; // 댓글 작성자의 정보
}
