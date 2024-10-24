package web.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import web.dto.request.filter.Filter;
import web.dto.request.account.UserUpdateDto;
import web.dto.response.ResponseDto;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/app/account")
@Tag(name = "Данные аккаунта", description = "Методы для работы с пользователем.")
public interface AccountApi {


    @Operation(summary = "Получение данных авторизированного пользователя.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные получены.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь/Данные о пользователе не найдены.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            )
    })
    @GetMapping("/user-data/current")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto getCurrentUserData();


    @Operation(summary = "Получение данных пользователей.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные получены.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь/Данные о пользователе не найдены.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            )
    })
    @GetMapping("/user-data/{ids}")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto getUsersData(@Parameter(description = "Идентификаторы пользователей.")
                            final @PathVariable List<UUID> ids
    );


    @Operation(summary = "Получение отфильтрованных данных пользователей.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные получены.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Данные о пользователе не найдены.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            )
    })
    @PostMapping("/user-data/list")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto getUsersFilteredData(
            final @RequestBody(required = false) @Valid Filter filter,
            @Parameter(description = "Пагинация и сортировка списка.", required = true)
            @ParameterObject @PageableDefault Pageable pageable
    );


    @Operation(summary = "Редактирование данных авторизированного пользователя.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные получены.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
            )
    )
    })
    @PutMapping("/user-update/current")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto editCurrentUserData(final @RequestBody @Valid UserUpdateDto dto);


    @Operation(summary = "Удаление пользователей.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь удален.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/user-delete/{ids}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto deleteUsers(final @PathVariable List<UUID> ids);


    @Operation(summary = "Удаление авторизированного пользователя.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь удален.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/user-delete/current")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto deleteCurrentUser();
}
