package web.service;

import org.springframework.stereotype.Service;
import web.dto.request.SignUpDto;
import web.model.User;

import java.util.Optional;

@Service
public interface UserService {

    User addUser(SignUpDto dto);

    Optional<User> findByUsername(String username);

}
