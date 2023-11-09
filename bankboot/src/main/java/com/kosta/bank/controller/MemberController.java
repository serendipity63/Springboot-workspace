package com.kosta.bank.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kosta.bank.dto.Member;
import com.kosta.bank.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private HttpSession session; 
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/join")
	public String join() {
		return "join";
	}
	
	@GetMapping("/logout")
	public String logout() {
		session.removeAttribute("user");
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("id") String id,@RequestParam("password") String password, Model model) {
		try {
			Member member=memberService.login(id, password);
			session.setAttribute("user", member); // user를 멤버로
			return "makeaccount";
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
			return "error";
		}
	}
	
	@PostMapping("/join") //커맨드 객체
	public String join(@ModelAttribute Member member, Model model) {
		try {
			memberService.join(member);
			return "redirect:/login"; //로그인이 멤버를 공유한다
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("err", e.getMessage());
			return "error";
		}
	}
	
	
	
}
