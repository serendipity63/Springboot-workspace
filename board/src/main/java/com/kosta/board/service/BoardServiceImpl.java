package com.kosta.board.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.board.dao.BoardDao;
import com.kosta.board.dto.Board;
import com.kosta.board.dto.FileVo;
import com.kosta.board.util.PageInfo;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDao boardDao;

//	@Autowired
//	private BoardLikeDao boardLikeDao;

	@Override
	public List<Board> boardListByPage(PageInfo pageInfo) throws Exception {

		int boardCount = boardDao.selectBoardCount();
		if (boardCount == 0)
			return null;
		int allPage = (int) Math.ceil((double) boardCount / 10);
		int startPage = (pageInfo.getCurPage() - 1) / 10 * 10 + 1;
		int endPage = Math.min(startPage + 10 - 1, allPage);

		pageInfo.setAllPage(allPage);
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);
		if (pageInfo.getCurPage() > allPage)
			pageInfo.setCurPage(allPage);

		int row = (pageInfo.getCurPage() - 1) * 10 + 1;
		return boardDao.selectBoardList(row - 1);

	}

	@Override
	public List<Board> searchListByPage(String type, String keyword, PageInfo pageInfo) throws Exception {
		int searchCount = boardDao.searchBoardCount(type, keyword);
		if (searchCount == 0)
			return null;
		int allPage = (int) Math.ceil((double) searchCount / 10);
		int startPage = (pageInfo.getCurPage() - 1) / 10 * 10 + 1;
		int endPage = Math.min(startPage + 10 - 1, allPage);

		pageInfo.setAllPage(allPage);
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);
		if (pageInfo.getCurPage() > allPage)
			pageInfo.setCurPage(allPage);
		int row = (pageInfo.getCurPage() - 1) * 10 + 1;

		return boardDao.searchBoardList(type, keyword, row - 1);
	}

	@Override
	public Board boardDetail(Integer num) throws Exception {
		Board board = boardDao.selectBoard(num);
		if (board == null)
			throw new Exception("글 번호 오류");
		return boardDao.selectBoard(num);
	}

	@Override
	public void readImage(Integer num, OutputStream out) throws Exception {
		String dir = "c:/jisu/upload/";
		FileInputStream fis = new FileInputStream(dir + num);
		FileCopyUtils.copy(fis, out);
		fis.close();

	}

	@Override
	public void boardWrite(Board board, MultipartFile file) throws Exception {
		if (file != null && !file.isEmpty()) {
			String dir = "c:/jisu/upload/";
			FileVo fileVo = new FileVo();
			fileVo.setDirectory(dir);
			fileVo.setName(file.getOriginalFilename());
			fileVo.setSize(file.getSize());
			fileVo.setContenttype(file.getContentType());
			fileVo.setData(file.getBytes());
			boardDao.insertFile(fileVo);

			File uploadFile = new File(dir + fileVo.getNum());
			file.transferTo(uploadFile);
			board.setFileurl(fileVo.getNum() + "");
		}
		boardDao.insertBoard(board);
	}

	@Override
	public void boardModify(Board board, MultipartFile file) throws Exception {
		if (file != null && !file.isEmpty()) {
			// 파일정보 DB 삽입
			String dir = "c:/jisu/upload/";
			FileVo fileVo = new FileVo();
			fileVo.setDirectory(dir);
			fileVo.setName(file.getOriginalFilename());
			fileVo.setSize(file.getSize());
			fileVo.setContenttype(file.getContentType());
			fileVo.setData(file.getBytes());
			boardDao.insertFile(fileVo);

			// 파일 업로드
			File uploadFile = new File(dir + fileVo.getNum());
			file.transferTo(uploadFile);
			board.setFileurl(fileVo.getNum() + "");
			// 기존 파일 있으면 삭제
			Board sboard = boardDao.selectBoard(board.getNum());
			String orgFileNum = sboard.getFileurl();
			if (orgFileNum != null) {
				boardDao.deleteFile(Integer.valueOf(orgFileNum)); // 테이블에서 삭제

				File orgFile = new File(dir + orgFileNum); // 업로드 폴더에서 삭제
				if (orgFile.exists()) {
					orgFile.delete();
				}
			}
		}
		boardDao.updateBoard(board);
	}

	@Override
	public void boardDelete(Integer num) throws Exception {
		Board board = boardDao.selectBoard(num);
		if (board == null)
			throw new Exception("글번호 오류");
		String fileUrl = board.getFileurl();
		if (fileUrl != null && !fileUrl.equals("")) {
			boardDao.deleteFile(Integer.valueOf(fileUrl));
			String dir = "c:/jisu/uplad/";
			File file = new File(dir + fileUrl);
			if (file.exists()) {
				file.delete();
			}
		}
		boardDao.deleteBoard(num);
	}

//	@Override
//	public Boolean isBoardLike(String userId, Integer boardNum) throws Exception {
//		Map<String, Object> param = new HashMap<>();
//		param.put("id", userId);
//		param.put("num", boardNum);
//		Integer likeNum = boardLikeDao.selectBoardLike(param);
//		return likeNum == null ? false : true;
//	}

//	@Override
//	public Boolean selectBoardLike(String userId, Integer boardNum) throws Exception {
//		Map<String, Object> param = new HashMap<>();
//		param.put("id", userId);
//		param.put("num", boardNum);
//		Integer likeNum = boardLikeDao.selectBoardLike(param);
//		if (likeNum == null) {
//			boardLikeDao.insertBoardLike(param);
//			boardLikeDao.plusBoardLikeCount(boardNum);
//			return true;
//		} else {
//			boardLikeDao.deleteBoardLike(param);
//			boardLikeDao.minusBoardLikeCount(boardNum);
//			return false;
//		}
//	}

}
