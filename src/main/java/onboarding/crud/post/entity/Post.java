package onboarding.crud.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

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
    public ArrayList<Long> getLikedUsers() {
        String[] likedUsersArray = _likedUsers.split(",");
        ArrayList<Long> likedUsers = new ArrayList<>();
        for (String likedUser : likedUsersArray) {
            likedUsers.add(Long.parseLong(likedUser));
        }
        return likedUsers;
    }
    public void setLikedUsers(ArrayList<Long> likedUsers) {
        StringBuilder likedUsersString = new StringBuilder();
        for (Long likedUser : likedUsers) {
            likedUsersString.append(likedUser).append(",");
        }
        _likedUsers = likedUsersString.toString();
    }
}
