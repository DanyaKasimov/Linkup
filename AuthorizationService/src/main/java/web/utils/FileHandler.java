package web.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileHandler {

    public static String loadFromTemplate (String templateName) {
        ClassPathResource resource  =  new  ClassPathResource ( "templates/" + templateName + ".txt" );
        try {
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw  new  RuntimeException ("Ошибка загрузки шаблона: " + templateName, e);
        }
    }
}
