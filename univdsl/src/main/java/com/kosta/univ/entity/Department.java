package com.kosta.univ.entity;

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
//@Data // @Getter, @Setter. @ToString, @RequiredArgContstructor tostring은 안써야할 때가 있다
@Data
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

}
