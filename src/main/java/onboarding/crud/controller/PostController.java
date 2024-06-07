package onboarding.crud.controller;

import onboarding.crud.entity.Post;
import onboarding.crud.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("/write")
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        try {
            Post savedPost = postService.writePost(post);
            return ResponseEntity.ok(savedPost);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("게시글 등록 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            return ResponseEntity.ok(post.get());
        } else {
            return ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        if(postService.modifyPost(id, updatedPost)){
            return ResponseEntity.ok("게시글 수정이 성공적으로 완료되었습니다.");
        }else{
            return ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        if(postService.deletePost(id)){
            return ResponseEntity.ok("게시글 삭제가 성공적으로 완료되었습니다.");
        }else{
            return ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.");
        }
    }
}
