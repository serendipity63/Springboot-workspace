package com.kosta.bank;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kosta.bank.entity.Account;
import com.kosta.bank.entity.Member;
import com.kosta.bank.repository.AccountRepository;
import com.kosta.bank.repository.MemberRepository;
import com.kosta.bank.service.AccountService;
import com.kosta.bank.service.MemberService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BankjpaApplicationTests {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private AccountService accountService;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private MemberService memberService;

	@Test
	void contextLoads() {
	}

	@Test
	void selectAccountTest() {
		Optional<Account> oacc = accountRepository.findById("1234"); // 특정 칼럼을 가져오는건 findbyid다
		if (oacc.isPresent()) {
			System.out.println(oacc.get());

		}
	}

	@Test
	void insertAccountTest() {
//		Account acc = new Account("1234", "금요일", 30000, "Special", "VIP");
////		accountRepository.save(acc); // 기본 리파지토리에 데이터를 넣는거는 save다
//		try {
//			accountService.makeAccount(acc);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	@Test
	void updateAccountTest() {
		Account acc = new Account("999", "금요일", 400000, "Special", "VIP");
		accountRepository.save(acc); // 기본 리파지토리에 데이터를 넣는거는 save다
	}

	@Test
	void deleteAccountTest() {
		accountRepository.deleteById("999");
		; // 기본 리파지토리에 데이터를 넣는거는 save다
	}

	@Test
	void selectAllAccountTest() {
		List<Account> accList = accountRepository.findAll();
		for (Account acc : accList) {
			System.out.println(acc);
		}

	}

	@Test
	void insertMember() {
		Member member = Member.builder().id("jude").name("주드").password("1234").email("email").address("레알마드리드")
				.build();
		memberRepository.save(member);
	}

}
