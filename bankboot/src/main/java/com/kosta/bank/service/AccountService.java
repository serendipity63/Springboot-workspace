package com.kosta.bank.service;

import java.util.List;

import com.kosta.bank.dto.Account;
//Service의 역할은 Dao가 DB에서 받아온 데이터를 전달받아 가공하는 것이다.
public interface AccountService {
	void makeAccount(Account acc) throws Exception;
	Account accountInfo(String id) throws Exception;
	void deposit(String id, Integer money) throws Exception;
	void withdraw(String id, Integer money) throws Exception;
	List<Account> allAccountInfo() throws Exception;
	
}
