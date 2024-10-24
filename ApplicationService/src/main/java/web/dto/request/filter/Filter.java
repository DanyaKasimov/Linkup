package web.dto.request.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Тело запроса с описанием правил фильтрации.")
public class Filter {
    
    List<@Valid Item> items;
}
