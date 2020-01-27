package com.dongwuk.board.dto;

import java.time.LocalDateTime;

import com.dongwuk.board.domain.entity.BoardEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
	private Long id;
	private String writer;
	private String title;
	private String content;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	
	public BoardEntity toEntity() {
		BoardEntity build = BoardEntity.builder()
				.id(id)
				.writer(writer)
				.title(title)
				.content(content)
				.build();
		return build;
	}
	
	@Builder
	public BoardDto(Long id, String title, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}
}
