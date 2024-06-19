package onboarding.crud.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import onboarding.crud.post.LikedUsersConverter;
import onboarding.crud.comment.entity.Comment;

import java.util.HashSet;
import java.util.List;

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
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;
    @Setter(AccessLevel.NONE)
    private Integer likes;
    // Stirng of user ids separated by comma
    // ex: 1, 2, 3
    @Convert(converter = LikedUsersConverter.class)
    private HashSet<Long> likedUsers = new HashSet<>();
    public void updateLikes() {
        this.likes = likedUsers.size();
    }
    @Column(nullable = false)
    private Integer viewCount = 0;
}
