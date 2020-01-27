package com.dongwuk.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dongwuk.board.dto.BoardDto;
import com.dongwuk.board.service.BoardService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class BoardController {

	private BoardService boardService;

	@GetMapping("/")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
		List<BoardDto> boardDtoList = boardService.getBoardlist(pageNum);
		Integer[] pageList = boardService.getPageList(pageNum);

		model.addAttribute("boardList", boardDtoList);
		model.addAttribute("pageList", pageList);
		return "/board/list.html";
	}

	/* 게시글 작성 */
	@GetMapping("/post")
	public String write() {
		return "/board/write.html";
	}

	@PostMapping("/post")
	public String write(BoardDto boardDto) {
		boardService.savePost(boardDto);

		return "redirect:/";
	}

	/* 상세 게시글 */
	@GetMapping("/post/{no}")
	public String detail(@PathVariable("no") Long no, Model model) {
		BoardDto boardDTO = boardService.getPost(no);

		model.addAttribute("boardDto", boardDTO);
		return "/board/detail.html";
	}

	/* 게시글 수정 */
	@GetMapping("/post/edit/{no}")
	public String edit(@PathVariable("no") Long no, Model model) {
		BoardDto boardDTO = boardService.getPost(no);

		model.addAttribute("boardDto", boardDTO);
		return "/board/update.html";
	}

	@PutMapping("/post/edit/{no}")
	public String update(BoardDto boardDTO) {
		boardService.savePost(boardDTO);

		return "redirect:/post/{no}";
	}

	/* 게시글 삭제 */
	@DeleteMapping("/post/{no}")
	public String delete(@PathVariable("no") Long no) {
		boardService.deletePost(no);

		return "redirect:/";
	}

	/* 게시글 검색 */
	@GetMapping("/board/search")
	public String search(@RequestParam(value = "keyword") String keyword, Model model) {
		
		List<BoardDto> boardDtoList = boardService.searchPosts(keyword);
		
		model.addAttribute("boardList", boardDtoList);

		return "/board/list.html";
	}
	
//	/* 게시글 검색 페이징 */
//	@GetMapping("/board/search")
//	public String search(@RequestParam(value = "keyword") String keyword,
//			@RequestParam(value = "page", defaultValue = "1") Integer pageNum, Model model) {
//		
////		List<BoardDto> list = boardService.getSearchCount(keyword);
//		List<BoardDto> boardDtoList = boardService.searchPosts(keyword, pageNum);
//		Integer[] pageList = boardService.getServicePageList(pageNum, boardService.getSearchCount(keyword));
//
////		model.addAttribute("boardList", boardList);
//		model.addAttribute("pageList", pageList);
//		model.addAttribute("boardList", boardDtoList);
//
//		return "/board/list.html";
//	}
}
