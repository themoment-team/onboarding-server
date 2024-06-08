package onboarding.crud.comment.repository;

import onboarding.crud.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByPostId(Long postId);
}
