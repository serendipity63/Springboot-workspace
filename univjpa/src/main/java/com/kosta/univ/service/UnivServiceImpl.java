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
	@Transactional
	public List<Student> studentListInDept1ByDeptName(String deptName) throws Exception {
		Optional<Department> odepartment = departmentRepository.findByDname(deptName);
		if (odepartment.isEmpty())
			throw new Exception("학과명 오류");
		return new ArrayList<>(odepartment.get().getStudentList1());
		// return studentRepository.findByDept1_deptno(odepartment.get().getDeptno());
	}

	@Override
	@Transactional
	public List<Student> studentListInDept2ByDeptName(String deptName) throws Exception {
		Optional<Department> odepartment = departmentRepository.findByDname(deptName);
		if (odepartment.isEmpty())
			throw new Exception("학과명 오류");
		return new ArrayList<>(odepartment.get().getStudentList2());
		// return studentRepository.findByDept2_deptno(odepartment.get().getDeptno());
	}

	@Override
	public List<Student> studentListByGrade(Integer grade) throws Exception {
		return studentRepository.findByGrade(grade);
	}

	@Override
	public List<Student> studentListByNoProfessor() throws Exception {
		return studentRepository.findByProfessorIsNull();
	}

	@Override
	public Student studentByStudno(Integer studno) throws Exception {
		Optional<Student> ostudent = studentRepository.findById(studno);
		if (ostudent.isEmpty())
			throw new Exception("학번 오류");
		return ostudent.get();
	}

	@Override
	public Student studentByJumin(String jumin) throws Exception {
		Optional<Student> ostudent = studentRepository.findByJumin(jumin);
		if (ostudent.isEmpty())
			throw new Exception("주민번호 오류");
		return ostudent.get();
	}

	@Override
	@Transactional
	public List<Student> studentListByProfNo(Integer profNo) throws Exception {
		Optional<Professor> oprofessor = professorRepository.findById(profNo);
		if (oprofessor.isEmpty())
			throw new Exception("교수번호 오류");
		return new ArrayList<>(oprofessor.get().getStudentList());
	}

	@Override
	@Transactional
	public List<Student> studentListByProfName(String profName) throws Exception {
		List<Professor> professorList = professorRepository.findByName(profName);
		List<Student> studentList = new ArrayList<>();
		for (Professor professor : professorList) {
			studentList.addAll(professor.getStudentList());
		}
		return studentList;
	}

	@Override
	public Professor professorByProfNo(Integer profNo) throws Exception {
		Optional<Professor> oprofessor = professorRepository.findById(profNo);
		if (oprofessor.isEmpty())
			throw new Exception("교수번호 오류");
		return oprofessor.get();
	}

	@Override
	public List<Professor> professorListByProfName(String profName) throws Exception {
		return professorRepository.findByName(profName);
	}

	@Override
	@Transactional
	public List<Professor> professorListByDeptNo(Integer deptNo) throws Exception {
		Optional<Department> odepartment = departmentRepository.findById(deptNo);
		if (odepartment.isEmpty())
			throw new Exception("학과번호 오류");
		return new ArrayList<>(odepartment.get().getProfessorList());
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
		return odepartment.get();
	}

	@Override
	public Department departmentByDeptName(String deptName) throws Exception {
		Optional<Department> odepartment = departmentRepository.findByDname(deptName);
		if (odepartment.isEmpty())
			throw new Exception("학과이름 오류");
		return odepartment.get();
	}

	@Override
	public List<Department> departmentListByPart(Integer part) throws Exception {
		return departmentRepository.findByPart(part);
	}

	@Override
	public List<Department> departmentListByBuild(String build) throws Exception {
		return departmentRepository.findByBuild(build);
	}

}
