package onboarding.crud.post.service;

import lombok.AllArgsConstructor;
import onboarding.crud.post.dto.CreatePostDto;
import onboarding.crud.post.dto.PostDto;
import onboarding.crud.post.dto.UpdatePostDto;
import onboarding.crud.post.entity.Post;
import onboarding.crud.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    @Autowired
    public PostRepository postRepository;

    public PostDto writePost(CreatePostDto createPostDto) {
        Post post = new Post();
        post.setTitle(createPostDto.getTitle());
        post.setContent(createPostDto.getContent());
        post.setAuthor(createPostDto.getAuthor());
        post.resetLikes();
        Post savedPost = postRepository.save(post);
        return convertToDto(savedPost);
    }

    public Optional<PostDto> getPostById(Long id) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setViewCount(post.getViewCount() + 1);
                    postRepository.save(post);
                    return convertToDto(post);
                });
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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

    public boolean modifyPost(Long id, UpdatePostDto updatePostDto) {
        Optional<Post> oldPost = postRepository.findById(id);
        if (oldPost.isPresent()) {
            Post post = oldPost.get();
            if (updatePostDto.getTitle() != null) {
                post.setTitle(updatePostDto.getTitle());
            }
            if (updatePostDto.getContent() != null) {
                post.setContent(updatePostDto.getContent());
            }
            postRepository.save(post);
            return true;
        } else {
            return false;
        }
    }

    private PostDto convertToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setAuthor(post.getAuthor());
        postDto.setViewCount(post.getViewCount());
        return postDto;
    }
}
