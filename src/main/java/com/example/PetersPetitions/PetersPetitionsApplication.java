package com.example.PetersPetitions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class PetersPetitionsApplication {
	/**
	 * main method
	 * @param args
	 */
	public static void main(String[] args) {
		// added in the port change for local in-development and revert (manually) revert to run statement before pushing to Git
		SpringApplication.run(PetersPetitionsApplication.class, args);

	}

}
