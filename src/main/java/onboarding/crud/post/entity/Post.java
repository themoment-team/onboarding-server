package onboarding.crud.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import onboarding.crud.comment.dto.CommentDto;
import onboarding.crud.comment.entity.Comment;

import java.util.ArrayList;
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
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    // Stirng of user ids separated by comma
    // ex: 1, 2, 3
    private String _likedUsers;
    public void resetLikes() {
        likes = 0;
        _likedUsers = "";
    }
    public void updateLikes() {
        likes = getLikedUsers().size();
    }
    public void updateLikes(int likes) {
        this.likes = likes;
    }
    public HashSet<Long> getLikedUsers() {
        if(_likedUsers == null) return new HashSet<>();
        String[] likedUsersSet = _likedUsers.split(",");
        HashSet<Long> likedUsers = new HashSet<>();
        for (String likedUser : likedUsersSet) {
            likedUsers.add(Long.parseLong(likedUser));
        }
        return likedUsers;
    }
    public void setLikedUsers(HashSet<Long> likedUsers) {
        StringBuilder likedUsersString = new StringBuilder();
        for (Long likedUser : likedUsers) {
            likedUsersString.append(likedUser).append(",");
        }
        _likedUsers = likedUsersString.toString();
        updateLikes(likedUsers.size());
    }
    public void addLikedUser(Long userId) {
        HashSet<Long> likedUsers = getLikedUsers();
        likedUsers.add(userId);
        setLikedUsers(likedUsers);
    }
    public void removeLikedUser(Long userId) {
        HashSet<Long> likedUsers = getLikedUsers();
        likedUsers.remove(userId);
        setLikedUsers(likedUsers);
    }

    @Column(nullable = false)
    private Integer viewCount = 0;
}
