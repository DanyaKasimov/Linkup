package web.security.JWT;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface JWTService {

    DecodedJWT validate(String jwt);
}
