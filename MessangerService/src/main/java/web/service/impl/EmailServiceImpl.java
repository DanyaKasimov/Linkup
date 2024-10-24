package web.service.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dto.request.VerificationDto;
import web.exception.InvalidDataException;
import web.model.Email;
import web.repositories.EmailRepository;
import web.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private VerificationServiceImpl verificationService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendVerificationEmail(String toEmail) {
        if (emailRepository.existsUserByEmail(toEmail)) {
            throw new InvalidDataException("Электронная почта уже существует.");
        }

        if (emailRepository.existsByEmail(toEmail)) {
            emailRepository.updateIsAcceptedByEmail(toEmail, false);
        }

        // Генерация кода подтверждения
        String code = verificationService.generateVerificationCode();
        verificationService.storeVerificationCode(toEmail, code);

        // Отправка email с кодом подтверждения
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Ваш код подтверждения");
        message.setText("Ваш код подтверждения: " + code);
        mailSender.send(message);

        // Отправка сообщения в очередь RabbitMQ
        String verificationMessage = "Код подтверждения для " + toEmail + ": " + code;
        amqpTemplate.convertAndSend("verificationQueue", verificationMessage);
    }

    @Override
    public Email findByEmail(String email) {
        return emailRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void verifyCode(VerificationDto verificationDto) {
        boolean isValid = verificationService.verifyCode(verificationDto.getEmail(), verificationDto.getCode());
        if (!isValid) {
            throw new InvalidDataException("Неверный код подтверждения.");
        }

        if (emailRepository.existsByEmail(verificationDto.getEmail())) {
            emailRepository.updateIsAcceptedByEmail(verificationDto.getEmail(), true);
        } else {
            Email email = Email.builder()
                    .email(verificationDto.getEmail())
                    .isAccepted(true)
                    .build();
            emailRepository.save(email);
        }

        verificationService.removeVerificationCode(verificationDto.getEmail());
    }
}