package web.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    @NotBlank(message = "Поле email не должно быть пустым.")
    @Email(message = "Ошибка валидации адреса почты.")
    private String email;
}
