package web.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import web.security.detail.UserDetailsImpl;
import web.service.JWTService;

import java.util.Date;

@Service
public class JWTServiceImpl implements JWTService {

    @Value("${auth.jwt.secret}")
    private String SECRET;

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return JWT.create()
                .withClaim("username", userDetails.getUsername())
                .withClaim("name", userDetails.getName())
                .withClaim("surname", userDetails.getSurname())
                .withClaim("email", userDetails.getEmail().toString())
                .withClaim("id", String.valueOf(userDetails.getId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .sign(Algorithm.HMAC256(SECRET));
    }
}
