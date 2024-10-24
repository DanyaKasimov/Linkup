package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthorizationApplication {

    public static void main(String[] args) throws IOException {

        ApplicationContext context = SpringApplication.run(AuthorizationApplication.class, args);
    }
}