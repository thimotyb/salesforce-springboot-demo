package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.example.api.ContactsApiController;

@SpringBootApplication
@ComponentScan(basePackageClasses = ContactsApiController.class)
public class SalesforceDemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(SalesforceDemo1Application.class, args);
	}

}
