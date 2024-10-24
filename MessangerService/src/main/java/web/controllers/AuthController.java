package web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.dto.request.VerificationDto;
import web.dto.request.auth.EmailDto;
import web.dto.request.auth.SignInDto;
import web.dto.request.auth.SignUpDto;
import web.dto.response.ResponseDto;
import web.service.AuthenticateService;
import web.service.EmailService;
import web.service.UserService;

@RestController
@RequestMapping("api/v1/auth")
@Tag(name = "Авторизация", description = "Методы для авторизации.")
@CrossOrigin("${cors.hosts}")
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticateService authenticationService;

    @Autowired
    private EmailService emailService;


    @PostMapping("/request-verification")
    @Operation(description = "Отправить код подтверждения на почту.")
    public ResponseEntity<?> requestVerification(
            @Parameter(description = "Почта пользователя", required = true)
            @RequestBody @Valid EmailDto emailDto
    ) {
        log.info("Пришел запрос на отправку кода на почту. Входные данные: {}", emailDto);

        emailService.sendVerificationEmail(emailDto.getEmail());
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .result("Код подтверждения отправлен вам на почту.")
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/verify-code")
    @Operation(description = "Подтверждение кода.")
    public ResponseEntity<?> verifyCode(
            @Parameter(description = "Почта пользователя и код подтверждения", required = true)
            @RequestBody @Valid VerificationDto verificationDto
    ) {
        log.info("Пришел запрос на верификацию кода с почты. Входные данные: {}", verificationDto);

        emailService.verifyCode(verificationDto);
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .result("Email подтвержден.")
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/signup")
    @Operation(description = "Регистрация пользователя.")
    public ResponseEntity<?> signup(
            @Parameter(description = "Данные пользователя для регистрации", required = true)
            @RequestBody @Valid SignUpDto userAddDto
    ) {
        log.info("Пришел запрос на регистрацию. Входные данные: {}", userAddDto);

        userService.createUser(userAddDto);
        String jwt = authenticationService.authenticate(userAddDto.getUsername(), userAddDto.getPassword());
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .result(jwt)
                        .build(),
                HttpStatus.OK
        );
    }


    @PostMapping("/signin")
    @Operation(description = "Вход пользователя в аккаунт.")
    public ResponseEntity<?> signin(
            @Parameter(description = "Имя пользователя и пароль", required = true)
            @RequestBody @Valid SignInDto signInDto
    ) {
        log.info("Пришел запрос на вход. Входные данные: {}", signInDto);

        String jwt = authenticationService.authenticate(signInDto.getUsername(),signInDto.getPassword());
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .result(jwt)
                        .build(),
                HttpStatus.OK
        );
    }

}
