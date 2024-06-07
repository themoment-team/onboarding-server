package onboarding.crud.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import onboarding.crud.entity.Post;
import onboarding.crud.repository.PostRepository;
import onboarding.crud.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("/write")
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        try {
            Post savedPost = postRepository.save(post);
            return ResponseEntity.ok(savedPost);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("게시글 등록 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            return ResponseEntity.ok(post.get());
        } else {
            return ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.");
        }
    }

    @PatchMapping("/{id}/like")
    public ResponseEntity<?> likePost(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object _userId = session.getAttribute("userId");
        if(_userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        String userId = _userId.toString();

        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            Post existingPost = post.get();
            existingPost.setLikes(existingPost.getLikes() + 1);
            Post savedPost = postRepository.save(existingPost);
            return ResponseEntity.ok(savedPost);
        } else {
            return ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            Post existingPost = post.get();
            if (updatedPost.getTitle() != null) {
                existingPost.setTitle(updatedPost.getTitle());
            }
            if (updatedPost.getContent() != null) {
                existingPost.setContent(updatedPost.getContent());
            }
            Post savedPost = postRepository.save(existingPost);
            return ResponseEntity.ok(savedPost);
        } else {
            return ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.ok("게시글 삭제가 성공적으로 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시글 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
