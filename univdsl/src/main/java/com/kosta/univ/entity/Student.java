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
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
//@DynamicInsert
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer studno;
	@Column
	private String name;
	@Column
	private String id;
	@Column
	private Integer grade;
	@Column
	private String jumin;
	@Column
	private Date birthday;
	@Column
	private String tel;
	@Column
	private Integer height;
	@Column
	private Integer weight;
	@Column
	private Integer profno;
	@Column
	private Integer deptno1;
	@Column
	private Integer deptno2;

}
