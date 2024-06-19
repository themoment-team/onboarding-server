package onboarding.crud.post.service;

import lombok.AllArgsConstructor;
import onboarding.crud.comment.dto.CommentDto;
import onboarding.crud.comment.entity.Comment;
import onboarding.crud.comment.repository.CommentRepository;
import onboarding.crud.post.dto.CreatePostDto;
import onboarding.crud.post.dto.PostDto;
import onboarding.crud.post.dto.PostWithCommentsDto;
import onboarding.crud.post.dto.UpdatePostDto;
import onboarding.crud.post.entity.Post;
import onboarding.crud.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    @Autowired
    public PostRepository postRepository;

    @Autowired
    public CommentRepository commentRepository;

    public PostDto writePost(CreatePostDto createPostDto) {
        Post post = new Post();
        post.setTitle(createPostDto.getTitle());
        post.setContent(createPostDto.getContent());
        post.setAuthor(createPostDto.getAuthor());
        post.resetLikes();
        Post savedPost = postRepository.save(post);
        return convertToDto(savedPost);
    }

    public PostWithCommentsDto getPostWithCommentsById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + postId));

        // Increase view count
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);

        // Convert post to DTO
        PostDto postDto = convertToDto(post);

        // Get comments for the post and convert to DTOs
        List<CommentDto> comments = commentRepository.findByPostId(postId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        // Create PostWithCommentsDto and set postDto and comments
        PostWithCommentsDto postWithCommentsDto = new PostWithCommentsDto();
        postWithCommentsDto.setPost(postDto);
        postWithCommentsDto.setComments(comments);

        return postWithCommentsDto;
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
        PostDto postDto = new PostDto(); // builder
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setAuthor(post.getAuthor());
        postDto.setViewCount(post.getViewCount());
        postDto.setLikes(post.getLikes());
        return postDto;
    }

    private CommentDto convertToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        return commentDto;
    }
}