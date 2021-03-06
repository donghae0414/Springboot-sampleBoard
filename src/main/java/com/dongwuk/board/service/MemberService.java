package com.dongwuk.board.service;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dongwuk.board.domain.Role;
import com.dongwuk.board.domain.entity.MemberEntity;
import com.dongwuk.board.domain.repository.MemberRepository;
import com.dongwuk.board.dto.MemberDto;
import com.dongwuk.board.userdetail.CustomMember;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {
	private MemberRepository memberRepository;

	@Transactional
	public MemberEntity joinUser(MemberDto memberDto) {
		// password encode
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

		return memberRepository.save(memberDto.toEntity());
	}

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		Optional<MemberEntity> userEntityWrapper = memberRepository.findByEmail(userEmail);
		MemberEntity userEntity = userEntityWrapper.get();

		List<GrantedAuthority> authorities = new ArrayList<>();

		if (("donghae0414@naver.com").equals(userEmail)) {
			authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
		}

//		CustomMember userDetails = new CustomMember();
//		userDetails.setId(userEntity.getId());
//		userDetails.setEmail(userEntity.getEmail());
//		userDetails.setPassword(userEntity.getPassword());
//		return userDetails;
		
		return new User(userEntity.getEmail(), userEntity.getPassword(), authorities);
	}
}
