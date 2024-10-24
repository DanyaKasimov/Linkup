package web.service;


import web.dto.request.SignInDto;
import web.dto.request.SignUpDto;

public interface AuthenticateService {

    String authenticateUser(final SignInDto userDto);

    String authenticateAndCreateUser(final SignUpDto userDto);
}
