package com.example.AcmeBank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AcmeBankApplication {

	public static void main(String[] args) {
		System.out.println("Acme application startss");
		SpringApplication.run(AcmeBankApplication.class, args);
	}

}
