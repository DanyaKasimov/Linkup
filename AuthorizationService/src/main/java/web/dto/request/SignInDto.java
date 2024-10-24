package web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignInDto {
    @NotBlank(message = "Имя пользователя не может быть пустым.")
    private String username;

    @NotBlank(message = "Пароль пользователя не может быть пустым.")
    private String password;
}
