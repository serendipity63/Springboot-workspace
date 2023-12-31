package com.kosta.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.bank.dto.Account;
import com.kosta.bank.service.AccountService;

@Controller
public class AccountController {

	// 의존성 주입
	@Autowired
	private AccountService accountService;

	@GetMapping("/main")
	public String main() {
		return "main";
		// 실행할때 contextroot가 없다
	}

	@GetMapping("/makeaccount")
	public String makeaccount() {
		return "makeaccount";
	}

	@GetMapping("/deposit")
	public String deposit() {
		return "deposit";
	}

	@GetMapping("/withdraw")
	public String withdraw() {
		return "withdraw";
	}

	@GetMapping("/accountinfo")
	public String accountinfo() {
		return "accountinfoform";
	}

	@GetMapping("/allaccountinfo")
	public ModelAndView allaccountinfo() {
		ModelAndView mav = new ModelAndView();
		try {
			List<Account> accs = accountService.allAccountInfo();
			mav.addObject("accs", accs);
			mav.setViewName("allaccountinfo");

		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", "전체계좌조회 실패");
		}
		return mav;
	}

	@PostMapping("/makeaccount")
	public ModelAndView makeAccount(@ModelAttribute Account acc) { // 커맨드 객체
		ModelAndView mav = new ModelAndView();
		try {
			accountService.makeAccount(acc);
			Account sacc = accountService.accountInfo(acc.getId());
			mav.addObject("acc", sacc);
			mav.setViewName("accountinfo");
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", e.getMessage());
			mav.setViewName("error");
		}
		return mav;
	}

	@PostMapping("/deposit")
	public ModelAndView deposit(@RequestParam("id") String id, @RequestParam("money") Integer money) {
		ModelAndView mav = new ModelAndView();
		try {
			accountService.deposit(id, money);
			mav.addObject("id", id); // 이렇게 하면 코드가 더 간결하다
			mav.setViewName("forward:/accountinfo"); // 데이터를 공유한다
//			Account acc= accountService.accountInfo(id);
//			mav.addObject("acc", acc);
//			mav.setViewName("accountinfo");

		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", e.getMessage());
			mav.setViewName("error");
		}

		return mav;

	}

	@PostMapping("/withdraw")
	public ModelAndView Withdraw(@RequestParam("id") String id, @RequestParam("balance") Integer money) {
		ModelAndView mav = new ModelAndView();
		try {
			accountService.withdraw(id, money);
			mav.addObject("id", id);
			mav.setViewName("forward:/accountinfo");

		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", e.getMessage());
			mav.setViewName("error");
		}

		return mav;

	}

	@PostMapping("/accountinfo")
	public ModelAndView accountinfo(@RequestParam("id") String id) {
		ModelAndView mav = new ModelAndView();
		try {
			Account acc = accountService.accountInfo(id);
			mav.addObject("acc", acc);
			mav.setViewName("accountinfo");
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", e.getMessage());
			mav.setViewName("error");
		}
		return mav;
	}

}
