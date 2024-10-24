package web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import web.exception.InvalidDataException;
import web.security.JWT.JwtCore;
import web.service.AuthenticateService;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtCore jwtCore;

    @Override
    public String authenticate(String username, String password) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e){
            throw new InvalidDataException("Неверные имя пользователя или пароль");
        }
        return jwtCore.generateToken(authentication);
    }
}
