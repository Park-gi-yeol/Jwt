package com.jwt.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
public class MainController {
	
	@GetMapping("/")
	public ResponseEntity<String> mainP() {
		return ResponseEntity.ok("mainc");	
	}
	
}
