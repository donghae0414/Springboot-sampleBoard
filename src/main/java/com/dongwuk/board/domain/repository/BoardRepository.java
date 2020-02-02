package com.dongwuk.board.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dongwuk.board.domain.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>{

	List<BoardEntity> findByTitleContaining(String keyword);
//	List<BoardEntity> findByTitleContaining(String keyword, Pageable pageable);
	
//	@Query("select b from BoardEntity b where b.memberEntity = :member")
//	List<BoardEntity> findByMember(@Param("member") MemberEntity memberEntity);
	
	@Query("select b from BoardEntity b where b.memberEntity.name = :userName")
	List<BoardEntity> findByUserName(@Param("userName") String userName);
}
