package onboarding.crud.service;

import lombok.AllArgsConstructor;
import onboarding.crud.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
    public PostRepository postRepository;

}
