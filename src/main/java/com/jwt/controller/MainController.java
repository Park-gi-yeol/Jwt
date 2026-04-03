package com.jwt.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.dto.JoinDTO;

@RestController
@RequestMapping("/main")
public class MainController {
	
	@GetMapping("/hello")
	public ResponseEntity<String> mainP() {
		return ResponseEntity.ok("mainc");	
	}
	
	
	
}
