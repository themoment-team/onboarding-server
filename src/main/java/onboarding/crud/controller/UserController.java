package onboarding.crud.controller;


import onboarding.crud.entity.User;
import onboarding.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 오류가 발생했습니다.");
        }
    }
}