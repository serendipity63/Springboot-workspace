package com.kosta.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.board.entity.Boardlike;

public interface BoardlikeRepository extends JpaRepository<Boardlike, Integer> {

}
