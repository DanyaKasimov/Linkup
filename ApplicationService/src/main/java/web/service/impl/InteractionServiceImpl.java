package web.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.constants.RabbitConstants;
import web.dto.response.AccountDataDto;
import web.exception.InvalidDataException;
import web.exception.NoDataFoundException;
import web.model.Friends;
import web.repositories.InteractionRepository;
import web.service.InteractionService;
import web.service.UserService;
import web.utils.TimeUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InteractionServiceImpl implements InteractionService {

    private final InteractionRepository interactionRepository;

    private final UserService userService;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public Page<AccountDataDto> getFriends(UUID id, Pageable pageable) {
        Page<Friends> friends = interactionRepository.findAllFriends(id, pageable);

        List<AccountDataDto> allFriends = friends.getContent().stream()
                .map(friend -> userService.getAccountData(
                        friend.getUserTo().getId().equals(id) ? friend.getUserFrom().getId() : friend.getUserTo().getId()
                ))
                .collect(Collectors.toList());

        return new PageImpl<>(allFriends, pageable, friends.getTotalElements());
    }


    @Override
    @Transactional
    public void sendFriendRequest(final UUID id) {
        log.debug("Отправка запроса в список друзей: ID: {}", id);

        val currentId = userService.getCurrentUserId();
        val userFrom = userService.getUserById(currentId);
        val userTo = userService.getUserById(id);

        interactionRepository.findFriends(userFrom, userTo)
                .ifPresent(interaction -> {
                    throw new InvalidDataException("Запрос на дружбу уже отправлен.");
                });

        val friendRequest = Friends.builder()
                .userTo(userTo)
                .userFrom(userFrom)
                .isAccepted(false)
                .time(TimeUtil.getLocalTime())
                .build();

        interactionRepository.save(friendRequest);
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE_1, RabbitConstants.KEY_1, id);
    }


    @Override
    @Transactional
    public void acceptFriendRequest(final UUID id) {
        log.debug("Добавление в список друзей: ID: {}", id);

        val currentId = userService.getCurrentUserId();
        val userTo = userService.getUserById(currentId);
        val userFrom = userService.getUserById(id);

        val friends = interactionRepository.findFriendRequest(userTo, userFrom)
                .orElseThrow(() -> new NoDataFoundException("Запроса на дружбу не существует"));

        friends.setIsAccepted(true);
        friends.setTime(TimeUtil.getLocalTime());

        interactionRepository.save(friends);
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE_1, RabbitConstants.KEY_1, id);
    }


    @Override
    @Transactional
    public void deleteFriendRequest(final UUID id) {
        log.debug("Удаление из списка друзей: ID: {}", id);

        val currentId = userService.getCurrentUserId();
        val userTo = userService.getUserById(currentId);
        val userFrom = userService.getUserById(id);

        val friends = interactionRepository.findFriendRequest(userTo, userFrom)
                .orElseThrow(() -> new NoDataFoundException("Запроса на дружбу не существует"));

        interactionRepository.delete(friends);
    }

}
