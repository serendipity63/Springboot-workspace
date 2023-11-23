package com.kosta.univ.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.univ.entity.Department;
import com.kosta.univ.entity.Professor;
import com.kosta.univ.entity.Student;
import com.kosta.univ.repository.DepartmentRepository;
import com.kosta.univ.repository.ProfessorRepository;
import com.kosta.univ.repository.StudentRepository;
import com.kosta.univ.repository.UnivRepository;
import com.querydsl.core.Tuple;

@Service
public class UnivServiceImpl implements UnivService {

	@Autowired
	private UnivRepository univRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private ProfessorRepository professorRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public void enterStudent(Student stud) throws Exception {
		studentRepository.save(stud);

	}

	@Override // 학번으로 학생 정보 조회 //info
	public Student getStudentByNo(Integer studno) throws Exception {
		Optional<Student> ostud = studentRepository.findById(studno);
		return ostud.get();
	}

	@Override // 학번으로 학생 정보 조회(학과명 포함)
	public Map<String, Object> getStudentByNoWithDeptName(Integer studno) throws Exception {
		Tuple tuple = univRepository.findStudentWithDeptNameByStudno(studno);
		Student student = tuple.get(0, Student.class);
		String deptName = tuple.get(1, String.class);
		Map<String, Object> map = new HashMap<>();
		map.put("studno", student.getStudno());
		map.put("name", student.getName());
		map.put("id", student.getId());
		map.put("grade", student.getGrade());
		map.put("jumin", student.getJumin());
		map.put("birthday", student.getBirthday());
		map.put("tel", student.getTel());
		map.put("height", student.getHeight());
		map.put("weight", student.getWeight());
		map.put("deptno1", student.getDeptno1());
		map.put("deptno2", student.getDeptno2());
		map.put("profno", student.getStudno());
		map.put("deptname", deptName);
		return map;
	}

	@Override
	public Map<String, Object> getStudentByNoWithProfName(Integer studno) throws Exception {
		Tuple tuple = univRepository.findStudentWithProfNameByStudno(studno);
		Student student = tuple.get(0, Student.class);
		String profName = tuple.get(1, String.class);
		Map<String, Object> map = new HashMap<>();
		map.put("studno", student.getStudno());
		map.put("name", student.getName());
		map.put("id", student.getId());
		map.put("grade", student.getGrade());
		map.put("jumin", student.getJumin());
		map.put("birthday", student.getBirthday());
		map.put("tel", student.getTel());
		map.put("height", student.getHeight());
		map.put("weight", student.getWeight());
		map.put("deptno1", student.getDeptno1());
		map.put("deptno2", student.getDeptno2());
		map.put("profno", student.getStudno());
		map.put("profname", profName);

		return map;
	}

	@Override
	public Map<String, Object> getStudentByNoWithDeptNameAndProfName(Integer studno) throws Exception {
		Tuple tuple = univRepository.findStudentWithDeptNameAndProfNameByStudno(studno);
		Student student = tuple.get(0, Student.class);
		String profName = tuple.get(1, String.class);
		String deptname = tuple.get(1, String.class);

		Map<String, Object> map = new HashMap<>();
		map.put("studno", student.getStudno());
		map.put("name", student.getName());
		map.put("id", student.getId());
		map.put("grade", student.getGrade());
		map.put("jumin", student.getJumin());
		map.put("birthday", student.getBirthday());
		map.put("tel", student.getTel());
		map.put("height", student.getHeight());
		map.put("weight", student.getWeight());
		map.put("deptno1", student.getDeptno1());
		map.put("deptno2", student.getDeptno2());
		map.put("profno", student.getStudno());
		map.put("profname", profName);
//		map.put("deptname", deptName);

		return map;
	}

	@Override
	public List<Student> getStudentByName(String name) throws Exception {
		return studentRepository.findByName(name);
	}

	@Override
	public List<Student> getStudentByDeptNo(Integer deptno) throws Exception {
		return studentRepository.findByDeptno1(deptno);
	}

	@Override
	public List<Student> getStudentByDeptName(String deptName) throws Exception {
		return univRepository.findStudentListByDeptName(deptName);
	}

	@Override
	public List<Student> getStudentByGrade(Integer grade) throws Exception {
		return studentRepository.findByGrade(grade);
	}

	@Override
	public List<Student> getStudentByDeptNameAndGrade(String deptName, Integer grade) throws Exception {
		return univRepository.findStudentByDeptNameAndGrade(deptName, grade);
	}

	@Override
	public List<Student> getStudentByDeptno1OrDeptno2(Integer deptno1, Integer deptno2) throws Exception {
		return studentRepository.findByDeptno1OrDeptno2(deptno1, deptno2);
	}

	@Override
	public List<Student> getStudentByDeptno1OrDeptno2(Integer deptno) throws Exception {
		return null;
	}

	@Override
	public List<Student> getStudentByDeptName1OrDeptName2(String deptName1, String deptName2) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> getStudentByDeptName1OrDeptName2(String deptName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> getStudentByProfessorNo(Integer profno) throws Exception {
		return univRepository.getStudentByProfessorNo(profno);
	}

	@Override
	public void enterProfessor(Professor prof) throws Exception {
		professorRepository.save(prof);
	}

	@Override
	public Professor getProfessorByProfno(Integer profno) throws Exception {
		return univRepository.getProfessorByProfno(profno);
	}

	@Override
	public List<Professor> getProfessorByProfName(String name) throws Exception {
		return professorRepository.findByName(name);
	}

	@Override
	public Map<String, Object> getProfessorByProfnoWithDeptName(Integer profno) throws Exception {
		return null;
	}

	@Override
	public Professor getProfessorByStudno(Integer studno) throws Exception {
		return null;
	}

	@Override
	public List<Professor> getProfessorByDeptno(Integer deptno) throws Exception {
		return null;
	}

	@Override
	public List<Professor> getProfessorByDeptName(Integer deptName) throws Exception {
		return null;
	}

	@Override
	public void foundDepartment(Department dept) throws Exception {
		departmentRepository.save(dept);
	}

	@Override
	public Department getDepartmentByDeptno(Integer deptno) throws Exception {
		return univRepository.getDepartmentByDeptno(deptno);
	}

	@Override
	public Department getDepartmentByDeptName(String name) throws Exception {
//		return departmentRepository.findByDname(name);
		return null;
	}

	@Override
	public Department getDepartmentByStudNo(Integer studno) throws Exception {
		return null;
	}

	@Override
	public List<Department> getDepartmentByBuild(String build) throws Exception {
		return departmentRepository.findByBuild(build);
	}

}
