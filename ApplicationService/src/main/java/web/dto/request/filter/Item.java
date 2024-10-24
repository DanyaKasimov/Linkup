package web.dto.request.filter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @NotBlank(message = "Обязательное поле field.")
    private String field;

    @Pattern(regexp = "=|!=|>|<|>=|<=|like|notLike")
    private String condition;

    @NotBlank(message = "Обязательное поле value.")
    private String value;

}
