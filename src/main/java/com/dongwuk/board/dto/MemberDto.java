package com.dongwuk.board.dto;

import com.dongwuk.board.domain.entity.MemberEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {
	private Long id;
	private String email;
	private String name;
	private String password;
	
	public MemberEntity toEntity() {
		return MemberEntity.builder()
				.id(id)
				.name(name)
				.email(email)
				.password(password)
				.build();
	}
	
	@Builder
	public MemberDto(Long id, String email, String name, String password) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
	}
}
