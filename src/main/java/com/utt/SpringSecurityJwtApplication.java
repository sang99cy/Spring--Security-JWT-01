package com.utt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.utt.model.User;
import com.utt.repositories.UserRepository;

@SpringBootApplication
public class SpringSecurityJwtApplication implements CommandLineRunner {
	

	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user=new User();
		user.setUsername("loda");
		user.setPassword(passwordEncoder.encode("loda"));
		userRepository.save(user);
		System.out.println(user);
	}

}
