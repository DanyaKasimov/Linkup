package web.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import web.exception.AuthorizationException;
import web.security.JWT.JwtCore;
import web.security.authentication.TokenAuthentication;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtCore jwtCore;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = null;
        String username = null;
        try {
            String headerAuth = request.getHeader("Authorization");
            if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
                jwt = headerAuth.substring(7);
            }
            if (jwt != null) {
                DecodedJWT decodedJWT;
                try {
                    decodedJWT = jwtCore.validate(jwt);
                    username = String.valueOf(decodedJWT.getClaim("username")).replaceAll("\"", "");
                } catch (ExpiredJwtException e) {
                    throw new AuthorizationException("Ошибка парсинга jwt");
                }
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    TokenAuthentication authentication = new TokenAuthentication(jwt);
                    authentication.setAuthenticated(true);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    authentication.setUserDetails(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            throw new AuthorizationException("Недействительный токен");
        }
        filterChain.doFilter(request, response);
    }
}
