package web.dto.request.post;

import lombok.Data;

import java.util.UUID;

@Data
public class PostDto {

    private String message;

    private UUID userFrom;

    private byte[] image;



}
