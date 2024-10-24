package web.service;

import org.springframework.stereotype.Service;

@Service
public interface VerificationService {

    String generateVerificationCode();

    void storeVerificationCode(String email, String code);

    boolean verifyCode(String email, String code);

    void removeVerificationCode(String email);

    boolean isEmailVerified(String email);
}
