package com.kosta.bank.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.bank.dto.MemberDto;
import com.kosta.bank.service.MemberService;

@RestController
public class MemberController {
	@Autowired
	private MemberService memberService;

	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestBody MemberDto member) {
		try {
			memberService.join(member);
			return new ResponseEntity<String>(member.getId() + "회원가입을 축하합니다", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody Map<String, String> param) {
		try {
			MemberDto user = memberService.login(param.get("id"), param.get("password"));
			return new ResponseEntity<Object>(user, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
