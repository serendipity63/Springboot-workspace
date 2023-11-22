package com.kosta.bank;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.kosta.bank.entity.Account;
import com.kosta.bank.entity.Member;
import com.kosta.bank.repository.BankRepository;

@SpringBootTest
class BankdslApplicationTests {
	@Autowired
	BankRepository bankRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void findAccountById() {
		Account acc = bankRepository.findAccountById("1234");
		System.out.println(acc);
	}

	@Test
	void findAllAccount() {
		System.out.println(bankRepository.findAllAccount());
	}

	@Commit // commit is only for test
	@Test
	@Transactional // 2개의 쿼리문이 연속적일 경우는 transactional
	void updateBalance() {
		bankRepository.updateBalance("0000", 4000000);
		Account acc = bankRepository.findAccountById("0000");
		System.out.println(acc);
	}

	@Test
	@Commit
	void insertAccount() {
		Account acc = Account.builder().id("1333").name("수요일").balance(1000000).type("normal").build();
		bankRepository.insertAccount(acc);

	}

	@Test
	void selectMember() {
		Member member = bankRepository.selectMemberById("lee");
		System.out.println(member);
	}

	@Test
	void selectMemberByIdAndPassword() {
		Member member = bankRepository.selectMemberByIdAndPassword("lee", "1234");
		System.out.println(member);
	}

}
