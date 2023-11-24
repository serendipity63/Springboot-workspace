package com.kosta.sec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kosta.sec.config.oauth.PrincipalOauth2UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean // 메소드 있는 오브젝트를 ioc에 등록한다
	public BCryptPasswordEncoder paswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception { // http설정
		http.csrf().disable(); // 공격 막아줌
		http.authorizeRequests().antMatchers("/user/**").authenticated() // 로그인해야
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // 로그인 & 권한
				.antMatchers("/manager/**").access("hasRole('ROLE_MANAGER')") // 매니저
				.anyRequest().permitAll() // 나머지는 허용
				.and().formLogin().loginPage("/login") // 로그인 페이지
				.loginProcessingUrl("/loginProc") // 로그인 프로세스
				.defaultSuccessUrl("/") // 로그인 처리 url
				.and().oauth2Login().loginPage("/login").userInfoEndpoint()
				.userService(new PrincipalOauth2UserService());

	}

}
