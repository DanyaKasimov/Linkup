package web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerificationDto {
    @NotBlank(message = "Поле email не должно быть пустым.")
    @Email(message = "Ошибка валидации адреса почты.")
    private String email;

    @NotBlank(message = "Поле код не может быть пустым.")
    private String code;
}

