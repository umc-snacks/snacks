//package com.example.demo.Chat;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//import org.springframework.stereotype.Component;
//
//import com.example.demo.Chat.Dto.MessageDTO;
//
//@Component
//@Mapper(componentModel = "spring")
//public interface ChatMapper {
//	ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);
//	
//	@Mapping(target = "time", expression = "java(java.time.LocalDateTime.now())") // 현재 시간을 sentAt에 설정
//	MessageDTO.Response MessageDtoGetToMessageDtoResponse(MessageDTO.Get get);
//}
