package web.security.detail;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.exception.NoDataFoundException;
import web.model.User;
import web.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsImlService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndIsDeletedFalse(username).orElseThrow(() -> new NoDataFoundException(
                "Пользователь не найден"
        ));
        return new UserDetailsImpl(user);
    }
}
