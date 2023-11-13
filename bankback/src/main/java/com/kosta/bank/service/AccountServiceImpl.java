package com.kosta.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.bank.dao.AccountDao;
import com.kosta.bank.dto.Account;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao accountDao; // 객체가 아님 그냥 레퍼런스 오토와이어드 해야만 들어감

	@Override
	public void makeAccount(Account acc) throws Exception {
		Account sacc = accountDao.selectAccount(acc.getId());
		if (sacc != null)
			throw new Exception("계좌번호 중복");
		accountDao.insertAccount(acc);
	}

	@Override
	public Account accountInfo(String id) throws Exception {
		Account acc = accountDao.selectAccount(id);
		if (acc == null)
			throw new Exception("계좌번호 오류");
		return acc;
	}

	@Override
	public Integer deposit(String id, Integer money) throws Exception {
		Account acc = accountInfo(id);
		acc.deposit(money);
		accountDao.updateBalance(acc.getId(), acc.getBalance());
		return acc.getBalance();
	}

	@Override
	public Integer withdraw(String id, Integer money) throws Exception {
		Account acc = accountInfo(id);
		acc.withdraw(money);
		accountDao.updateBalance(acc.getId(), acc.getBalance());
		return acc.getBalance();

	}

	@Override
	public List<Account> allAccountInfo() throws Exception {
		return accountDao.selectAccountList();
	}

}
