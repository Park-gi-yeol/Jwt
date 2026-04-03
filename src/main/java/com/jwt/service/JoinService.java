package com.jwt.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.dto.JoinDTO;
import com.jwt.entity.UserEntity;
import com.jwt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JoinService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswrodEncoder;
	
	
	public void joinProcess(JoinDTO joinDTO) {
		String username = joinDTO.getUsername();
		String password = joinDTO.getPassword();
		
		Boolean isExist = userRepository.existsByUsername(username);
		
		if(isExist) {
			return ;
		} 
		UserEntity data = new UserEntity();
		data.setUsername(username);
		data.setPassword(bCryptPasswrodEncoder.encode(password));
		data.setRole("ROLE_ADMIN");
		
		userRepository.save(data);
	}
}
