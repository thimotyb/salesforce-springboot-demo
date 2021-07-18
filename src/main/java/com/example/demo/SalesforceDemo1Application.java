package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.example.api.ContactsApiController;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo", "com.example.api", "com.example.service"} )
public class SalesforceDemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(SalesforceDemo1Application.class, args);
	}

}
