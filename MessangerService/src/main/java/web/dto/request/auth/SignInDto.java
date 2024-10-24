package web.dto.request.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignInDto {
    @NotNull(message = "Имя пользователя не может быть пустым.")
    private String username;

    @NotNull(message = "Пароль пользователя не может быть пустым.")
    private String password;
}
