package onboarding.crud.post.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import onboarding.crud.post.entity.Post;
import onboarding.crud.post.service.PostService;
import onboarding.crud.user.dto.UserDto;
import onboarding.crud.user.entity.User;
import onboarding.crud.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object _userId = session.getAttribute("userId");
        if(_userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        Optional<UserDto> author = userService.getUserById(Long.parseLong(_userId.toString()));
        if(author.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        post.setAuthor(author.get().getName());
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

    @PatchMapping("/{id}/like")
    public ResponseEntity<?> likePost(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object _userId = session.getAttribute("userId");
        //todo : 좋아요 기능 구현 with Session
        if(_userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        String userId = _userId.toString();
        return ResponseEntity.status(500).body("it will be implemented soon");
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
    public ResponseEntity<String> deletePost(@PathVariable Long id, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Object _userId = session.getAttribute("userId");
        if(_userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        Optional<Post> _post = postService.getPostById(id);
        if(_post.isEmpty()) {
            return ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.");
        }
        Post post = _post.get();
        if(!Objects.equals(post.getAuthor(), userService.getUserById(Long.parseLong(_userId.toString())).get().getName())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("게시글 삭제 권한이 없습니다.");
        }
        if(postService.deletePost(id)){
            return ResponseEntity.ok("게시글 삭제가 성공적으로 완료되었습니다.");
        }else{
            return ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.");
        }
    }
}
