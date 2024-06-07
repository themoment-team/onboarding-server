package onboarding.crud.service;

import lombok.AllArgsConstructor;
import onboarding.crud.entity.Post;
import onboarding.crud.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    @Autowired
    public PostRepository postRepository;

    public Post writePost(Post post) {
        return postRepository.save(post);
    }

    public Post getPostById(long id) {
        return postRepository.findById(id);
    }

    public Post getPostByTitle(String title) {
        return postRepository.findByTitle(title);
    }
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public void deletePost(Long id) throws Exception {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
        } else {
            throw new Exception("Post not found");
        }
    }
}
