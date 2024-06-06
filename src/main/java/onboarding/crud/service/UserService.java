package onboarding.crud.service;

import lombok.AllArgsConstructor;
import onboarding.crud.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    public UserRepository userRepository;
}
