package com.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.dto.JoinDTO;
import com.jwt.service.JoinService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/join")
@RequiredArgsConstructor
public class JoinController {
	
	private final JoinService joinService;
	
	
	@PostMapping("/join")
	public ResponseEntity<?> joinProcess(JoinDTO joinDTO) {
		
		joinService.joinProcess(joinDTO);
		
		return ResponseEntity.ok("ok");
	}
}
