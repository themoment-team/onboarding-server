package onboarding.crud.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String author;
    private Integer likes;
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String likedUsers;
}
