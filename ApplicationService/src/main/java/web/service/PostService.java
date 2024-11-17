package web.service;

import org.springframework.stereotype.Service;
import web.dto.request.post.PostDto;


import java.util.UUID;

@Service
public interface PostService {

    void save(final PostDto dto);

    void delete(final UUID id);
}
