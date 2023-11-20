package com.kosta.board.service;

import com.kosta.board.dto.MemberDto;

public interface MemberService {
	MemberDto login(String id, String password) throws Exception;

	void join(MemberDto member) throws Exception;

}
