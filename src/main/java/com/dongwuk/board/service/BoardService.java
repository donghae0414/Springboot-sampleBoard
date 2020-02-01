package com.dongwuk.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.dongwuk.board.domain.entity.BoardEntity;
import com.dongwuk.board.domain.repository.BoardRepository;
import com.dongwuk.board.dto.BoardDto;
import com.dongwuk.board.dto.MemberDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BoardService {
	private BoardRepository boardRepository;

	// 블럭 내 페이지 수
	private static final int BLOCK_PAGE_NUM_COUNT = 5;
	// 페이지 내 게시글 수
	private static final int PAGE_POST_COUNT = 4;

	@Transactional
	public Long savePost(BoardDto boardDto, MemberDto memberDto) {
		BoardEntity boardEntity = boardDto.toEntity();
		boardEntity.setMemberEntity(memberDto.toEntity());
		return boardRepository.save(boardEntity).getId();
	}

	@Transactional
	public BoardDto getPost(Long id) {
		Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(id);
		BoardEntity boardEntity = boardEntityWrapper.get();
		
		BoardDto boardDTO = convertEntityToDto(boardEntity); 

		return boardDTO;
	}

	@Transactional
	public void deletePost(Long id) {
		boardRepository.deleteById(id);
	}

	@Transactional
	public List<BoardDto> searchPosts(String keyword) {

		List<BoardEntity> boardEntities = boardRepository.findByTitleContaining(keyword);
		List<BoardDto> boardDtoList = new ArrayList<>();

		for (BoardEntity boardEntity : boardEntities) {
			boardDtoList.add(this.convertEntityToDto(boardEntity));
		}

		return boardDtoList;
	}
	
	@Transactional
	public List<BoardDto> getBoardlist(Integer pageNum) {
//		List<BoardEntity> boardEntities = boardRepository.findAll();

		Page<BoardEntity> page = boardRepository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT));
		List<BoardEntity> boardEntities = page.getContent();
		List<BoardDto> boardDtoList = new ArrayList<>();

		for (BoardEntity boardEntity : boardEntities) {
			boardDtoList.add(this.convertEntityToDto(boardEntity));
		}

		return boardDtoList;
	}

	@Transactional
	public Long getBoardCount() {
		return boardRepository.count();
	}

	public Integer[] getPageList(Integer curPageNum) {
		Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

		// 총 게시물 수
		Double postsTotalCount = Double.valueOf(this.getBoardCount());

		// 마지막 페이지 번호
		Integer totalLastPageNum = (int) (Math.ceil(postsTotalCount / PAGE_POST_COUNT));

		// 현재 블럭 시작 페이지 번호
		curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;
//		curPageNum = blockLastPageNum - 4;

		// 현재 블럭의 마지막 페이지 번호 계산
		Integer blockLastPageNum;
		if (totalLastPageNum > curPageNum - 1 + BLOCK_PAGE_NUM_COUNT) {
			blockLastPageNum = curPageNum - 1 + BLOCK_PAGE_NUM_COUNT;
		} else {
			blockLastPageNum = totalLastPageNum;
		}

//		System.out.println("cur : " + curPageNum + ", blockLast : " + blockLastPageNum);

		for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
			pageList[idx] = val;
		}

		return pageList;
	}

	private BoardDto convertEntityToDto(BoardEntity boardEntity) {
		return BoardDto.builder().id(boardEntity.getId()).writer(boardEntity.getWriter())
				.content(boardEntity.getContent()).title(boardEntity.getTitle())
				.createdDate(boardEntity.getCreatedDate()).build();
	}


	
	/* 게시글 검색 페이징 */
//	@Transactional
//	public List<BoardDto> searchPosts(String keyword, Integer pageNum) {
//
//		List<BoardEntity> boardEntities = boardRepository.findByTitleContaining(keyword,
//				PageRequest.of(pageNum - 1, PAGE_POST_COUNT));
////		List<BoardEntity> boardEntities = boardRepository.findByTitleContaining(keyword);
//		List<BoardDto> boardDtoList = new ArrayList<>();
//
//		for (BoardEntity boardEntity : boardEntities) {
//			boardDtoList.add(this.convertEntityToDto(boardEntity));
//		}
//
//		return boardDtoList;
//	}
//
//	@Transactional
//	public Integer getSearchCount(String keyword) {
//		return boardRepository.findByTitleContaining(keyword).size();
//	}
//	
//	public Integer[] getServicePageList(Integer curPageNum, Integer postsTotalCount) {
//	Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];
//
//	// 마지막 페이지 번호
//	Integer totalLastPageNum = (int) (Math.ceil(postsTotalCount / PAGE_POST_COUNT));
//	
//	System.out.println(postsTotalCount + " " + totalLastPageNum);
//	// 현재 블럭 시작 페이지 번호
//	curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;
////	curPageNum = blockLastPageNum - 4;
//
//	// 현재 블럭의 마지막 페이지 번호 계산
//	Integer blockLastPageNum;
//	if (totalLastPageNum > curPageNum - 1 + BLOCK_PAGE_NUM_COUNT) {
//		blockLastPageNum = curPageNum - 1 + BLOCK_PAGE_NUM_COUNT;
//	} else {
//		blockLastPageNum = totalLastPageNum;
//	}
//
//	System.out.println("Service => cur : " + curPageNum + ", blockLast : " + blockLastPageNum);
//
//	for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
//		pageList[idx] = val;
//	}
//
//	return pageList;
//}
}
