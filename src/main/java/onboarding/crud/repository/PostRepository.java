package onboarding.crud.repository;

import onboarding.crud.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(long id);
    Optional<Post> findByTitle(String title);
}
