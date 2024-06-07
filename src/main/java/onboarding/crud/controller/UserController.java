package onboarding.crud.controller;


import jakarta.servlet.http.HttpSession;
import onboarding.crud.entity.User;
import onboarding.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/users")

public class UserController{

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable long userId) {
        Optional<User> userOptional = userService.getUserById(userId);
        if(userOptional.isPresent())
            return userOptional.get();
        else throw new ResponseStatusException(
            HttpStatus.NOT_FOUND, "User not found"
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.registerUser(user));
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "회원가입 중 오류가 발생했습니다."
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> loginData) {

        String name;
        String password;
        try {
            name = loginData.get("name");
            password = loginData.get("password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("요청 정보가 형식에 맞지 않습니다.");
        }

        Optional<User> user = userService.loginUser(name, password);
        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인에 실패했습니다.");
        }
        //todo: 세션에 로그인 정보 저장
        return ResponseEntity.ok("로그인이 성공적으로 완료되었습니다.");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpSession session) {
        try {
            session.invalidate(); // 무효화 세션
            return ResponseEntity.ok("로그아웃이 성공적으로 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그아웃 중 오류가 발생했습니다.");
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("회원 탈퇴가 성공적으로 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 탈퇴 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}