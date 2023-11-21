package com.kosta.univ.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.univ.entity.Department;
import com.kosta.univ.entity.Professor;
import com.kosta.univ.entity.Student;
import com.kosta.univ.repository.DepartmentRepository;
import com.kosta.univ.repository.ProfessorRepository;
import com.kosta.univ.repository.StudentRepository;

@Service
public class UnivServiceImpl implements UnivService {
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public List<Student> studentListByName(String studName) throws Exception {
		return studentRepository.findByName(studName);
	}

	@Override
	public List<Student> studentListInDept1ByDeptName(String deptName) throws Exception {
		return null;
	}

	@Override
	public List<Student> studentListInDept2ByDeptName(String deptName) throws Exception {
		return null;
	}

	@Override
	public List<Student> studentListByGrade(Integer grade) throws Exception {
		return studentRepository.findByGrade(grade);
	}

	@Override
	public List<Student> studentListByNoProfessor() throws Exception {
		return null;
	}

	@Override
	public Student studentByStudno(Integer studno) throws Exception {
		return null;
//				studentRepository.findById(studno);
	}

	@Override
	public Student studentByJumin(String jumin) throws Exception {
		return null;
	}

	@Override
	public List<Student> studentListByProfNo(Integer profNo) throws Exception {
		return null;
	}

	@Override
	public List<Student> studentListByProfName(String profName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Professor professorByProfNo(Integer profNo) throws Exception {
		return null;
	}

	@Override
	public List<Professor> professorListByProfName(String profName) throws Exception {
		return null;
	}

	@Override
	public List<Professor> professorListByDeptNo(Integer deptNo) throws Exception {
		return null;
	}

	@Override
	@Transactional
	public List<Professor> professorListByDeptName(String deptName) throws Exception {
		Optional<Department> odepartment = departmentRepository.findByDname(deptName);
		if (odepartment.isEmpty())
			throw new Exception("학과이름 오류");
		return new ArrayList<>(odepartment.get().getProfessorList());
	}

	@Override
	public List<Professor> professorListByPosition(String position) throws Exception {
		return professorRepository.findByPosition(position);
	}

	@Override
	public Department departmentByDeptNo(Integer deptNo) throws Exception {
		Optional<Department> odepartment = departmentRepository.findById(deptNo);
		if (odepartment.isEmpty())
			throw new Exception("학과번호 오류");
		return null;
	}

	@Override
	public Department departmentByDeptName(String deptName) throws Exception {
		return null;
	}

	@Override
	public List<Department> departmentListByPart(Integer part) throws Exception {
		return null;
	}

	@Override
	public List<Department> departmentListByBuild(String build) throws Exception {
		return null;
	}

}
