package web.service;

public interface VerificationService {

    String generateVerificationCode();

    void storeVerificationCode(String email, String code);

    boolean verifyCode(String email, String code);

    void removeVerificationCode(String email);

    boolean isEmailVerified(String email);
}
