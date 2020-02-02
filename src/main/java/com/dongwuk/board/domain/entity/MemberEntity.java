package com.dongwuk.board.domain.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member")
public class MemberEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 100, nullable = false)
	private String email;
	
	@Column(length = 100, nullable = false)
	private String name;
	
	@Column(length = 100, nullable = false)
	private String password;
	
	@OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY)
	private Collection<BoardEntity> boardEntities;
	
	@Builder
	public MemberEntity(Long id, String email, String name, String password, ArrayList<BoardEntity> boardEntities) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.boardEntities = boardEntities;
	}
	
	@PrePersist
	public void init() {
		boardEntities = new ArrayList<BoardEntity>();	
				
	}
}
