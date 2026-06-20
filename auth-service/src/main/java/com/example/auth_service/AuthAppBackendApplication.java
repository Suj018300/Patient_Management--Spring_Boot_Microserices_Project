package com.example.auth_service;

import com.example.auth_service.config.AppConstants;
import com.example.auth_service.entities.Role;
import com.example.auth_service.entities.User;
import com.example.auth_service.repositories.RoleRepository;
import com.example.auth_service.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Slf4j
@SpringBootApplication
public class AuthAppBackendApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(AuthAppBackendApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		//we will create some default user role
		//ADMIN
		//GUEST
//		Role adminRole = roleRepository.findByName("ROLE_ADMIN")
//				.orElseThrow();
//
//		if (!userRepository.existsByEmail("admin@role.com")) {
//
//			User admin = User.builder()
//					.email("admin@role.com")
//					.password(passwordEncoder.encode("Admin@123"))
//					.roles(Set.of(adminRole))
//					.build();
//
//			userRepository.save(admin);
//		}


		roleRepository.findByName("ROLE_"+ AppConstants.ADMIN_ROLE).ifPresentOrElse(role->{
            log.info("Admin Role Already Exists: {}", role.getName());
		},()->{

			Role role=new Role();
			role.setName("ROLE_"+AppConstants.ADMIN_ROLE);
//			role.setId(UUID.randomUUID());
			roleRepository.save(role);

		});

		roleRepository.findByName("ROLE_"+AppConstants.GUEST_ROLE).ifPresentOrElse(role->{
            log.info("Guest Role Already Exists: {}", role.getName());
		},()->{

			Role role=new Role();
			role.setName("ROLE_"+AppConstants.GUEST_ROLE);
//			role.setId(UUID.randomUUID());
			roleRepository.save(role);

		});



	}

}
