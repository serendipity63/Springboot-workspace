package com.kosta.bank.service;

import java.util.List;

import com.kosta.bank.dto.AccountDto;
import com.kosta.bank.entity.Account;

public interface AccountService {
	void makeAccount(AccountDto acc) throws Exception;

	AccountDto accountInfo(String id) throws Exception;

	Integer deposit(String id, Integer money) throws Exception;

	Integer withdraw(String id, Integer money) throws Exception;

	List<Account> allAccountInfo() throws Exception;

}
