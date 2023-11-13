package com.kosta.bank.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.bank.dto.Member;
import com.kosta.bank.service.MemberService;

@RestController
public class MemberController {
	@Autowired
	private MemberService memberService;

	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestBody Member member) {
		try {
			memberService.join(member);
			return new ResponseEntity<String>("회원가입되었습니다", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody Map<String, String> param) {
		try {
			Member user = memberService.login(param.get("id"), param.get("password"));
			return new ResponseEntity<Object>(user, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
