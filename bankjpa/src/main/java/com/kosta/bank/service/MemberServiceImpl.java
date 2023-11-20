package com.kosta.bank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.bank.dto.MemberDto;
import com.kosta.bank.entity.Member;
import com.kosta.bank.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public void join(MemberDto member) throws Exception { // dto를 가지고 entity를 만들어준다
		Optional<Member> omember = memberRepository.findById(member.getId());
		if (omember.isPresent())
			throw new Exception("아이디 중복 오류");
		memberRepository.save(Member.toEntity(member));

	}

	@Override
	public MemberDto login(String id, String password) throws Exception {
		Optional<Member> omember = memberRepository.findById(id);
		if (omember.isEmpty())
			throw new Exception("아이디 오류");
		Member member = omember.get();
		if (!member.getPassword().equals(password))
			throw new Exception("비번오류");
		return MemberDto.toDto(member); // 멤버 엔터티를 가지고 dto를 만들어주는거다
	}

//	Member member=memberDao.selectMember(id);
//	if(member==null) throw new Exception("아이디 오류");
//	if(!member.getPassword().equals(password)) throw new Exception("비번오류");
//	return member;

}
