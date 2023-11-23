package com.kosta.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.board.entity.Board;
import com.kosta.board.entity.Member;
import com.kosta.board.service.BoardService;
import com.kosta.board.util.PageInfo;

@RestController
public class BoardController {
	@Autowired
	private BoardService boardService;

	@GetMapping({ "/boardlist/{page}", "/boardlist" })
	public ResponseEntity<Map<String, Object>> boardlist(@PathVariable(required = false) Integer page) {
		try {
			PageInfo pageInfo = PageInfo.builder().curPage(page).build();
			List<Board> boardList = boardService.boardListByPage(pageInfo);
			Map<String, Object> res = new HashMap<>();
			res.put("boardList", boardList);
			res.put("pageInfo", pageInfo);
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}

	}

//	@GetMapping("/img/{num}")
//	public void imageView(@PathVariable Integer num, HttpServletResponse response) {
//		try {
//			boardService.readImage(num, response.getOutputStream());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	@GetMapping("/memInfo")
	public ResponseEntity<Object> memberInfo(@RequestParam("id") String id) {
		try {
			Member member = boardService.memberInfo(id);
			if (member == null)
				throw new Exception("아이디 오류");
			return new ResponseEntity<Object>(member, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<Boolean> login(@RequestBody Map<String, String> param) {
		try {
			Boolean isLogin = boardService.login(param.get("id"), param.get("password"));
			return new ResponseEntity<Boolean>(isLogin, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
		}
	}

	// 폼 모델어트리븃
	// 제이슨 requestbody
	@PostMapping("/join")
	public ResponseEntity<Boolean> join(@RequestBody Member member) {
		try {
			boardService.join(member);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/memName")
	public ResponseEntity<String> memberName(@RequestBody Map<String, String> param) {
		try {
			String name = boardService.memberName(param.get("id"));
			if (name == null)
				throw new Exception("아이디 오류");
			return new ResponseEntity<String>(name, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/writerinfo/{boardNum}")
	public ResponseEntity<Member> writerInfo(@PathVariable Integer boardNum) {
		try {
			Member member = boardService.memberInfoByBoardNum(boardNum);
			return new ResponseEntity<Member>(member, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Member>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/boarddetail/{sect}/{num}") // 보드랑 하트 둘다 가져가기 위해서 맵으로 바꿈
	public ResponseEntity<Map<String, Object>> boardDetail(@PathVariable String sect, @PathVariable Integer num) {
		try {
			Map<String, Object> res = new HashMap<>();
			Board board = boardService.boardInfo(num);
			res.put("board", board);
//			res.put("heart", heart);
			if (sect.equals("only-detail")) {
				boardService.plusViewCount(num);
				Boolean heart = boardService.selectedBoardLike("lee", num);
				// null대신 사용자 아이디를 가져와야 해서 null값으로 우선 냅둠
				res.put("heart", heart);
//				res.put("heart", false); // true면 좋아요 눌림, false면 아님
			} else if (sect.equals("after-modify")) {
				Boolean heart = boardService.selectedBoardLike("lee", num);
				// null대신 사용자 아이디를 가져와야 해서 null값으로 우선 냅둠
				res.put("heart", heart); // true면 좋아요 눌림, false면 아님
//				res.put("heart", false);
			}
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/boardlistwi/{id}")
	public ResponseEntity<List<Board>> boardListByWriterId(@PathVariable String id) {
		try {
			List<Board> boardList = boardService.boardListByWriterId(id);
			return new ResponseEntity<List<Board>>(boardList, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Board>>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/boardlistwn/{name}")
	public ResponseEntity<List<Board>> boardListByWriterName(@PathVariable String name) {
		try {
			List<Board> boardList = boardService.boardListByWriterName(name);
			return new ResponseEntity<List<Board>>(boardList, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Board>>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/boardlike/{num}")
	public ResponseEntity<Map<String, Object>> boardLike(@PathVariable Integer num) {
		try {
			Map<String, Object> res = new HashMap<>();
			Boolean selectBoard = boardService.selectBoardLike("lee", num);
			res.put("isSelect", selectBoard);
			Integer likeCount = boardService.boardInfo(num).getLikecount();
			res.put("likeCount", likeCount);
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);

		}
	}

}
