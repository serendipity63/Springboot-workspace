package com.kosta.board.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.kosta.board.entity.Board;
import com.kosta.board.entity.Boardlike;
import com.kosta.board.entity.Member;
import com.kosta.board.entity.QBoard;
import com.kosta.board.entity.QBoardlike;
import com.kosta.board.entity.QMember;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class BoardDslRepository {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;

	// member id로 member 검색
	public Member findMemberByMemberId(String id) throws Exception {
		QMember member = QMember.member;
		return jpaQueryFactory.selectFrom(member).where(member.id.eq(id)).fetchOne();
	}

	// member id로 member name 검색

	public String findMemberNameByMemberId(String id) throws Exception {
		QMember member = QMember.member;
		return jpaQueryFactory.select(member.name).from(member).where(member.id.eq(id)).fetchOne();
	}

	public Tuple findMemberNameAndEmailByMemberId(String id) throws Exception {
		QMember member = QMember.member;
		return jpaQueryFactory.select(member.name, member.email).from(member).where(member.id.eq(id)).fetchOne();
	}

	public Member findMemberByMemberIdAndPassword(String id, String password) throws Exception {
		QMember member = QMember.member;
		return jpaQueryFactory.selectFrom(member).where(member.id.eq(id).and(member.password.eq(password))).fetchOne();
	}

	// 글번호로 작성자 정보 가져오기
	public Member findMemberByBoardNum(Integer num) {
		QBoard board = QBoard.board;
		QMember member = QMember.member;

		return jpaQueryFactory.selectFrom(member).join(board).on(member.id.eq(board.writer)).where(board.num.eq(num))
				.fetchOne();
		// select member.*from member m join board b on(m.id=b.writer) where b.num=10;
	}

	// 글번호로 글 가져오기
	public Board findBoardByBoardNum(Integer num) throws Exception {
		QBoard board = QBoard.board;
		return jpaQueryFactory.selectFrom(board).where(board.num.eq(num)).fetchOne();

	}

	// 작성자 아이디로 글 목록 가져오기
	public List<Board> findBoardListByWriterId(String id) throws Exception {
		QBoard board = QBoard.board;
		return jpaQueryFactory.selectFrom(board).where(board.writer.eq(id)).fetch();

	}

	// 작성자 이름으로 글 목록 가져오기
	public List<Board> findBoardListByWriterName(String name) throws Exception {
		QBoard board = QBoard.board;
		QMember member = QMember.member;
		return jpaQueryFactory.selectFrom(board).join(member).on(board.writer.eq(member.id)).where(member.name.eq(name))
				.fetch();
		// select b.* from board b join member m on(b.writer=m.id) where m.name=name

	}

	// 게시글 목록(특정 페이지)
	public List<Board> findBoardListByPaging(PageRequest pageRequest) throws Exception {
		QBoard board = QBoard.board;

		return jpaQueryFactory.selectFrom(board).orderBy(board.num.desc()).offset(pageRequest.getOffset())
				.limit(pageRequest.getPageSize()).fetch();

	}

	// 글 개수 가져오기
	public Long findBoardCount() throws Exception {
		QBoard board = QBoard.board;

		return jpaQueryFactory.select(board.count()).from(board).fetchOne();
//board.count()로 해야한다
	}

	// boardlike에서 데이터 조회하기
	public Boardlike findBoardlike(String id, Integer num) throws Exception {
		QBoardlike boardlike = QBoardlike.boardlike;
		return jpaQueryFactory.select(boardlike).from(boardlike)
				.where(boardlike.memberId.eq(id).and(boardlike.boardNum.eq(num))).fetchOne();

	}

	// boardlike에서 데이터 조회하기(개수)
	public Long findIsBoardlike(String id, Integer num) throws Exception {
		QBoardlike boardlike = QBoardlike.boardlike;
		return jpaQueryFactory.select(boardlike.count()).from(boardlike)
				.where(boardlike.memberId.eq(id).and(boardlike.boardNum.eq(num))).fetchOne();

	}

}
