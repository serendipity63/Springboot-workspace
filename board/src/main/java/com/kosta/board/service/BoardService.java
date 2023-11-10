package com.kosta.board.service;

import java.io.OutputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.board.dto.Board;
import com.kosta.board.util.PageInfo;

public interface BoardService {
	List<Board> boardListByPage(PageInfo pageInfo) throws Exception;

	Board boardDetail(Integer num) throws Exception;

	void readImage(Integer num, OutputStream out) throws Exception;

	void boardWrite(Board board, MultipartFile file) throws Exception;

	void boardModify(Board board, MultipartFile file) throws Exception;

	void boardDelete(Integer num) throws Exception;

	List<Board> searchListByPage(String type, String keyword, PageInfo pageInfo) throws Exception;

//	Boolean isBoardLike(String userId, Integer boardNum) throws Exception; // 사용자가 글을 선택했는지 여부
//
//	Boolean selectBoardLike(String userId, Integer boardNum) throws Exception; // 사용자가 좋아요를 눌렀을 경우 처리하고 선택여부 가져오기

}
