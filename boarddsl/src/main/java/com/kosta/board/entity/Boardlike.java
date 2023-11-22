package com.kosta.board.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Boardlike {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer num;

	@Column
	private String memberId;

	@Column
	private Integer boardNum;

}
