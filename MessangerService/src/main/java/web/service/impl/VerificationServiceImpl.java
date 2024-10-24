package web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import web.service.VerificationService;

import java.time.Duration;
import java.util.Random;

@Service
public class VerificationServiceImpl implements VerificationService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${redis.prefix}")
    private static String KEY_PREFIX;


    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public void storeVerificationCode(String email, String code) {
        String key = KEY_PREFIX + email;
        redisTemplate.opsForValue().set(key, code, Duration.ofMinutes(10));
    }

    public boolean verifyCode(String email, String code) {
        String key = KEY_PREFIX + email;
        String storedCode = redisTemplate.opsForValue().get(key);
        return code.equals(storedCode);
    }

    public void removeVerificationCode(String email) {
        String key = KEY_PREFIX + email;
        redisTemplate.delete(key);
    }

    public boolean isEmailVerified(String email) {
        String key = KEY_PREFIX + email;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}