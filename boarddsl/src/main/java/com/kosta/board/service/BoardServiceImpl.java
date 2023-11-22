package com.kosta.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kosta.board.entity.Board;
import com.kosta.board.entity.Member;
import com.kosta.board.repository.BoardDslRepository;
import com.kosta.board.repository.MemberRepository;
import com.kosta.board.util.PageInfo;
import com.querydsl.core.Tuple;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDslRepository boardDslRepository;
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
	public Member memberByBoardNum(Integer num) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Board boardInfo(Integer num) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Board> boardListByWriterId(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Board> boardListByWriterName(String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean selectBoardLike(String id, Integer num) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean selectedBoardLike(String id, Integer num) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
