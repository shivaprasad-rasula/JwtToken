package com.jwt.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo3Application {

	public static void main(String[] args) {
		System.out.println("start");
		SpringApplication.run(Demo3Application.class, args);
		System.out.println("ends");
	}

}
