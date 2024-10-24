package web.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dto.request.auth.SignUpDto;
import web.exception.AccessErrorException;
import web.exception.InvalidDataException;
import web.model.Email;
import web.model.RoleType;
import web.model.User;
import web.repositories.UserRepository;
import web.service.EmailService;
import web.service.UserService;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createUser(SignUpDto userDto) {
        if (userRepository.existsUserByUsername(userDto.getUsername())) {
            throw new InvalidDataException("Пользователь с таким username уже существует.");
        }

        Email email = emailService.findByEmail(userDto.getEmail());
        if (email == null || !email.getIsAccepted()) {
            throw new AccessErrorException("Почта не подтверждена.");
        }

        if (userRepository.existsUserByEmail(email)) {
            throw new InvalidDataException("Электронная почта уже занята.");
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .name(userDto.getName())
                .secondName(userDto.getSecondName())
                .userRole(RoleType.USER)
                .password(passwordEncoder.encode(userDto.getPassword()))
                .isDeleted(false)
                .email(email)
                .build();

        return userRepository.save(user);
    }
}
