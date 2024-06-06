package onboarding.crud.repository;

import onboarding.crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByName(String name);
    Optional<User> findById(long id);
    User findBynameAndPassword(String name, String password);
}
