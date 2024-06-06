package onboarding.crud.service;

import lombok.AllArgsConstructor;
import onboarding.crud.entity.User;
import onboarding.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public Optional<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }

    public Optional<User> getUserById(long userId) {
        return userRepository.findById(userId);
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User loginUser(String name, String password) {
        return userRepository.findBynameAndPassword(name, password);
    }
}

