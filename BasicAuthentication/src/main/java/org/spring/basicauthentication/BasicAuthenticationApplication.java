package org.spring.basicauthentication;

import org.spring.basicauthentication.domain.User;
import org.spring.basicauthentication.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BasicAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicAuthenticationApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        String password = passwordEncoder.encode("password");
        return _ -> userRepository.save(new User("admin", password, "mamali"));
    }
}
