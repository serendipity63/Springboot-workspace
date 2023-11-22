package com.kosta.board.service;

import java.util.List;
import java.util.Map;

import com.kosta.board.entity.Board;
import com.kosta.board.entity.Member;
import com.kosta.board.util.PageInfo;

public interface BoardService {
	Member memberInfo(String id) throws Exception;

	String memberName(String id) throws Exception;

	Boolean login(String id, String password) throws Exception;

	Map<String, String> memberNameAndEmail(String id) throws Exception;

	void join(Member member) throws Exception;

	Member memberByBoardNum(Integer num) throws Exception;

	Board boardInfo(Integer num) throws Exception;

	List<Board> boardListByWriterId(String id) throws Exception;

	List<Board> boardListByWriterName(String name) throws Exception;

	List<Board> boardListByPage(PageInfo pageInfo) throws Exception;

	Long boardCount() throws Exception;

	Boolean selectBoardLike(String id, Integer num) throws Exception;

	Boolean selectedBoardLike(String id, Integer num) throws Exception;
}
