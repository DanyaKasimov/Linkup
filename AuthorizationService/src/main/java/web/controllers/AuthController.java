package web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import web.api.AuthApi;
import web.dto.request.EmailDto;
import web.dto.request.SignInDto;
import web.dto.request.SignUpDto;
import web.dto.request.VerificationDto;
import web.dto.response.ResponseDto;
import web.service.AuthenticateService;
import web.service.EmailService;

@Slf4j
@RestController
//@CrossOrigin("${cors.hosts}")
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthenticateService authenticationService;

    private final EmailService emailService;

    @Override
    public ResponseDto requestVerification(final EmailDto emailDto) {
        log.info("Пришел запрос на отправку кода на почту. Входные данные: {}", emailDto);

        emailService.sendVerificationEmail(emailDto);
        return ResponseDto.builder().result("Код подтверждения отправлен вам на почту.").build();
    }

    @Override
    public ResponseDto verifyCode(final VerificationDto verificationDto) {
        log.info("Пришел запрос на верификацию кода с почты. Входные данные: {}", verificationDto);

        emailService.verifyCode(verificationDto);
        return ResponseDto.builder().result("Email подтвержден.").build();
    }

    @Override
    public ResponseDto signup(final SignUpDto userAddDto) {
        log.info("Пришел запрос на регистрацию. Входные данные: {}", userAddDto);


        String jwt = authenticationService.authenticateAndCreateUser(userAddDto);
        return ResponseDto.builder().result(jwt).build();
    }

    @Override
    public ResponseDto signin(final SignInDto signInDto) {
        log.info("Пришел запрос на вход. Входные данные: {}", signInDto);

        String jwt = authenticationService.authenticateUser(signInDto);
        return ResponseDto.builder().result(jwt).build();
    }

}
