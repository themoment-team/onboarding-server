package onboarding.crud.comment.controller;

import onboarding.crud.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class CommentController {

    @Autowired
    private PostService postService;
}
