package com.kosta.board.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.board.dto.BoardDto;
import com.kosta.board.entity.Board;
import com.kosta.board.entity.FileVo;
import com.kosta.board.repository.BoardRepository;
import com.kosta.board.repository.FileVoRepository;
import com.kosta.board.util.PageInfo;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private FileVoRepository fileVoRepository;

	@Override
	public List<BoardDto> boardListByPage(PageInfo pageInfo) throws Exception {
		// jpa는 pagerequest 가 있다 - paging처리를 위한 api
		// 1페이지가 0페이지다. index같이
		// 1 페이지는 10개 있다 order orderby descendingsort해서 페이지를 가져온다
		PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage() - 1, 10, Sort.by(Sort.Direction.DESC, "num"));
		Page<Board> pages = boardRepository.findAll(pageRequest);
		// 특정 페이지의 모든걸 가져온다 수동으로 했던 거의 일부를 이게 해준다
		// 이게 allpage를 대체한다

		pageInfo.setAllPage(pages.getTotalPages());
		int startPage = (pageInfo.getCurPage() - 1) / 10 * 10 + 1;
		int endPage = Math.min(startPage + 10 - 1, pageInfo.getAllPage());

		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);
		List<BoardDto> boardDtoList = new ArrayList<>();
		for (Board board : pages.getContent()) {
			boardDtoList.add(board.toDto());

		}
		// 실제 조회한 데이터 목록? 쿼리와 관련된건 다 엔티티

		return boardDtoList;
	}

	@Override
	public List<BoardDto> searchListByPage(String type, String keyword, PageInfo pageInfo) throws Exception {
		// jpa는 pagerequest 가 있다 - paging처리를 위한 api
		// 1페이지가 0페이지다. index같이
		// 1 페이지는 10개 있다 order orderby descendingsort해서 페이지를 가져온다
		PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage() - 1, 10, Sort.by(Sort.Direction.DESC, "num"));
		Page<Board> pages = null;
		if (type.equals("subject")) {
			pages = boardRepository.findBySubjectContains(keyword, pageRequest);

		} else if (type.equals("content")) {
			pages = boardRepository.findByContentContains(keyword, pageRequest);

		} else if (type.equals("writer")) {
			pages = boardRepository.findByMember_Id(keyword, pageRequest);

		} else {
			return null;
		}
		// 특정 페이지의 모든걸 가져온다 수동으로 했던 거의 일부를 이게 해준다
		// 이게 allpage를 대체한다

		pageInfo.setAllPage(pages.getTotalPages());
		int startPage = (pageInfo.getCurPage() - 1) / 10 * 10 + 1;
		int endPage = Math.min(startPage + 10 - 1, pageInfo.getAllPage());

		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);
		List<BoardDto> boardDtoList = new ArrayList<>();
		for (Board board : pages.getContent()) {
			boardDtoList.add(board.toDto());

		}
		// 실제 조회한 데이터 목록? 쿼리와 관련된건 다 엔티티

		return boardDtoList;
	}

	@Override
	public BoardDto boardDetail(Integer num) throws Exception {
		Optional<Board> oboard = boardRepository.findById(num);
		if (oboard.isEmpty())
			throw new Exception("글번호 오류");
		return oboard.get().toDto();
		// num이 null이면 num reference exception이 뜬다.
	}

	@Override
	public void readImage(Integer num, OutputStream out) throws Exception { // 그냥 업로드 폴더에서 읽어오는거
		String dir = "c:/jisu/upload/";
		FileInputStream fis = new FileInputStream(dir + num);
		FileCopyUtils.copy(fis, out);
		fis.close();
	}

	@Override
	public Integer boardWrite(BoardDto boardDto, List<MultipartFile> files) throws Exception {
		if (files != null && files.size() != 0) {
			String dir = "c:/jisu/upload/";
			String fileNums = "";
			for (MultipartFile file : files) {
				// file table에 insert
				FileVo fileVo = FileVo.builder().directory(dir).name(file.getOriginalFilename()).size(file.getSize())
						.contenttype(file.getContentType()).data(file.getBytes()).build();
				fileVoRepository.save(fileVo);

				// upload 폴더에 upload
				File uploadFile = new File(dir + fileVo.getNum());
				file.transferTo(uploadFile);

				// file 번호 목록 만들기
				if (!fileNums.equals(""))
					fileNums += ",";

				fileNums += fileVo.getNum();
			}

			boardDto.setFileurl(fileNums); // 1,2,3 첫번째꺼 후에 2 3번째에서 코마 앞에다가 붙인다
		}
		// board table에 insert
		Board board = boardDto.toEntity();
		boardRepository.save(board);
		return board.getNum();
	}

	@Override
	public Integer boardModify(BoardDto boardDto, List<MultipartFile> files) throws Exception {
		Board board = boardRepository.findById(boardDto.getNum()).get();
		board.setContent(boardDto.getContent());
		board.setSubject(boardDto.getSubject());
		// 사용자가 입력한 값을 엎어씀
		if (files != null && files.size() != 0) {
			String dir = "c:/jisu/upload/";
			String fileNums = "";
			for (MultipartFile file : files) {
				// file table에 insert
				if (file.isEmpty()) {
					fileNums += (fileNums.equals("") ? "" : ",") + file.getOriginalFilename();
				} else {
					// file table에 insert
					FileVo fileVo = FileVo.builder().directory(dir).name(file.getOriginalFilename())
							.size(file.getSize()).contenttype(file.getContentType()).data(file.getBytes()).build();
					fileVoRepository.save(fileVo);
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
		boardRepository.save(board); // 보드 테이블에 인서트 repository에 넣을때 entity로 해야하니까
		return board.getNum();

	}

	@Override
	public void boardDelete(Integer num) throws Exception {
		boardRepository.deleteById(num);
	}

	@Override
	public Boolean isHeartBoard(String id, Integer num) throws Exception {
		return null;
	}

	@Override
	public void selHeartBoard(String id, Integer num) throws Exception {

	}

	@Override
	public void delHeartBoard(String id, Integer num) throws Exception {

	}

	@Override
	public void plusViewCount(Integer num) throws Exception {
		Board board = boardRepository.findById(num).get();
		board.setViewcount(board.getViewcount() + 1);
		boardRepository.save(board);
	}

}

//fileVo.setDirectory(dir);
//fileVo.setName(file.getOriginalFilename());
//fileVo.setSize(file.getSize());
//fileVo.setContenttype(file.getContentType());
//fileVo.setData(file.getBytes());
