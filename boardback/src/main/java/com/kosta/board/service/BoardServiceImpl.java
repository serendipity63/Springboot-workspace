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
import com.kosta.board.dao.BoardLikeDao;
import com.kosta.board.dto.Board;
import com.kosta.board.dto.FileVo;
import com.kosta.board.util.PageInfo;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDao boardDao;

	@Autowired
	private BoardLikeDao boardLikeDao;

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
	public Integer boardWrite(Board board, List<MultipartFile> files) throws Exception {
		String dir = "c:/jisu/upload/";
		if (files != null && files.size() != 0) {
			String fileNums = "";
			for (MultipartFile file : files) {
				// file table에 insert
				FileVo fileVo = new FileVo();
				fileVo.setDirectory(dir);
				fileVo.setName(file.getOriginalFilename());
				fileVo.setSize(file.getSize());
				fileVo.setContenttype(file.getContentType());
				fileVo.setData(file.getBytes());
				boardDao.insertFile(fileVo);
				// upload 폴더에 upload
				File uploadFile = new File(dir + fileVo.getNum());
				file.transferTo(uploadFile);

				// file 번호 목록 만들기
				if (!fileNums.equals(""))
					fileNums += ",";

				fileNums += fileVo.getNum();

			}

			board.setFileurl(fileNums); // 1,2,3 첫번째꺼 후에 2 3번째에서 코마 앞에다가 붙인다
		}
		boardDao.insertBoard(board); // 보드 테이블에 인서트
		return board.getNum();
	}

	@Override
	public Integer boardModify(Board board, List<MultipartFile> fileList) throws Exception {
		String dir = "c:/jisu/upload/";
		if (fileList != null && fileList.size() != 0) {
			String fileNums = "";
			for (MultipartFile file : fileList) {
				// file table에 insert
				if (file.isEmpty()) {
					fileNums += (fileNums.equals("") ? "" : ",") + file.getOriginalFilename();
				} else {
					// 파일정보 db 삽입
					FileVo fileVo = new FileVo();
					fileVo.setDirectory(dir);
					fileVo.setName(file.getOriginalFilename());
					fileVo.setSize(file.getSize());
					fileVo.setContenttype(file.getContentType());
					fileVo.setData(file.getBytes());
					boardDao.insertFile(fileVo);
					// upload 폴더에 upload
					File uploadFile = new File(dir + fileVo.getNum());
					file.transferTo(uploadFile);
					fileNums += (fileNums.equals("") ? "" : ",") + fileVo.getNum();

				}
			}

			board.setFileurl(fileNums); // 1,2,3 첫번째꺼 후에 2 3번째에서 코마 앞에다가 붙인다
		} else {
			board.setFileurl(null);
		}
		boardDao.updateBoard(board); // 보드 테이블에 인서트
		return board.getNum();

	}

	private void fileDelete(Integer num) throws Exception { // 중복으로 쓰이는건 걍 이렇게 써놔도 ㅇㅋ다
		Board board = boardDao.selectBoard(num);
		if (board == null)
			throw new Exception("글번호 오류");
		String fileUrl = board.getFileurl();
		if (fileUrl != null && !fileUrl.equals("")) {

			String[] fileList = fileUrl.split(",");
			for (String fileNum : fileList) {
				boardDao.deleteFile(Integer.valueOf(fileNum));
				String dir = "c:/jisu/uplad/";
				File file = new File(dir + fileNum);
				if (file.exists()) {
					file.delete();
				}

			}
		}
	}

	@Override
	public void boardDelete(Integer num) throws Exception {
		fileDelete(num);
		boardDao.deleteBoard(num);
	}

	@Override
	public Boolean isHeartBoard(String id, Integer num) throws Exception {
		Integer heartNum = boardLikeDao.selectBoardLike(id, num);
		return heartNum == null ? false : true;
	}

	@Override
	public void selHeartBoard(String id, Integer num) throws Exception {
		boardLikeDao.insertBoardLike(id, num);

	}

	@Override
	public void delHeartBoard(String id, Integer num) throws Exception {
		boardLikeDao.deleteBoardLike(id, num);

	}

}
