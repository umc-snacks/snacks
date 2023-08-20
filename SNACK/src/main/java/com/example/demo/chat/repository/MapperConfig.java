package com.example.demo.chat.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.demo.chat.Mapper;

@Component
public class MapperConfig {
	@Bean
    public Mapper mapper() {
        return new Mapper();
    }
}
