package com.kosta.bank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.bank.dto.AccountDto;
import com.kosta.bank.entity.Account;
import com.kosta.bank.service.AccountService;

@RestController
public class AccountController {
	@Autowired
	private AccountService accountService;

	@PostMapping("/makeaccount") // 폼데이터- modelattribute json은 requestbody-json으로 온걸 객체로 바꾼다
	public ResponseEntity<String> makeAccount(@RequestBody AccountDto acc) {
		try {
			accountService.makeAccount(acc);
			return new ResponseEntity<String>(acc.getId() + "계좌가 개설되었습니다", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/accountinfo/{id}") // 이것도 된다

	public ResponseEntity<Object> accountInfo(@PathVariable String id) { // pathvariable은 path로부터
		try {
			AccountDto acc = accountService.accountInfo(id);
			return new ResponseEntity<Object>(acc, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

//	@GetMapping("/accountinfo/{id}")
//	public ResponseEntity<Object> accountInfo(@RequestParam("id") String id) {
//		try {
//			Account acc = accountService.accountInfo(id);
//			return new ResponseEntity<Object>(acc, HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
//		}
//	}

	@GetMapping("/allaccountinfo")
	public ResponseEntity<List<Account>> allAccountInfo() {

		try {
//			List<Account> accs = accountService.allAccountInfo();
			return new ResponseEntity<List<Account>>(accountService.allAccountInfo(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Account>>(HttpStatus.BAD_REQUEST);

		}

	}

	@PutMapping("/deposit/{id}") // 보내는건 객체지만 받는건 map으로 받는다 //Json을 받는 방법은 requestbody로 받는다 거기에 맞는 클래스를 받아야 한다
	public ResponseEntity<Object> deposit(@PathVariable String id, @RequestBody Map<String, Integer> param) { // 실제
		// 데이터는

		try {
			Integer balance = accountService.deposit(id, param.get("money"));
			Map<String, Object> res = new HashMap<>();
			res.put("id", id);
			res.put("balance", balance);
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

//requestparam은 폼태그일때만

	@PostMapping("/withdraw")
	public ResponseEntity<Object> withdraw(@RequestBody Map<String, String> param) {
		try {

			// Integer.valueof((String)param.get... 만약 프론드에서 acc로 보냈었으면
			Integer balance = accountService.withdraw(param.get("id"), Integer.valueOf(param.get("money")));
			Map<String, Object> res = new HashMap<>();
			res.put("id", param.get("id"));
			res.put("balance", balance);
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}
//	@PostMapping("/withdraw")
//	public ResponseEntity<Object> withdraw(@RequestBody Map<String, Object> param) {
//		try {
//
//			// Integer.valueof((String)param.get... 만약 프론드에서 acc로 보냈었으면
//			Integer balance = accountService.withdraw((String) param.get("id"), (Integer) param.get("money"));
//			Map<String, Object> res = new HashMap<>();
//			res.put("id", param.get("id"));
//			res.put("balance", balance);
//			return new ResponseEntity<Object>(res, HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
//
//		}
//	}

}
