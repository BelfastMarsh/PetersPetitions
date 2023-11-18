package com.example.PetersPetitions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class PetersPetitionsApplication {

	public static void main(String[] args) {
		// test data

		//SpringApplication.run(PetersPetitionsApplication.class, args);/*

		SpringApplication app = new SpringApplication(PetersPetitionsApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port","9094"));
		app.run(args);
		//*/

	}

}
