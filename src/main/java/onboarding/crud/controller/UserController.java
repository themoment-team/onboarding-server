package onboarding.crud.controller;

import onboarding.crud.entity.Post;
import onboarding.crud.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")

public class UserController{
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable long userId) {
        Optional<User> userOptional = userService.getUserById(userId);
        return userOptional.orElseGet(() -> userService.fetchUserFromExternalApi(userId));
    }
}