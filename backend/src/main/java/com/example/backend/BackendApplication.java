package com.example.backend;

import com.example.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class BackendApplication {
	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		for (RoleType roleType : RoleType.values()) {
//			roleRepository.save(new Role(roleType.getName(), roleType.getDescription()));
//		}
//	}
}
