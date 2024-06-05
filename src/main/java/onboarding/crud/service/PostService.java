package onboarding.crud.service;

import lombok.AllArgsConstructor;
import onboarding.crud.entity.Post;
import onboarding.crud.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
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
}
