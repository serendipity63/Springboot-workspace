package com.kosta.bank.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kosta.bank.dto.Account;

@Mapper
@Repository
//외부I/O 처리 : @Repository (퍼시스턴스 레이어, DB나 파일같은 외부 I/O 작업을 처리함)
public interface AccountDao {
	void insertAccount(Account acc) throws Exception;
	
	Account selectAccount(String id) throws Exception;
	
	void updateBalance(@Param("id") String id, @Param("balance") Integer balance) throws Exception;
	//파라미터가 여러개일때 param 을 dao에 명시적으로 지정해준다 
	//마이바티스때문에 쓰는거@param은 이게 경로를 지정해주는 느낌? 타입이 똑같으면 못 찾아간다 if string id string balnce면 못 찾는다
	
	List<Account> selectAccountList() throws Exception;
	
}
