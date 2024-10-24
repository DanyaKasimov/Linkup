package web.service;

import web.dto.request.auth.SignUpDto;
import web.model.User;

public interface UserService {

    User createUser(SignUpDto dto);

}
