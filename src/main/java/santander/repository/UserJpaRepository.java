package santander.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import santander.model.user.User;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<Object> findByEmail(String email);

    Optional<Object> findByUsername(String username);
}