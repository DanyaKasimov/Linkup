package web.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dto.request.SignUpDto;
import web.exception.AccessErrorException;
import web.exception.InvalidDataException;
import web.mappers.UserMapper;
import web.model.Email;
import web.model.User;
import web.model.UserInfo;
import web.repositories.UserInfoRepository;
import web.repositories.UserRepository;
import web.service.EmailService;
import web.service.UserService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public  class  UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserInfoRepository userInfoRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final UserMapper userMapper;

    @Override
    @Transactional
    public User addUser(SignUpDto userDto) {
        if (userRepository.existsUserByUsernameAndIsDeletedFalse(userDto.getUsername())) {
            throw new InvalidDataException("Пользователь с таким username уже существует.");
        }

        Email email = emailService.findByEmail(userDto.getEmail());
        if (email == null || !email.getIsAccepted()) {
            throw new AccessErrorException("Почта не подтверждена.");
        }

        if (userRepository.existsUserByEmailAndIsDeletedFalse(email)) {
            throw new InvalidDataException("Электронная почта уже занята.");
        }

        val user = userRepository.save(userMapper.toEntity(userDto, passwordEncoder, email));

        val info = new UserInfo();
        info.setId(user.getId());
        userInfoRepository.save(info);

        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsernameAndIsDeletedFalse(username);
    }
}