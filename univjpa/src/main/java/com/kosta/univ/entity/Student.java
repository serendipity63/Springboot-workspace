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
	private String jumin;
	@Column
	private Date birthday;
	@Column
	private String tel;
	@Column
	private Integer height;
	@Column
	private Integer weight;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "profno")
	private Professor professor;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "deptno1")
	private Department dept1;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "deptno2")
	private Department dept2;

	@Override
	public String toString() {
		return String.format("[%d,%s,%s,%d,%s,%s,%s,%d,%d,%d,%s,%d,%s,%d,%s]", studno, name, id, grade, jumin, birthday,
				tel, height, weight, dept1.getDeptno(), dept1.getDname(), dept2 == null ? null : dept2.getDeptno(),
				dept2 == null ? null : dept2.getDname(), professor == null ? null : professor.getProfno(),
				professor == null ? null : professor.getName());
	}

}
