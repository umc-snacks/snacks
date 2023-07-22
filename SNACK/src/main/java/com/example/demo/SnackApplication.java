package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@EnableCaching
@SpringBootApplication
@ComponentScan(basePackages= { "com.example.demo", "com.example.demo.config", "com.example.demo.Chat.Mapper" } )
public class SnackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnackApplication.class, args);
	}

}
