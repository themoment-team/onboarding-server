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

    @Autowired
    private HashingService hashingService;

    public Optional<UserDto> getUserById(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(User::toDto);
    }

    public User registerUser(String name, String nickname, String password){
        if (userRepository.findByName(name).isPresent()) {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "입력한 Id가 이미 존재합니다."
            );
        }
        User user = new User();

        user.setName(name);
        user = userRepository.save(user);

        user.setNickname(nickname);
        user = userRepository.save(user);

        String hashedPassword = hashingService.hashPasswordWithId(password, user.getId());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    public Optional<User> loginUser(String name, String password) {
        Optional<User> userOptional = userRepository.findByName(name);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }
        User user = userOptional.get();
        String hashedPassword = hashingService.hashPasswordWithId(password, user.getId());

        if (hashedPassword.equals(user.getPassword())) {
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
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
            if (userDto.getNickname() != null) {
                user.setNickname(userDto.getNickname());
            }
            return userRepository.save(user);
        } else {
            throw new RuntimeException("사용자를 찾을 수 없습니다");
        }
    }
}

