package web.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import web.dto.request.EmailDto;
import web.dto.request.SignInDto;
import web.dto.request.SignUpDto;
import web.dto.request.VerificationDto;
import web.dto.response.ResponseDto;

@RequestMapping("api/v1/auth")
@Tag(name = "Авторизация", description = "Методы для авторизации.")
public interface AuthApi {


    @Operation(description = "Отправить код подтверждения на почту.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Код отправлен на почту.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Электронная почта уже существует.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            )
    })
    @PostMapping("/request-verification")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto requestVerification(final @RequestBody @Valid EmailDto emailDto);




    @Operation(description = "Подтверждение кода.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Код подтвержден.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный код.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            )
    })
    @PostMapping("/verify-code")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto verifyCode(final @RequestBody @Valid VerificationDto verificationDto);




    @Operation(description = "Регистрация пользователя.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Регистрация прошла успешно.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Пользователь уже существует.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Почта не подтверждена.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            )
    })
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto signup(final @RequestBody @Valid SignUpDto userAddDto);




    @Operation(description = "Вход пользователя в аккаунт.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Вход прошел успешно.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Неверные данные пользователя.",
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
    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto signin(final @RequestBody @Valid SignInDto signInDto);

}
