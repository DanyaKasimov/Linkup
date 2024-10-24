package web.service;

import org.springframework.stereotype.Service;
import web.model.Avatar;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface AvatarService {

    List<Avatar> getAvatarsByUserId(UUID userId);

}
