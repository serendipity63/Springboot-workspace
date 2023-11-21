package com.kosta.univ.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
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
	private Integer jumin;
	@Column
	private Date birthday;
	@Column
	private String tel;
	@Column
	private Integer height;
	@Column
	private Integer weight;
	@Column
	private Integer deptno1;
	@Column
	private Integer deptno2;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "profno")
	private Professor professor;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "deptno1", referencedColumnName = "deptno")
////	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "deptno2", referencedColumnName = "deptno")
//
//	private Department department;

	@Override
	public String toString() {
		return String.format("[%d,%s,%s,%d]", studno, name, id, grade);
	}

}
