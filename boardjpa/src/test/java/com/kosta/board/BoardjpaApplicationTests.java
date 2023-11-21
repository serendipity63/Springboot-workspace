package com.kosta.board;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kosta.board.entity.Board;
import com.kosta.board.entity.Boardlike;
import com.kosta.board.entity.Member;
import com.kosta.board.repository.BoardRepository;
import com.kosta.board.repository.BoardlikeRepository;
import com.kosta.board.repository.MemberRepository;
import com.kosta.board.service.BoardService;

@ExtendWith(SpringExtension.class)

@SpringBootTest
class BoardjpaApplicationTests {
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private BoardlikeRepository boardlikeRepository;
	@Autowired
	private BoardService boardService;

	@Test
	void contextLoads() {
	}

	@Test

	void selectBoard() {
		Board board = boardRepository.findById(9).get();
		System.out.println(board);
		System.out.println(board.getMember());

	}

	@Test
	void selectMember() {
		Member member = memberRepository.findById("lee").get();
		System.out.println(member);
		for (Board board : member.getBoardList()) {
			System.out.println(board);
		}

	}

	@Test
	void selectMemberByEmail() {
		Optional<Member> member = memberRepository.findByEmail("email");
		System.out.println(member.get());
	}

	@Test
	void isHeartSelected() {
		Optional<Boardlike> boardlike = boardlikeRepository.findByMember_idAndBoard_num("lee", 8);
		if (boardlike.isPresent()) {
			System.out.println(boardlike.get());
		} else {
			System.out.println("없음");
		}
	}

	@Test
	void selHeartBoard() { // insert test

		Boardlike boardlike = Boardlike.builder().member(Member.builder().id("park").build())
				.board(Board.builder().num(77).build()).build();
		boardlikeRepository.save(boardlike);

	}

	@Test
	void delHeartBoard() { // delete하는거 테스트
		Optional<Boardlike> boardlike = boardlikeRepository.findByMember_idAndBoard_num("park", 77);

		boardlikeRepository.deleteById(boardlike.get().getNum());

	}

	@Test
	void isHeartSelectedService() {
		try {
			System.out.println(boardService.isHeartBoard("lee", 8));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void setHeartBoardService() {
		try {
			System.out.println(boardService.selHeartBoard("park", 77));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
