package com.kosta.univ;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.univ.entity.Student;
import com.kosta.univ.repository.DepartmentRepository;
import com.kosta.univ.repository.ProfessorRepository;
import com.kosta.univ.repository.StudentRepository;

@SpringBootTest
class UnivjpaApplicationTests {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private ProfessorRepository professorRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void selectStudentByName() {
		List<Student> student = studentRepository.findByName("서진수");
		System.out.println(student);
	}
}
