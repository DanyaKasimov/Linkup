package web.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dto.request.account.UserUpdateDto;
import web.dto.request.filter.Filter;
import web.dto.request.filter.Item;
import web.dto.response.AccountDataDto;
import web.exception.NoDataFoundException;
import web.mappers.AvatarMapper;
import web.mappers.UserInfoMapper;
import web.mappers.UserMapper;
import web.model.User;
import web.repositories.UserInfoRepository;
import web.repositories.UserRepository;
import web.repositories.specifications.SearchCriteria;
import web.repositories.specifications.Specifications;
import web.security.detail.UserDetailsImpl;
import web.service.AvatarService;
import web.service.UserService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserInfoRepository infoRepository;

    private final UserMapper userMapper;

    private final UserInfoMapper infoMapper;

    private final AvatarMapper avatarMapper;

    private final AvatarService avatarService;

    @Override
    public List<AccountDataDto> getAccountsData(final List<UUID> ids) {
        return ids.stream().map(this::getAccountData).toList();
    }


    @Override
    @Transactional
    public void deleteUsers(final List<UUID> ids) {
        ids.forEach(this::deleteUser);
    }


    @Override
    @Transactional
    @CacheEvict(value = {"account.data", "user.data"}, key = "#id")
    public void editUserData(final UUID id, final UserUpdateDto dto) {
        log.debug("Редактирование данных пользователя. Входные данные: {}, ID: {}", dto, id);

        var user = userRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NoDataFoundException("Пользователь с ID = " + id + " не найден."));

        var info = infoRepository
                .findById(id)
                .orElseThrow(() -> new NoDataFoundException("Данные о пользователе не найдены."));

        info = infoMapper.toEntity(dto, info.getId());

        user.setName(dto.getName());
        user.setSecondName(dto.getSecondName());

        userRepository.save(user);
        infoRepository.save(info);
    }


    @Override
    @Transactional
    @CacheEvict(value = {"account.data", "user.data"}, key = "#id")
    public void deleteUser(final UUID id) {
        log.debug("Удаление данных пользователя с ID: {}", id);

        var user = userRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NoDataFoundException("Пользователь с ID = " + id + " не найден."));

        user.setIsDeleted(true);
        userRepository.save(user);
    }


    @Override
    public Page<AccountDataDto> getAccountsFilteredData(final Filter filter, final Pageable pageable) {
        log.debug("Получение списка пользователей с фильтрацией. Входные данные: {}, Pageable: {}", filter, pageable);

        SearchCriteria criteria = new SearchCriteria();
        if (filter != null) {
            criteria.addAll(filter.getItems());
        }
        criteria.add(new Item("isDeleted", "=", "false"));
        Page<User> page = userRepository.findAll(Specifications.hasField(criteria), pageable);

        val data = page.getContent().stream()
                .map(user -> getAccountData(user.getId()))
                .toList();

        return new PageImpl<>(data, pageable, page.getTotalElements());
    }


    @Override
    public UUID getCurrentUserId() {
        val principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return ((UserDetailsImpl) principal).getId();
    }


    @Override
    @Cacheable(value = "user.data", key = "#id")
    public User getUserById(final UUID id) {
        return userRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NoDataFoundException("Пользователь с ID = " + id + " не найден."));
    }


    @Override
    @Cacheable(value = "account.data", key = "#id")
    public AccountDataDto getAccountData(UUID id) {
        log.info("Получение данных пользователя. ID: {}", id);

        val avatars = avatarService.getAvatarsByUserId(id);

        val info = infoRepository
                .findById(id)
                .orElseThrow(() -> new NoDataFoundException("Информация о пользователе не найдена."));

        val user = getUserById(id);

        return AccountDataDto.builder()
                .user(userMapper.toDto(user))
                .avatars(avatarMapper.toDtoList(avatars))
                .info(info)
                .build();
    }
}
