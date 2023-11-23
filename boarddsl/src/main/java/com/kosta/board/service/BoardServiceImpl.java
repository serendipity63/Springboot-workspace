package com.kosta.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kosta.board.entity.Board;
import com.kosta.board.entity.Boardlike;
import com.kosta.board.entity.Member;
import com.kosta.board.repository.BoardDslRepository;
import com.kosta.board.repository.BoardRepository;
import com.kosta.board.repository.BoardlikeRepository;
import com.kosta.board.repository.MemberRepository;
import com.kosta.board.util.PageInfo;
import com.querydsl.core.Tuple;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDslRepository boardDslRepository;
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private BoardlikeRepository boardlikeRepository;
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public Member memberInfo(String id) throws Exception {
		return boardDslRepository.findMemberByMemberId(id);
	}

	@Override
	public String memberName(String id) throws Exception {
		return boardDslRepository.findMemberNameByMemberId(id);
	}

	@Override
	public Boolean login(String id, String password) throws Exception {
		Member member = boardDslRepository.findMemberByMemberIdAndPassword(id, password);
		return member == null ? false : true;
	}

	@Override
	public Map<String, String> memberNameAndEmail(String id) throws Exception {
		Tuple tuple = boardDslRepository.findMemberNameAndEmailByMemberId(id);
		String name = tuple.get(0, String.class); // 0번째거의 타입을 넣으줌
		String email = tuple.get(1, String.class); // 1번째거의 타입을 넣으줌
		Map<String, String> res = new HashMap<>();
		res.put("name", name);
//		res.put("name", tuple.get(0,String.class)); 이렇게 써주기도 한다 //이걸 쓰면  이게 String name = tuple.get(0, String.class); 필요 없다

		res.put("email", email);
//		res.put("email", tuple.get(1,String.class)); 이렇게 써주기도 한다

		return res;
	}

	@Override
	public void join(Member member) throws Exception {
		memberRepository.save(member);
	}

	@Override
	public Member memberInfoByBoardNum(Integer num) throws Exception {
		return boardDslRepository.findMemberByBoardNum(num);
	}

	@Override
	public Board boardInfo(Integer num) throws Exception {
		return boardDslRepository.findBoardByBoardNum(num);
	}

	@Override
	public List<Board> boardListByWriterId(String id) throws Exception {
		return boardDslRepository.findBoardListByWriterId(id);
	}

	@Override
	public List<Board> boardListByWriterName(String name) throws Exception {
		return boardDslRepository.findBoardListByWriterName(name);
	}

	@Override
	public List<Board> boardListByPage(PageInfo pageInfo) throws Exception {
		PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage() - 1, 10); // 한 페이지에 보여줘야 할거
		List<Board> boardList = boardDslRepository.findBoardListByPaging(pageRequest);
		// 페이지 처리해줘야함
		Long allCount = boardDslRepository.findBoardCount();
		Integer allPage = allCount.intValue() / pageRequest.getPageSize();
		if (allCount % pageRequest.getPageSize() != 0)
			allPage += 1;
		Integer startPage = (pageInfo.getCurPage() - 1) / 10 * 10 + 1;
		Integer endPage = Math.min(startPage + 10 - 1, allPage);

		pageInfo.setAllPage(allPage);
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);

		return boardList;
	}

	@Override
	public Long boardCount() throws Exception {
		return boardDslRepository.findBoardCount();
	}

	@Override
	public Boolean selectBoardLike(String id, Integer num) throws Exception {
		Board board = boardRepository.findById(num).get();
		Boardlike boardlike = boardDslRepository.findBoardlike(id, num);
		if (boardlike == null) {
			boardlikeRepository.save(Boardlike.builder().memberId(id).boardNum(num).build());
			board.setLikecount(board.getLikecount() + 1);
			boardRepository.save(board);
			return true;
		} else {
			boardlikeRepository.deleteById(boardlike.getNum());
			board.setLikecount(board.getLikecount() - 1);
			boardRepository.save(board);
			return false;
		}
	}

	@Override
	public Boolean selectedBoardLike(String id, Integer num) throws Exception {
		Long cnt = boardDslRepository.findIsBoardlike(id, num);
		if (cnt < 1)
			return false;

		return true;
	}

	@Override
	public void plusViewCount(Integer num) throws Exception {
		Board board = boardRepository.findById(num).get();
		board.setViewcount(board.getViewcount() + 1);
		boardRepository.save(board);

	}

}
