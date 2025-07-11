package com.springproject.droolEngineProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.springproject.droolEngineProject.repo")
@EntityScan("com.springproject.droolEngineProject.model")
public class DroolEngineProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroolEngineProjectApplication.class, args);
	}

}
