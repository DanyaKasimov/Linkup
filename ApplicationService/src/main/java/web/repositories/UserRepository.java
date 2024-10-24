package web.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.model.Email;
import web.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Boolean existsUserByUsername(String username);

    Optional<User> findByIdAndIsDeletedFalse(UUID id);

    Boolean existsUserByEmail(Email email);

    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
