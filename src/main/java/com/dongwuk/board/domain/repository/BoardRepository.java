package com.dongwuk.board.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dongwuk.board.domain.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>{

	List<BoardEntity> findByTitleContaining(String keyword);
//	List<BoardEntity> findByTitleContaining(String keyword, Pageable pageable);
}
