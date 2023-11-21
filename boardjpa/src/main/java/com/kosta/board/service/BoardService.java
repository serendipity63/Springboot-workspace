package com.kosta.board.service;

import java.io.OutputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.board.dto.BoardDto;
import com.kosta.board.util.PageInfo;

public interface BoardService {

	List<BoardDto> boardListByPage(PageInfo pageInfo) throws Exception;

	BoardDto boardDetail(Integer num) throws Exception;

	void readImage(Integer num, OutputStream out) throws Exception;

	Integer boardWrite(BoardDto board, List<MultipartFile> file) throws Exception;

	Integer boardModify(BoardDto board, List<MultipartFile> fileInfo) throws Exception;

	void boardDelete(Integer num) throws Exception;

	void plusViewCount(Integer num) throws Exception;

	List<BoardDto> searchListByPage(String type, String keyword, PageInfo pageInfo) throws Exception;

	Boolean isHeartBoard(String id, Integer num) throws Exception;

	Boolean selHeartBoard(String id, Integer num) throws Exception;

}
