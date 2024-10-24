package web.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.constants.RabbitConstants;
import web.dto.request.EmailBodyDto;
import web.dto.request.EmailDto;
import web.dto.request.VerificationDto;
import web.exception.InvalidDataException;
import web.model.Email;
import web.repositories.EmailRepository;
import web.service.EmailService;
import web.service.VerificationService;
import web.utils.FileHandler;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;

    private final JavaMailSender emailSender;

    private final RabbitTemplate rabbitTemplate;

    private final VerificationService verificationService;

    @RabbitListener(queues = RabbitConstants.QUEUE_1)
    public void processEmailMessage(EmailBodyDto emailDto) throws MessagingException {
        String to = emailDto.getTo();
        String subject = emailDto.getSubject();
        String body = generateEmailBody(emailDto);
        sendEmail(to, subject, body);
    }

    @Override
    public void sendVerificationEmail(final EmailDto emailDto) {
        if (emailRepository.existsUserByEmail(emailDto.getEmail())) {
            throw new InvalidDataException("Электронная почта уже существует.");
        }

        String email = emailDto.getEmail();
        String code = verificationService.generateVerificationCode();
        verificationService.storeVerificationCode(email, code);

        EmailBodyDto body = EmailBodyDto.builder()
                .to(email)
                .body(code)
                .subject("Код подтверждения.")
                .template("email")
                .build();

        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE_1, RabbitConstants.KEY_1, body);
    }

    @Override
    public Email findByEmail(String email) {
        return emailRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public Email saveEmail(final Email email) {
        return emailRepository.save(email);
    }


    @Override
    @Transactional
    public void verifyCode(final VerificationDto verificationDto) {
        boolean isValid = verificationService.verifyCode(verificationDto.getEmail(), verificationDto.getCode());
        if (!isValid) {
            throw new InvalidDataException("Неверный код подтверждения.");
        }

        if (!emailRepository.existsByEmail(verificationDto.getEmail())) {
            Email email = Email.builder()
                    .email(verificationDto.getEmail())
                    .isAccepted(true)
                    .build();
            saveEmail(email);
        }

        verificationService.removeVerificationCode(verificationDto.getEmail());
    }

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
    }

    public String generateEmailBody(EmailBodyDto emailDto) {
        String body = FileHandler.loadFromTemplate(emailDto.getTemplate());
        body = body.replace("{{ code }}", emailDto.getBody());
        return body;
    }

}