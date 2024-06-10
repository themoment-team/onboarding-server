package onboarding.crud.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

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
    //String of user ids separated by comma
    // ex: 1,2,3
    private String _likedUsers;
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
}
