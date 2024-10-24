package web.service;

import web.dto.request.VerificationDto;
import web.model.Email;

public interface EmailService {

    void sendVerificationEmail(String email);

    Email findByEmail(String email);

    void verifyCode(VerificationDto verificationDto);
}
