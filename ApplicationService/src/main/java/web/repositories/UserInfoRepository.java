package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.UserInfo;

import java.util.UUID;

public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {
}
