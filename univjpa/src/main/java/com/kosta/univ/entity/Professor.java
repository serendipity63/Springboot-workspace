package com.kosta.univ.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "deptno")
	private Department department;

	@OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
	private List<Student> studentList = new ArrayList<>();

	@Override
	public String toString() {
		return String.format("[%d,%s,%s,%s,%d,%s,%d,%s,%s,%d,%s]", profno, name, id, position, pay, hiredate, bonus,
				email, hpage, department.getDeptno(), department.getDname());
	}
}
