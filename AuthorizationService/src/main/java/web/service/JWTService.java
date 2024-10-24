package web.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
public interface JWTService {

    String generateToken(Authentication authentication);
}
