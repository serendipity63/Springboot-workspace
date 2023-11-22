package com.kosta.board.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer num;

	@Column
	private String subject;
	@Column
	private String content;
	@Column
	private String fileurl;
	@Column
	@ColumnDefault("0")
	private Integer viewcount;
	@Column
	@ColumnDefault("0")
	private Integer likecount;
	@Column
	@CreationTimestamp
	private Date writedate;
	@Column
	private String writer;

}
