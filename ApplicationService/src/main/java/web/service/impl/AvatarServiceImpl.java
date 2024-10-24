package web.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.model.Avatar;
import web.repositories.AvatarRepository;
import web.service.AvatarService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;

    @Override
    public List<Avatar> getAvatarsByUserId(UUID userId) {
        return avatarRepository.findAllByUserIdAndUserIsDeletedFalse(userId);
    }
}
