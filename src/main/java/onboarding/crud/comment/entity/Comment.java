package onboarding.crud.comment.entity;

import jakarta.persistence.*;
import lombok.Data;
import onboarding.crud.post.entity.Post;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
