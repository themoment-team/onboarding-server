package onboarding.crud.post.service;

import lombok.AllArgsConstructor;
import onboarding.crud.post.entity.Post;
import onboarding.crud.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    @Autowired
    public PostRepository postRepository;

    public Post writePost(Post post) {
        post.setId(null);
        post.resetLikes();
        return postRepository.save(post);
    }

    public Optional<Post> getPostById(long id) {
        return postRepository.findById(id);
    }

    public Optional<Post> getPostByTitle(String title) {
        return postRepository.findByTitle(title);
    }
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public boolean deletePost(Long id) {
        if(postRepository.existsById(id)){
            postRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    public boolean toggleLikePost(Long postId, Long userId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            Post likedPost = post.get();
            if(likedPost.getLikedUsers().contains(userId)){
                likedPost.removeLikedUser(userId);
            }else{
                likedPost.addLikedUser(userId);
            }
            postRepository.save(likedPost);
            return true;
        } else {
            return false;
        }
    }

    public boolean modifyPost(Long id, Post newPost) {
        Optional<Post> oldPost = postRepository.findById(id);
        if (oldPost.isPresent()) {
            Post updatedPost = oldPost.get();
            if (newPost.getTitle() != null) {
                updatedPost.setTitle(newPost.getTitle());
            }
            if (newPost.getContent() != null) {
                updatedPost.setContent(newPost.getContent());
            }
            postRepository.save(updatedPost);
            return true;
        } else {
            return false;
        }
    }
}
