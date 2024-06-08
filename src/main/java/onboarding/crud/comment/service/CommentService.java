package onboarding.crud.comment.service;

import onboarding.crud.comment.repository.CommentRepository;
import onboarding.crud.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

}
