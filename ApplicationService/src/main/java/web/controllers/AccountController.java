package web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import web.api.AccountApi;
import web.dto.request.filter.Filter;
import web.dto.request.account.UserUpdateDto;
import web.dto.response.ResponseDto;
import web.service.UserService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
//@CrossOrigin("${cors.hosts}")
@RequiredArgsConstructor
public class AccountController implements AccountApi {

    private final UserService userService;

    @Override
    public ResponseDto getCurrentUserData() {
        log.info("Поступил запрос на получение данных авторизированного пользователя.");

        val data = userService.getCurrentAccountData();
        return ResponseDto.builder().result(data).build();
    }


    @Override
    public ResponseDto getUsersData(final List<UUID> ids) {
        log.info("Поступил запрос на получение данных пользователей. IDs = {}", ids);

        val data = userService.getAccountsData(ids);
        return ResponseDto.builder().result(data).build();
    }


    @Override
    public ResponseDto getUsersFilteredData(final Filter filter, final Pageable pageable) {
        log.info("Поступил запрос на получение отфильтрованного списка пользователей. Входные данные: {}", filter);

        val data = userService.getAccountsFilteredData(filter, pageable);
        return ResponseDto.builder().result(data).build();
    }


    @Override
    public ResponseDto editCurrentUserData(final UserUpdateDto dto) {
        log.info("Поступил запрос на редактирование данных пользователя. Входные данные: {}", dto);

        userService.editCurrentUserData(dto);
        return ResponseDto.builder().result("Данные пользователя изменены.").build();
    }


    @Override
    public ResponseDto deleteUsers(final List<UUID> ids) {
        log.info("Поступил запрос на удаление пользователя админом. Входные данные: IDs = {}", ids);

        userService.deleteUsers(ids);
        return ResponseDto.builder().result("Пользователь удален.").build();
    }


    @Override
    public ResponseDto deleteCurrentUser() {
        log.info("Поступил запрос на удаление авторизированного пользователя.");

        userService.deleteCurrentUser();
        return ResponseDto.builder().result("Пользователь удален.").build();
    }
}
