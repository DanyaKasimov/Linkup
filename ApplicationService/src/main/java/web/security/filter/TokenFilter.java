package web.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import web.exception.AuthorizationException;
import web.security.JWT.JWTService;
import web.security.authentication.TokenAuthentication;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            Optional<String> jwt = getTokenFromRequest(request);

            if (jwt.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {
                DecodedJWT decodedJWT = jwtService.validate(jwt.get());
                String username = decodedJWT.getClaim("username").asString();

                if (username != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    setAuthentication(jwt.get(), userDetails);
                }
            }
        } catch (ExpiredJwtException e) {
            throw new AuthorizationException("Токен истек: " + e.getMessage());
        } catch (Exception e) {
            throw new AuthorizationException("Недействительный токен: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return Optional.of(headerAuth.substring(7));
        }
        return Optional.empty();
    }

    private void setAuthentication(String jwt, UserDetails userDetails) {
        TokenAuthentication authentication = new TokenAuthentication(jwt);
        authentication.setAuthenticated(true);
        authentication.setUserDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}