package onboarding.crud.user.service;

import lombok.AllArgsConstructor;
import onboarding.crud.user.entity.User;
import onboarding.crud.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional<User> loginUser(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}

