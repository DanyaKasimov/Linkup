package web.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    private Object result;

    private String errorMessage;
}
