package web.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    @Length(min = 3, max = 50, message = "Длина имени пользователя должна быть от 3 до 50.")
    private String username;

    @Length(min = 1, max = 50, message = "Длина имени должна быть от 1 до 50.")
    private String name;

    @Length(min = 1, max = 50, message = "Длина фамилии должна быть от 1 до 50.")
    private String secondName;

    @Length(min = 6, max = 50, message = "Длина пароля должна быть от 6 до 50.")
    private String password;

    @NotBlank(message = "Поле email не может быть пустым.")
    @Email(message = "Неверный формат поля email.")
    private String email;
}
