package com.kosta.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.board.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

}
