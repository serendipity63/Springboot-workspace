package com.kosta.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosta.board.dto.Member;
import com.kosta.board.service.MemberService;

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
		return "main";
	}

	@PostMapping("/login")
	public String login(@RequestParam("id") String id, @RequestParam("password") String password, Model model) {
		try {
			Member user = memberService.login(id, password);
			session.setAttribute("user", user);
			return "main";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@PostMapping("/join")
	public String join(@ModelAttribute Member member, Model model) {
		try {
			memberService.join(member);
			return "redirect:/login";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@PostMapping("/idcheck")
	@ResponseBody // json 기반 메세지를 보낼때
	public String idCheck(@RequestParam("id") String id) {
		try {
			Member user = memberService.userInfo(id);
			if (user == null)
				return "notexist";
			return "exist";
		} catch (Exception e) {
			e.printStackTrace();
			return "exist";
		}
	}

}
