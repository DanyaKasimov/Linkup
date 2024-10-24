package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.model.Email;
import web.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsernameAndIsDeletedFalse(String username);

    Boolean existsUserByUsernameAndIsDeletedFalse(String username);

    Boolean existsUserByEmailAndIsDeletedFalse(Email email);
}
