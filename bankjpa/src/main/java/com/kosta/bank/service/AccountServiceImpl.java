package com.kosta.bank.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.bank.dto.AccountDto;
import com.kosta.bank.entity.Account;
import com.kosta.bank.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void makeAccount(AccountDto acc) throws Exception {
		Optional<Account> oacc = accountRepository.findById(acc.getId());
		if (oacc.isPresent())
			throw new Exception("계좌번호 중복 오류");
		Account accEntity = modelMapper.map(acc, Account.class); // account를 class화 dto타입으로 바꾼다
		// acc를 account.class로 바꾼다 가지고올때는 dto로 받아서 내보낼때 엔터티로 내보내려고 class는 엔터티타입
		// dto와 entity를 분리
		accountRepository.save(accEntity);
	}

	@Override
	public AccountDto accountInfo(String id) throws Exception {
		Optional<Account> oacc = accountRepository.findById(id);
		if (oacc.isEmpty())
			throw new Exception("계좌번호 오류");
		AccountDto accDto = modelMapper.map(oacc.get(), AccountDto.class);
		return accDto;
	}

	@Override
	public Integer deposit(String id, Integer money) throws Exception {
		Optional<Account> oacc = accountRepository.findById(id);
		if (oacc.isEmpty())
			throw new Exception("계좌번호 오류");
		Account acc = oacc.get();
		acc.deposit(money);
		accountRepository.save(acc);
		return acc.getBalance();

	}

	@Override
	public Integer withdraw(String id, Integer money) throws Exception {
		Optional<Account> oacc = accountRepository.findById(id);
		if (oacc.isEmpty())
			throw new Exception("계좌번호 오류");
		Account acc = oacc.get();
		acc.withdraw(money);
		accountRepository.save(acc);
		return acc.getBalance();
	}

	@Override
	public List<Account> allAccountInfo() throws Exception {
		return accountRepository.findAll();

	}

}
