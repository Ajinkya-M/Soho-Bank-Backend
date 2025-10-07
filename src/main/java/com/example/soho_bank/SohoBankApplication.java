package com.example.soho_bank;

import com.example.soho_bank.user.model.User;
import com.example.soho_bank.user.model.types.Role;
import com.example.soho_bank.user.repository.UserRepository;

import java.net.PasswordAuthentication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class SohoBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(SohoBankApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			var user1 = User.builder()
					.email("rs@gmail.com")
					.name("Rohit Sharma")
					.role(Role.USER)
					.password(passwordEncoder.encode("rs@123"))
					.build();

			var user2 = User.builder()
					.email("vk@gmail.com")
					.name("Virat Kohli")
					.role(Role.USER)
					.password(passwordEncoder.encode("vk@123"))
					.build();

			var user3 = User.builder()
					.email("admin@gmail.com")
					.name("Admin")
					.role(Role.ADMIN)
					.password(passwordEncoder.encode("admin@123"))
					.build();

			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(user3);

			log.info("Users created successfully");
		};
	}

}
