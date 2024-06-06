package onboarding.crud.service;

import lombok.AllArgsConstructor;
import onboarding.crud.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    public UserRepository userRepository;

    public Optional<User> getUserById(long userId) {
        return userRepository.findById(userId);
    }

    public User fetchUserFromExternalApi(long userId) {
        String apiUrl = "http://example.com/api/user/" + userId;
        RestTemplate restTemplate = new RestTemplate();
        ApiResponse response = restTemplate.getForObject(apiUrl, ApiResponse.class);
        return response != null && "success".equals(response.getStatus()) ? response.getData().getUser() : null;
    }
}

