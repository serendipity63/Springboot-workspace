package com.kosta.bank.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.kosta.bank.dto.Member;

@Mapper
@Repository
public interface MemberDao {
	void insertMember(Member member) throws Exception;
	
	Member selectMember(String id) throws Exception;
}
