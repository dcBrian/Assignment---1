package com.project.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomeworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeworkApplication.class, args);
		args = new String[] {
				"--imports",
				"https://bigfastblog.com/embed-base64-encoded-images-inline-in-html",
				"https://bigfastblog.com/embed-base64-encoded-images-inline-in-html"
		};
		new CommandLine().run(args);
	}

}
