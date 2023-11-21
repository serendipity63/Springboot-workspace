package com.kosta.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.board.dto.BoardDto;
import com.kosta.board.service.BoardService;
import com.kosta.board.util.PageInfo;

@RestController
public class BoardController {

	@Autowired
	private BoardService boardService;

	@GetMapping("/boardlist/{page}")
	public ResponseEntity<Map<String, Object>> boardlist(@PathVariable(required = false) Integer page) {
		try {
			PageInfo pageInfo = new PageInfo(page);
			List<BoardDto> boardList = boardService.boardListByPage(pageInfo);
			Map<String, Object> res = new HashMap<>();
			res.put("boardList", boardList);
			res.put("pageInfo", pageInfo);
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/boardsearch/{page}/{type}/{keyword}")
	public ResponseEntity<Map<String, Object>> boardSearch(@PathVariable(required = false) Integer page,
			@PathVariable(required = false) String type, @PathVariable(required = false) String keyword) {
		try {
			PageInfo pageInfo = new PageInfo(page);
			List<BoardDto> boardList = boardService.searchListByPage(type, keyword, pageInfo);
			Map<String, Object> res = new HashMap<>();
			res.put("boardList", boardList);
			res.put("pageInfo", pageInfo);
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);

		}

	}
	// object로 했었을때는 리턴되는걸 int로 주고 싶어서?

//	@GetMapping("/boarddelete/{num}")
	@DeleteMapping("/boarddelete/{num}")
	public ResponseEntity<Integer> boardDelete(@PathVariable Integer num) {
		try {
			boardService.boardDelete(num);
			return new ResponseEntity<Integer>(num, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/boarddetail/{sect}/{num}") // 보드랑 하트 둘다 가져가기 위해서 맵으로 바꿈
	public ResponseEntity<Map<String, Object>> boardDetail(@PathVariable String sect, @PathVariable Integer num) {
		try {
			Map<String, Object> res = new HashMap<>();
			BoardDto board = boardService.boardDetail(num);
			res.put("board", board);
//			res.put("heart", heart);
			if (sect.equals("only-detail")) {
				boardService.plusViewCount(num);
				Boolean heart = boardService.isHeartBoard("lee", num); // null대신 사용자 아이디를 가져와야 해서 null값으로 우선 냅둠
				res.put("heart", heart);
//				res.put("heart", false); // true면 좋아요 눌림, false면 아님
			} else if (sect.equals("after-modify")) {
				Boolean heart = boardService.isHeartBoard("lee", num); // null대신 사용자 아이디를 가져와야 해서 null값으로 우선 냅둠
				res.put("heart", heart); // true면 좋아요 눌림, false면 아님
//				res.put("heart", false);
			}
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/img/{num}")
	public void imageView(@PathVariable Integer num, HttpServletResponse response) {
		try {
			boardService.readImage(num, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/boardmodify/{num}")
	public ResponseEntity<BoardDto> boardModify(@PathVariable Integer num) {
		try {
			BoardDto board = boardService.boardDetail(num);
			return new ResponseEntity<BoardDto>(board, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<BoardDto>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/boardlike/{num}")
	public ResponseEntity<Map<String, Object>> boardLike(@PathVariable Integer num) {
		try {
			Map<String, Object> res = new HashMap<>();
			Boolean selectBoard = boardService.selHeartBoard("lee", num);
			res.put("isSelect", selectBoard);
			Integer likeCount = boardService.boardDetail(num).getLikecount();
			res.put("likeCount", likeCount);
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);

		}
	}

	@PostMapping("/boardwrite") // modelattribute은 폼을 수동으로 자바스크립트로 가져올 수 있다? json형식이 아니다
	public ResponseEntity<Integer> boardWrite(@ModelAttribute BoardDto board, List<MultipartFile> file) {
//		System.out.println(file.size());
		try {

			Integer num = boardService.boardWrite(board, file);
			System.out.println(num);
			return new ResponseEntity<Integer>(num, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);

		}
	}

	@PostMapping("/boardmodify") // 글 수정 페이지
	public ResponseEntity<Integer> boardModify(@ModelAttribute BoardDto board,
			@RequestParam(value = "file", required = false) List<MultipartFile> file) {
		try {
			Integer num = boardService.boardModify(board, file);
			return new ResponseEntity<Integer>(num, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
	}

}