package web.security.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import web.security.detail.UserDetailsImpl;

import java.util.concurrent.ThreadLocalRandom;

@Component
@EnableScheduling
public class JwtCore {

    private String SECRET;

    public JwtCore(){
        SECRET = generateRandomSecret();
    }

    private String generateRandomSecret(){
        StringBuilder secret = new StringBuilder();
        int count = ThreadLocalRandom.current().nextInt(2, 5);
        for (int i = 0; i < count; i++){
            int number = ThreadLocalRandom.current().nextInt(1, 10);
            secret.append(number);
        }
        int count2 = ThreadLocalRandom.current().nextInt(2, 5);
        for (int i = 0; i < count2; i++){
            int number = ThreadLocalRandom.current().nextInt(98, 121);
            secret.append((char) number);
        }
        int count3 = ThreadLocalRandom.current().nextInt(2, 5);
        for (int i = 0; i < count3; i++){
            int number = ThreadLocalRandom.current().nextInt(1, 10);
            secret.append(number);
        }
        return secret.toString();
    }

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return JWT.create()
                .withClaim("username", userDetails.getUsername())
                .withClaim("id", String.valueOf(userDetails.getId()))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public DecodedJWT validate(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token);

    }

}
