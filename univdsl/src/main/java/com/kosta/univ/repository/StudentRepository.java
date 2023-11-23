package com.kosta.univ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.univ.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

	List<Student> findByName(String name);

	List<Student> findByDeptno1(Integer deptno);

	List<Student> findByGrade(Integer grade);

	List<Student> findByDeptno1OrDeptno2(Integer deptno1, Integer deptno2);

}
