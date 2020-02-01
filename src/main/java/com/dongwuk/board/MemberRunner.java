package com.dongwuk.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dongwuk.board.dto.MemberDto;
import com.dongwuk.board.service.MemberService;

@Component
public class MemberRunner implements ApplicationRunner{

	@Autowired
	MemberService memberService;
	
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
		System.out.println("ADMIN ACCOUNT : " + member.getEmail() + ", password : " + member.getPassword());
		
		member.setId((long)2);
		member.setEmail("123");
		member.setName("회원123");
		member.setPassword("123");
		
		memberService.joinUser(member);
		System.out.println("MEMBER ACCOUNT : " + member.getEmail() + ", password : " + member.getPassword());
	}	
}
