package com.kosta.univ.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.univ.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

	Optional<Department> findByDname(String deptName);
//	Department departmentByDeptNo(Integer deptNo) throws Exception; //학과번호로 학과 조회
//	Department departmentByDeptName(String deptName) throws Exception; //학과이름으로 학과 조회
//	List<Department> departmentListByPart(Integer part) throws Exception; //학부(part)로 학과목록 조회
//	List<Department> departmentListByBuild(String build) throws Exception; //위치하는 건물로 학과목록 조회
}
