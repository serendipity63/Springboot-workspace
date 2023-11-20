package com.kosta.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.board.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	Optional<Member> findByEmail(String email); // findbyid는 optional로 준다
}
