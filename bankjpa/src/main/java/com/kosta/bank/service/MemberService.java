package com.kosta.bank.service;

import com.kosta.bank.dto.MemberDto;

public interface MemberService {
	void join(MemberDto member) throws Exception;

	MemberDto login(String id, String password) throws Exception;

//	void login(String id, String password) throws Exception;
}
