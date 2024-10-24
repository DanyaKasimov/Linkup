package web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import web.api.InteractionApi;
import web.dto.response.ResponseDto;
import web.service.InteractionService;
import java.util.UUID;

@Slf4j
@RestController
//@CrossOrigin("${cors.hosts}")
@RequiredArgsConstructor
public class InteractionController implements InteractionApi {

    private final InteractionService interactionService;

    @Override
    public ResponseDto sendFriendRequest(final UUID id) {
        log.info("Поступил запрос на добавление в друзья. ID: {}", id);

        interactionService.sendFriendRequest(id);
        return ResponseDto.builder().result("Запрос на добавление в друзья отправлен.").build();
    }


    @Override
    public ResponseDto answerFriendRequest(final Boolean answer, final UUID id) {
        log.info("Поступил ответ от пользователя на запрос в друзья. ID: {}. Ответ: {}", id, answer);

        if (answer) {
            interactionService.acceptFriendRequest(id);
        } else {
            interactionService.deleteFriendRequest(id);
        }
        return ResponseDto.builder().result("Ответ обработан.").build();
    }


    @Override
    public ResponseDto getFriendList(final Pageable pageable, final UUID id) {
        log.info("Поступил запрос на получение друзей пользователя. ID: {}", id);

        val friendsList = interactionService.getFriends(id, pageable);
        return ResponseDto.builder().result(friendsList).build();
    }


    @Override
    public ResponseDto deleteFriend(final UUID id) {
        log.info("Поступил запрос на удаление пользователя из друзей. ID: {}", id);

        interactionService.deleteFriendRequest(id);
        return ResponseDto.builder().result("Друг удален").build();
    }
}
