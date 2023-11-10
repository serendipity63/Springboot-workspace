package com.kosta.board.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BoardLikeDao {
	Integer selectBoardLike(Map<String, Object> param) throws Exception;

	void insertBoardLike(Map<String, Object> param) throws Exception;

	void deleteBoardLike(Map<String, Object> param) throws Exception;

	void plusBoardLikeCount(Integer num) throws Exception;

	void minusBoardLikeCount(Integer num) throws Exception;

}
