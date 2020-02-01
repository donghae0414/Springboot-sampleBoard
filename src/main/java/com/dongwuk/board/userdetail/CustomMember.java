package com.dongwuk.board.userdetail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dongwuk.board.domain.Role;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@SuppressWarnings("serial")
@Getter
@Setter
public class CustomMember implements UserDetails {

	private Long id;
	private String email;
	private String name;
	private String password;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		if(("donghae0414@naver.com").equals(email)) {
			authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
		}else {
			authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
		}
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	
	public String getEmail() {
		return email;
	}
	
	@Override
	public String getUsername() {
		return name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
