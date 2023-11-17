package com.kosta.bank.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer { // web.xml에서 넣어준 설정들
	@Override
	public void addCorsMappings(CorsRegistry registry) { // 네트워크에서 나는 에러 요청을 받아주는 곳에서 한다 // 리액트 서버 포트
		// 아무것도 안쓰면 GET POST만 된다
		registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("GET", "POST", "PUT",
				"DELETE");

	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
		// 객체를 생성해서 return해줌

	}
}
// 리액트 연결하는거 