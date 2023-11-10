package com.kosta.etc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {

	@GetMapping("/request_test")
	public String requestTest() {
		return "request_test";
	}

	@PostMapping("/request_test")
	public String requestTest(@RequestParam("data1") String data1, @ModelAttribute("data2") String data2) {
		return "response_test";
	}
}
