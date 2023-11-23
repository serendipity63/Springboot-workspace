package com.kosta.univ.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
//@Data // @Getter, @Setter. @ToString, @RequiredArgContstructor tostring은 안써야할 때가 있다
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer deptno;

	@Column
	private String dname;
	@Column
	private Integer part;
	@Column
	private String build;

	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
	private List<Professor> professorList = new ArrayList<>();

	@OneToMany(mappedBy = "dept1", fetch = FetchType.LAZY)
	private List<Student> studentList1 = new ArrayList<>();
	@OneToMany(mappedBy = "dept2", fetch = FetchType.LAZY)
	private List<Student> studentList2 = new ArrayList<>();

	@Override
	public String toString() {
		return String.format("[%d,%s,%d,%s]", deptno, dname, part, build);
	}
}
