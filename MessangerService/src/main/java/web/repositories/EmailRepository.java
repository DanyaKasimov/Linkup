package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.Email;


@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    @Query("select count(u) > 0 from User u join u.email e where e.email = :email")
    Boolean existsUserByEmail(@Param("email") String email);

    Boolean existsByEmail(String email);

    Email findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update Email e set e.isAccepted = :isAccepted where e.email = :email")
    void updateIsAcceptedByEmail(@Param("email") String email,
                                 @Param("isAccepted")  boolean isAccepted);
}
