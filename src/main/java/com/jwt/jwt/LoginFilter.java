package com.jwt.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
	
	// 인증 처리를 실제로 담당하는 AuthenticationManager
	// UsernamePasswordAuthenticationToken을 넘기면
	// UserDetailService 등을 통해 사용자 검증 진행
	private final AuthenticationManager authManager;
	
	
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
		
		// 요청에서 username,password를 꺼냄
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		System.out.println(username);
		
		// username,password를 Authentication객체 형태의 토큰으로 생성
		// 아직 인증이 완료된 토큰이 아니라, 인증을 요청하기 위한 토큰
		UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(username, password, null);
		
		// 생성한 토큰을 AuthenticationManager에게 전달해서 실제 인증 수행
		// 여기서 UserDetailService가 사용자를 조회
		// PasswordEncoder가 비밀번호 검증
		return authManager.authenticate(authtoken);
		
		
	}
	
	// 로그인 인증 성공 시 실행되는 메서드
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) {
		System.out.println("성공");
	}
	
	// 로그인 인증 실패 시 실행되는 메서드
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
		System.out.println("실패");
	}
}
