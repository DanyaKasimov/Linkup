package web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web.dto.response.AccountDataDto;

import java.util.UUID;

public interface InteractionService {

    Page<AccountDataDto> getFriends(final UUID id, Pageable pageable);

    void sendFriendRequest(final UUID id);

    void acceptFriendRequest(final UUID id);

    void deleteFriendRequest(final UUID id);

}
