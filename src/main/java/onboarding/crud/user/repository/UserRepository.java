package onboarding.crud.user.repository;

import onboarding.crud.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);

    Optional<User> findById(long id);

    Optional<User> findByNameAndPassword(String name, String password);
}