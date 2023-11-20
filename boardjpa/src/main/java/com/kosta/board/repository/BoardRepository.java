package com.kosta.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
//list는 옵셔널로 안쓴다
	Page<Board> findBySubjectContains(String subject, PageRequest pageRequest);

	Page<Board> findByContentContains(String content, PageRequest pageRequest);

	Page<Board> findByMember_Id(String content, PageRequest pageRequest);
// under바 후 속성을 써준다
//	List<Board> findByWriter(String writer);

}
