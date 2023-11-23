package com.kosta.univ.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.univ.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	List<Student> findByName(String name);

	List<Student> findByDept1_deptno(Integer deptno1);

	List<Student> findByDept2_deptno(Integer deptno2);

	List<Student> findByGrade(Integer grade);

	List<Student> findByProfessorIsNull();

	Optional<Student> findByJumin(String jumin);

}
