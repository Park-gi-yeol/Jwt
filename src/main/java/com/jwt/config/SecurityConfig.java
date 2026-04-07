package com.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwt.jwt.LoginFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final AuthenticationConfiguration authenticationConfiguration;
	
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswrodEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
    	return config.getAuthenticationManager();
    }
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			// CSRF 보호 기능 비활성화
			// JWT 방식은 세션 기반 인증이 아니라서 보통 csrf를 끄고 사용함
			.csrf((auth) -> auth.disable());
		http
			// 기본 로그인 폼 비 활성화
			// 스프링 시큐리티가 제공하는 로그인 페이지를 사용하지 않겠다는 의미
			.formLogin((auth) -> auth.disable());
		http
			// HTTP Basic 인증 방식 비활성화
			// Authorization 헤더에 아이디/비밀번호를 직접 담는 기본 인증방식 사용 안함
			.httpBasic((auth) -> auth.disable());
		http
			// 요청 URL별 인가(권한) 설정
			.authorizeHttpRequests((auth) -> auth
						// "/login", "/", "/join" 경로는 로그인하지 않아도 누구나 접근 가능
						.requestMatchers("/login", "/", "/join","/main","/main/**","/user/**").permitAll()
						// "/admin" 경로는 ADMIN 권한이 있는 사용자만 접근 가능						
						.requestMatchers("/admin").hasRole("ADMIN")
						// 그 외 모든 요청은 로그인(인증)된 사용자만 접근 가능
						.anyRequest().authenticated());
		http
			.addFilterAt(new LoginFilter(authManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);
		http
			// 세션 관리 방식 설정
			.sessionManagement((session) -> session
			// 세션을 생성하지 않음
		    // JWT를 사용하는 무상태(Stateless) 방식이므로 서버가 세션을 저장하지 않음
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}
}
