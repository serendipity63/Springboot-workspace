package com.kosta.univ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.univ.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	List<Student> findByName(String name);

//	List<Student> findByDept1ByDept
	List<Student> findByGrade(Integer grade);

	List<Student> findByStudno(Integer studno);

	List<Student> findByJumin(Integer jumin);

//	Optional<Boardlike> findByMember_idAndBoard_num(String id, Integer num);

//	List<Student> studentListByName(String studName) throws Exception;//학생 이름으로 학생목록 조회
//	List<Student> studentListInDept1ByDeptName(String deptName) throws Exception; //제1전공으로 학생목록 조회
//	List<Student> studentListInDept2ByDeptName(String deptName) throws Exception; //제2전공으로 학생목록 조회
//	List<Student> studentListByGrade(Integer grade) throws Exception; //학년으로 학생 목록 조회
//	List<Student> studentListByNoProfessor() throws Exception; //담당교수가 없는 학생 목록 조회
//	Student studentByStudno(Integer studno) throws Exception; //학번으로 학생 조회
//	Student studentByJumin(String jumin) throws Exception; //주민번호로 학생 조회

}
