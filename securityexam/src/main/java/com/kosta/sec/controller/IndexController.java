package com.kosta.sec.controller;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosta.sec.config.auth.PrincipalDetails;
import com.kosta.sec.entity.User;
import com.kosta.sec.repository.UserRepository;

@Controller
public class IndexController {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	@GetMapping({ "", "/" })
	@ResponseBody
	public String index() {
		return "인덱스 페이지입니다.";
	}

	@GetMapping("/user")
	@ResponseBody
	public String user(@AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println("principal:" + principal);
		System.out.println(principal.getUsername());
		System.out.println(principal.getPassword());
		Iterator<? extends GrantedAuthority> iter = principal.getAuthorities().iterator();
		while (iter.hasNext()) {
			GrantedAuthority auth = iter.next();
			System.out.println(auth.getAuthority());

		}
		return "유저입니다.";
	}

//좀 더 specific하게 하려면 이걸 쓰는걸 권장 

//	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") 
//	@PostAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") 
	// @Secured("ROLE_MANAGER")
	@GetMapping("/admin")
	@ResponseBody
	public String admin() {
		return "어드민입니다.";
	}

	@GetMapping("/manager")
	@ResponseBody
	public String manager() {
		return "매니저입니다.";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	// 스프링 시큐리티가 로그인 처리를 가로챈다

	@PostMapping("/loginProc")
	public String loginProc(String username, String password) {
		System.out.println(username);
		System.out.println(password);
		return "redirect:/";
		// config에 있는 애가 가로챈다
	}

	@GetMapping("/join")
	public String joinForm() {
		return "join";
	}

	@PostMapping("/joinProc")
	public String joinProc(User user) {
		System.out.println(user);
		String rawPassword = user.getPassword();
		String encoderPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setRoles("ROLE_USER");
		user.setPassword(encoderPassword);
		userRepository.save(user);

		return "redirect:/login";
	}
}
