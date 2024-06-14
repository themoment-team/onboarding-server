package onboarding.crud.user.service;

import lombok.AllArgsConstructor;
import onboarding.crud.user.dto.UpdateUserDto;
import onboarding.crud.user.dto.UserDto;
import onboarding.crud.user.entity.User;
import onboarding.crud.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public Optional<UserDto> getUserById(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(User::toDto);
    }

    public User registerUser(User user) throws ResponseStatusException {
        // Check if the user already exists
        user.setId(null);
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User updateUser(Long id, UpdateUserDto userDto) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (userDto.getPassword() != null) {
                user.setPassword(userDto.getPassword());
            }
            return userRepository.save(user);
        } else {
            throw new RuntimeException("사용자를 찾을 수 없습니다");
        }
    }
}

