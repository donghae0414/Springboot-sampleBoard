package com.dongwuk.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dongwuk.board.dto.MemberDto;
import com.dongwuk.board.service.MemberService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MemberController {
	private MemberService memberService;
	
//	// 메인 페이지
//    @GetMapping("/")
//    public String index() {
//        return "/index";
//    }
	
	// 회원가입
	@GetMapping("/user/signup")
	public String dispSignup() {
		return "/signup";
	}
	
	@PostMapping("/user/signup")
	public String execSignup(MemberDto memberDto) {
		memberService.joinUser(memberDto);
		
		return "redirect:/user/login";
	}
	
	// 로그인
	@GetMapping("/user/login")
	public String dispLogin() {
		return "/login";
	}
	// 로그인 성공 결과
	@GetMapping("/user/login/result")
	public String dispLoginResult() {
//		memberService.nowUser();
//		return "/loginSuccess";
		return "redirect:/";
	}
	
	// 로그아웃 결과
	@GetMapping("/user/logout/result")
	public String dispLogout() {
		return "/logout";
	}
	
	// access denied
	@GetMapping("/user/denied")
	public String dispDenied() {
		return "/denied";
	}
	
	// user info
	@GetMapping("/user/info")
	public String dispMyInfo(Model model) {
		MemberDto memberDto = memberService.getNowUser();
		
		model.addAttribute("member", memberDto);
		
		return "/myinfo";
	}
	
	// admin page
	@GetMapping("/admin")
	public String dispAdmin() {
		return "/admin";
	}
}
