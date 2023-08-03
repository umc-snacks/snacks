package com.example.demo.Chat.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.demo.Chat.Mapper;

@Component
public class MapperConfig {
	@Bean
    public Mapper mapper() {
        return new Mapper();
    }
}
