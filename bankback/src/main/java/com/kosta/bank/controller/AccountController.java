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

import com.kosta.bank.dto.Account;
import com.kosta.bank.service.AccountService;

@RestController // 이 뜻은 이 컨트롤러는 view를 주지 않는 controller다
public class AccountController { // view 가 없을때 responsebody를 썼다 하지만 여기에는 모든 메소드가 뷰가 없다 그래서 restcontroller를 쓴다

	@Autowired
	private AccountService accountService;

	@PostMapping("/makeaccount")
	public ResponseEntity<String> makeAccount(@RequestBody Account acc) { // 실제 데이터와 에러까지 가져감 제너릭 안에 들어가는 거가 실제 데이터
		// 자바객체를 HTTP요청의 바디내용으로 매핑하여 클라이언트로 전송한다.
		// @ResponseBody 가 붙은 파라미터가 있으면 HTTP요청의 미디어타입과 파라미터의 타입을 먼저 확인한다.
		// @RequestBody는 클라이언트에서 서버로 필요한 데이터를 요청하기 위해 JSON 데이터를 요청 본문에 담아서 서버로 보내면,
		// 서버에서는 @RequestBody 어노테이션을 사용하여 HTTP 요청 본문에 담긴 값들을 자바객체로 변환시켜, 객체에 저장한다.
		// modelattribute은 jsp에 쓴다
		// 보내는거랑 받는게 데이터 타입이 다르면 404가 뜰 수도 있다.

		try {
			accountService.makeAccount(acc);
			return new ResponseEntity<String>("계좌가 개설되었습니다.", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/accountinfo/{id}")
	public ResponseEntity<Object> accountInfo(@PathVariable String id) {
		try {
			Account acc = accountService.accountInfo(id);
			return new ResponseEntity<Object>(acc, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/allaccountinfo")
	public ResponseEntity<Object> allAccountInfo() {

		try {
			List<Account> accs = accountService.allAccountInfo();
			return new ResponseEntity<Object>(accs, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("계좌 목록 조회 실패", HttpStatus.BAD_REQUEST);

		}

	}

	@PutMapping("/deposit/{id}") // 보내는건 객체지만 받는건 map으로 받는다
	public ResponseEntity<Object> deposit(@PathVariable String id, @RequestBody Map<String, Integer> param) { // 실제 데이터는
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

	@PostMapping("/withdraw")
	public ResponseEntity<Object> withdraw(@RequestBody Map<String, Object> param) {
		try {
			String id = (String) param.get("id");
			Integer money = Integer.valueOf((String) param.get("money"));
			Integer balance = accountService.withdraw(id, money);
			Map<String, Object> res = new HashMap<>();
			res.put("id", id);
			res.put("balance", balance);
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

}
