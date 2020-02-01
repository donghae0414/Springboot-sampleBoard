package com.dongwuk.board;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dongwuk.board.dto.BoardDto;
import com.dongwuk.board.dto.MemberDto;
import com.dongwuk.board.service.BoardService;
import com.dongwuk.board.service.MemberService;

@Component
public class DatabaseRunner implements ApplicationRunner{

	@Autowired
	MemberService memberService;
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		MemberDto member = new MemberDto();
		member.setId((long)1);
		member.setEmail("donghae0414@naver.com");
		member.setName("양동욱");
		member.setPassword("didehddnr1!");
		// 인코딩은 joinUser 메소드 내에서 처리됨
		memberService.joinUser(member);
		
		BoardDto board = new BoardDto();
		board.setId((long)1);
		board.setContent("첫번째 글입니다");
		board.setTitle("Admin's Post");
		board.setWriter(member.getName());
		board.setCreatedDate(LocalDateTime.now());
		boardService.savePost(board, member);
		System.out.println("ADMIN ACCOUNT : " + member.getEmail() + ", password : " + member.getPassword());
		
		member.setId((long)2);
		member.setEmail("123");
		member.setName("이현호");
		member.setPassword("123");
		memberService.joinUser(member);
		
		board.setId((long)2);
		board.setContent("두번째 글입니다");
		board.setTitle("Member's Post");
		board.setWriter(member.getName());
		board.setCreatedDate(LocalDateTime.now());
		boardService.savePost(board, member);
		System.out.println("MEMBER ACCOUNT : " + member.getEmail() + ", password : " + member.getPassword());
	}	
}
