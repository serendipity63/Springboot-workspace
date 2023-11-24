package com.kosta.univ.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.univ.entity.Department;
import com.kosta.univ.entity.Professor;
import com.kosta.univ.entity.QDepartment;
import com.kosta.univ.entity.QProfessor;
import com.kosta.univ.entity.QStudent;
import com.kosta.univ.entity.Student;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class UnivRepository {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;

	public Tuple findStudentWithDeptNameByStudno(Integer studno) {
		QStudent student = QStudent.student;
		QDepartment department = QDepartment.department;
		return jpaQueryFactory.select(student, department.dname).from(student).join(department)
				.on(student.deptno1.eq(department.deptno)).where(student.studno.eq(studno)).fetchOne();
	}

	public Tuple findStudentWithProfNameByStudno(Integer studno) {
		QStudent student = QStudent.student;
		QProfessor professor = QProfessor.professor;
		return jpaQueryFactory.select(student, professor.name).from(student).join(professor)
				.on(student.profno.eq(professor.profno)).where(student.studno.eq(studno)).fetchOne();
	}

	public Tuple findStudentWithDeptNameAndProfNameByStudno(Integer studno) {
		QStudent student = QStudent.student;
		QDepartment department = QDepartment.department;
		QProfessor professor = QProfessor.professor;
		return jpaQueryFactory.select(student, professor.name, department.dname).from(student).join(department)
				.on(student.deptno1.eq(department.deptno)).leftJoin(professor).on(student.profno.eq(professor.deptno))
				.where(student.studno.eq(studno)).fetchOne();

	}

	public List<Student> findStudentListByDeptName(String deptName) {
		QDepartment department = QDepartment.department;
		QStudent student = QStudent.student;
		return jpaQueryFactory.selectFrom(student).join(department).on(student.deptno1.eq(department.deptno))
				.where(department.dname.eq(deptName)).fetch();

	}

	public List<Student> findStudentListByDeptNameAndGrade(String deptName, Integer grade) {
		QDepartment department = QDepartment.department;
		QStudent student = QStudent.student;
		return jpaQueryFactory.selectFrom(student).join(department).on(student.deptno1.eq(department.deptno))
				.where(department.dname.eq(deptName).and(student.grade.eq(grade))).fetch();

	}

	public List<Student> findStudentListByDeptno1OrDeptno2(Integer deptno) {
		QStudent student = QStudent.student;
		QDepartment department1 = new QDepartment("department1");
		QDepartment department2 = new QDepartment("department2");
//		return jpaQueryFactory.selectFrom(student).join(department1).on(student.deptno1.eq(department.deptno))
//				.leftJoin(department2).on(student.deptno2.eq(department.deptno))
//				.where(student.deptno1.eq(deptno).or(student.deptno2.eq(deptno))).fetch();
		return null;
	}

	public List<Student> getStudentByProfessorNo(Integer profno) throws Exception {
		QStudent student = QStudent.student;
		return jpaQueryFactory.selectFrom(student).where(student.profno.eq(profno)).fetch();
	}

	public Professor getProfessorByProfno(Integer profno) throws Exception {
		QProfessor professor = QProfessor.professor;
		return jpaQueryFactory.selectFrom(professor).where(professor.profno.eq(profno)).fetchOne();

	}

	public Department getDepartmentByDeptno(Integer deptno) throws Exception {
		QDepartment department = QDepartment.department;
		return jpaQueryFactory.selectFrom(department).where(department.deptno.eq(deptno)).fetchOne();
	}
}
