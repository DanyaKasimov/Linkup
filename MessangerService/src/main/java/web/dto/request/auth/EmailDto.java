package web.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailDto {
    @NotBlank(message = "Поле email не должно быть пустым.")
    @Email(message = "Ошибка валидации адреса почты.")
    private String email;

}
