package onboarding.crud.post.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import onboarding.crud.post.dto.CreatePostDto;
import onboarding.crud.post.dto.PostDto;
import onboarding.crud.post.dto.UpdatePostDto;
import onboarding.crud.post.service.PostService;
import onboarding.crud.user.dto.UserDto;
import onboarding.crud.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/post")
public class PostController {

    @Autowired
    private PostService postService; // DI 필드, setter, 생성자

    @Autowired
    private UserService userService;

    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody CreatePostDto createPostDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object _userId = session.getAttribute("userId");
        if(_userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        Optional<UserDto> author = userService.getUserById(Long.parseLong(_userId.toString()));
        if(author.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        createPostDto.setAuthor(author.get().getNickname());
        try {
            PostDto savedPost = postService.writePost(createPostDto);
            return ResponseEntity.ok(savedPost);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("게시글 등록 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        Optional<PostDto> postDto = postService.getPostById(id);
        if (postDto.isPresent()) {
            return postDto.map(ResponseEntity::ok)
                    .orElseGet(()->ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.");
        }
    }

    @PatchMapping("/{id}/like")
    public ResponseEntity<String> likePost(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object _userId = session.getAttribute("userId");
        if(_userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        Optional<UserDto> user = userService.getUserById(Long.parseLong(_userId.toString()));
        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        if(postService.toggleLikePost(id, user.get().getId())){
            return ResponseEntity.ok("게시글 좋아요가 성공적으로 변경되었습니다.");
        }else{
            return ResponseEntity.status(404).body("해당하는 게시글을 찾을 수 없습니다.");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody UpdatePostDto updatePostDto) {
        if(postService.modifyPost(id, updatePostDto)){
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
        Optional<UserDto> user = userService.getUserById(Long.parseLong(_userId.toString()));
        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("알맞은 회원으로 로그인이 필요합니다.");
        }
        Optional<PostDto> _post = postService.getPostById(id);
        if(_post.isEmpty()) {
            return ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.");
        }
        PostDto post = _post.get();
        if(!Objects.equals(post.getAuthor(), user.get().getName())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("게시글 삭제 권한이 없습니다.");
        }
        if(postService.deletePost(id)){
            return ResponseEntity.ok("게시글 삭제가 성공적으로 완료되었습니다.");
        }else{
            return ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.");
        }
    }
}
