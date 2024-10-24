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
import org.springframework.web.bind.annotation.*;
import web.dto.response.ResponseDto;

import java.util.UUID;

@RequestMapping("api/v1/app/interaction")
@CrossOrigin("${cors.hosts}")
@Tag(name = "Взаимодействие пользователей", description = "Методы для взаимодействия пользователей.")
public interface InteractionApi {

    @Operation(summary = "Отправка заявки в друзья.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Заявка отправлена.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Запрос уже отправлен.",
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
    @PostMapping("/friend-request/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto sendFriendRequest(@Parameter(description = "Идентификатор отправляемого пользователя.")
                                  final @PathVariable UUID id);


    @Operation(summary = "Отправка ответа на заявку в друзья.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ответ отправлен.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь/Запись в друзья не найдены.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            )
    })
    @PostMapping("/friend-request/{id}/{answer}")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto answerFriendRequest(@Parameter(description = "Ответ пользователя на запрос.")
                                    final @PathVariable @Valid Boolean answer,
                                    @Parameter(description = "Идентификатор отправителя запроса.")
                                    final @PathVariable @Valid UUID id
    );


    @Operation(summary = "Получение списка друзей пользователя.")
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
                    description = "Пользователь не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            )
    })
    @PostMapping("/friend-list/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto getFriendList(@Parameter(description = "Пагинация и сортировка списка.", required = true)
                              @ParameterObject @PageableDefault Pageable pageable,
                              final @PathVariable UUID id);


    @Operation(summary = "Удаление из списка друзей.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Друг удален.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/friend-delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto deleteFriend(@Parameter(description = "Идентификатор удаляемого друга.")
                            final @PathVariable UUID id);
}
