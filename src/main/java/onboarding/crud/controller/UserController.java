package onboarding.crud.controller;


import onboarding.crud.entity.User;
import onboarding.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RestController
@RequestMapping("/api/users")

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
}