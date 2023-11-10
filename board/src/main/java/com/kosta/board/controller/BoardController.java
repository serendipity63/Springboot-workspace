package com.kosta.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.board.dto.Board;
import com.kosta.board.service.BoardService;
import com.kosta.board.util.PageInfo;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

//	@Autowired
//	private HttpSession session;

	@GetMapping("/main")
	public String main() {
		return "main";
	}

	@GetMapping({ "/boardlist/{page}", "/boardlist" })
	public ModelAndView boardlist(@PathVariable(required = false) Integer page) {
		// paging처리를 path variable로
		// 요청 URI의 매핑 템플릿 변수를 처리하는 데 사용할 수 있다
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurPage(page == null ? 1 : page);

		ModelAndView mav = new ModelAndView();
		try {
			List<Board> boardList = boardService.boardListByPage(pageInfo);
			mav.addObject("boardList", boardList);
			mav.addObject("pageInfo", pageInfo);
			mav.setViewName("boardlist");
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", "게시글 목록 조회 오류");
			mav.setViewName("error");
		}

		return mav;
	}

	@GetMapping("/boardwrite")
	public String boardWrite() {
		return "writeform";
	}

	@GetMapping("/boarddetail/{num}/{page}")
	public ModelAndView boardDetail(@PathVariable(name = "num") Integer num,
			@PathVariable(name = "page") Integer page) {
		ModelAndView mav = new ModelAndView();
		try {
			Board board = boardService.boardDetail(num);
			mav.addObject("board", board);
			mav.addObject("page", page);
			mav.setViewName("detailform");
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", "게시글 상세 보기 실패");
			mav.setViewName("error");
		}

		return mav;
	}

	@GetMapping("/image/{num}")
	public void imageView(@PathVariable(name = "num") Integer num, HttpServletResponse response) {
		try {
			boardService.readImage(num, response.getOutputStream());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/boardmodify/{num}/{page}")
	public ModelAndView boardModify(@PathVariable(name = "num") Integer num,
			@PathVariable(name = "page") Integer page) {
		ModelAndView mav = new ModelAndView();
		try {
			Board board = boardService.boardDetail(num);
			mav.addObject("board", board);
			mav.setViewName("modify");

		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", "게시글 수정 오류");
			mav.setViewName("error");
		}
		return mav;
	}

	@GetMapping("/boarddelete/{num}/{page}")
	public String boardDelete(@PathVariable("num") Integer num, @PathVariable("page") Integer page, Model model) {
		try {
			boardService.boardDelete(num);
			return "redirect:/boardlist/" + page;
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("err", e.getMessage());
			return "error";
		}
	}

	@PostMapping("/boardwrite")
	public ModelAndView boardWrite(@ModelAttribute Board board, MultipartFile file) {
		ModelAndView mav = new ModelAndView();
		try {
			boardService.boardWrite(board, file);
			mav.addObject("page", "1");
			mav.setViewName("detailform");
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", "게시글 등록 오류");
		}
		return mav;
	}

	@PostMapping("/boardmodify")
	public ModelAndView boardModify(@ModelAttribute Board board, MultipartFile file,
			@RequestParam("page") Integer page) {
		ModelAndView mav = new ModelAndView();
		try {
			mav.addObject("page", page);
			boardService.boardModify(board, file);
			mav.setViewName("detailform");
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", "게시글 등록 오류");
		}

		return mav;
	}

	@PostMapping("/boardsearch")
	public ModelAndView boardSearch(@RequestParam("type") String type, @RequestParam("keyword") String keyword,
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
		ModelAndView mav = new ModelAndView();
		try {
//			if (type.equals("all") || keyword == null || keyword.trim().equals("")) {
//				mav.setViewName("redirect:/boardlist/" + page);
//				return mav;
//			}
			PageInfo pageInfo = new PageInfo();
			pageInfo.setCurPage(page);
			List<Board> boardList = boardService.searchListByPage(type, keyword, pageInfo);
			mav.addObject("boardList", boardList);
			mav.addObject("pageInfo", pageInfo);
			mav.addObject("keyword", keyword);
			mav.addObject("type", type);
			mav.setViewName("boardlist");
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", "게시글 검색 실패");
			mav.setViewName("error");
		}

		return mav;

	}

//	@PostMapping("/like")
//	@ResponseBody
//	public String boardLike(@RequestParam("num") Integer num) {
//		Member user = (Member) session.getAttribute("user");
//		try {
//			if (user == null)
//				throw new Exception("로그인 필요");
//			Boolean select = boardService.selectBoardLike(user.getId(), num);
//			return String.valueOf(select);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "false";
//		}
//	}

}
