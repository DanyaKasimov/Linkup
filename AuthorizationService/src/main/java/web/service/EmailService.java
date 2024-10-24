package web.service;

import org.springframework.stereotype.Service;
import web.dto.request.EmailDto;
import web.dto.request.VerificationDto;
import web.model.Email;

@Service
public interface EmailService {

    void sendVerificationEmail(final EmailDto email);

    Email findByEmail(String email);

    Email saveEmail(Email email);

    void verifyCode(VerificationDto verificationDto);
}
