package com.kosta.univ.entity;

import java.sql.Date;

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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Professor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer profno;
	@Column
	private String name;
	@Column
	private String id;
	@Column
	private String position;
	@Column
	private Integer pay;
	@Column
	private Date hiredate;
	@Column
	private Integer bonus;
	@Column
	private String email;
	@Column
	private String hpage;
	@Column
	private Integer deptno;

}
