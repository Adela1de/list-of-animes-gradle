package luiz.augusto.userhandlerforlistofanimesproject.repositories;

import luiz.augusto.userhandlerforlistofanimesproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
