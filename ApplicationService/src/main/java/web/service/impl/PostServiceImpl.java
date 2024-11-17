package web.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.dto.request.post.PostDto;
import web.model.Post;
import web.repositories.PostRepository;
import web.service.PostService;
import web.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private UserService userService;

    @Override
    public void save(PostDto dto) {
        Post post = Post.builder()
                .userId(userService.getUserById(dto.getUserId()))
                .build();
    }

    @Override
    public void delete(UUID id) {

    }
}
