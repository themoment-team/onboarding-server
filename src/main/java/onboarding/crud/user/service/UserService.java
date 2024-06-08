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

    public User registerUser(User user) throws Exception{
        if(userRepository.existsById(user.getId())){
            throw new Exception("이미 존재하는 회원입니다.");
        }
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}

