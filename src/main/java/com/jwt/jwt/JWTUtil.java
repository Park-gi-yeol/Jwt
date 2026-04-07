package com.jwt.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {
	
	// JWT 서명 및 검증에 사용할 비밀키 객체
	// application.properties에 저장된 secret 값을 바탕으로 생성
	private SecretKey secretKey;
	
	public JWTUtil(@Value("${spring.jwt.secret}")String secret) {
		// 문자열 secret 값을 바이트 배열로 변환한 뒤
		// HS256 알고르짐용 SecretKey 객체로 생성
		this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), 
						Jwts.SIG.HS256.key().build().getAlgorithm());
	}
	
	public String getUsername(String token) {
		// 전달받은 JWT를 검증 및 파싱한 뒤
		// payload에 저장된 username 값을 꺼내서 반환
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
	}
	
	public String getRole(String token) {
		// 전달받은 JWT를 검증 및 파싱한 뒤
		// payload에 저장된 role 값을 꺼내서 반환
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
	}
	
	public Boolean isExpired(String token) {
		// 토큰의 만료 시간(exp)을 가져와서
		// 현재 시간보다 이전인지 비교
		// 이전이면 만료된 토큰이므로 true 반환
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
	}
	
	public String createJwt(String username, String role, Long expiredMs) {
		// JWT를 생성하는 메서드
		// username, role 정보를 payload에 저장하고
		// 발급 시간(iat), 만료 시간(exp)을 설정한 뒤
		// secretKey로 서명하여 최종 JWT 문자열을 반환
		return Jwts.builder()
					.claim("username", username)
					.claim("role", role)
					.issuedAt(new Date(System.currentTimeMillis()))
					.expiration(new Date(System.currentTimeMillis() + expiredMs))
					.signWith(secretKey)
			        .compact();
					}
}
