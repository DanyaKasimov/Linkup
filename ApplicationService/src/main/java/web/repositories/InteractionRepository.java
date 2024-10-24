package web.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.model.Friends;
import web.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InteractionRepository extends JpaRepository<Friends, UUID> {

    @Query("select f from Friends f where" +
            "(f.userTo = :userTo and f.userFrom = :userFrom) or " +
            "(f.userTo = :userFrom and f.userFrom = :userTo) ")
    Optional<Friends> findFriends(@Param("userTo") User userTo,
                                  @Param("userFrom") User userFrom);

    @Query("select f from Friends f where f.userTo = :userTo and f.userFrom = :userFrom")
    Optional<Friends> findFriendRequest(@Param("userTo") User userTo,
                                        @Param("userFrom") User userFrom);

    Page<Friends> findAll(Specification<Friends> spec, Pageable pageable);


    @Query("select f from Friends f where (f.userTo.id = :id or f.userFrom.id = :id) and f.isAccepted = true")
    Page<Friends> findAllFriends(@Param("id") UUID id, Pageable pageable);
}
