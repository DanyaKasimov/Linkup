package web.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import web.dto.request.post.PostDto;
import web.dto.response.ResponseDto;


@RequestMapping("api/v1/app/post")
//@CrossOrigin("${cors.hosts}")
@Tag(name = "Посты пользователей", description = "Методы для работы с постами пользователей.")
public interface PostApi {

    @Operation(summary = "Отправка поста")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пост отправлен.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            )
    })
    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto sendPost(final @RequestBody PostDto dto);
}
