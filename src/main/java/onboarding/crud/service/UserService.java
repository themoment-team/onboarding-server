package onboarding.crud.service;

import lombok.AllArgsConstructor;
import onboarding.crud.entity.User;
import onboarding.crud.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    public UserRepository userRepository;

    public Optional<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }

    public Optional<User> getUserById(long userId) {
        return userRepository.findById(userId);
    }
}

