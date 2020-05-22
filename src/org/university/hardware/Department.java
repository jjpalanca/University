package org.university.hardware;

import org.university.software.*;
import org.university.people.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Department implements Serializable{
	private String name;
	private ArrayList<Course> offeredCourses;
	private ArrayList<CampusCourse> cCourses;
	private ArrayList<OnlineCourse> oCourses;
	private ArrayList<Student> studentsEnrolled;
	private ArrayList<Professor> professors;
	private ArrayList<Staff> staff;
	public Department() {
		name = "unknown";
		offeredCourses = new ArrayList<Course>();
		studentsEnrolled = new ArrayList<Student>();
		professors = new ArrayList<Professor>();
		cCourses = new ArrayList<CampusCourse>();
		oCourses = new ArrayList<OnlineCourse>();
		staff = new ArrayList<Staff>();
	}
	
	public String getDepartmentName() {
		return name;
	}
	
	public void setDepartmentName(String aName) {
		name = aName;
	}
	
	public ArrayList<Student> getStudentList(){
		return studentsEnrolled;
	}
	
	public void addStudent(Student student) {
		studentsEnrolled.add(student);
		student.setDepartment(this);
	}
	
	public ArrayList<Staff> getStaffList(){
		return staff;
	}
	
	public void addStaff(Staff newStaff) {
		staff.add(newStaff);
		newStaff.setDepartment(this);
	}
	
	public ArrayList<Course> getCourseList(){
		return offeredCourses;
	}
	
	public ArrayList<CampusCourse> getCampusCourseList(){
		return cCourses;
	}
	
	public ArrayList<OnlineCourse> getOnlineCourseList(){
		return oCourses;
	}
	
	public void addCourse(CampusCourse c_Course) {
		offeredCourses.add(c_Course);
		cCourses.add(c_Course);
		c_Course.setDepartment(this);
	}

	public void addCourse(OnlineCourse o_Course) {
		offeredCourses.add(o_Course);
		oCourses.add(o_Course);
		o_Course.setDepartment(this);
	}
	
	public void addProfessor(Professor prof) {
		professors.add(prof);
		prof.setDept(this);
	}
	
	public ArrayList<Professor> getProfessorList(){
		return professors;
	}
	
	public void printStudentList() {
		for (Student student: studentsEnrolled) {
			System.out.println(student.getName());
		}
	}
	
	public void printProfessorList() {
		for (Professor prof: professors) {
			System.out.println(prof.getName());
		}
	}
	
	public void printCourseList() {
		for (CampusCourse cc: cCourses) {
			System.out.println(cc.getDepartment().getDepartmentName()+cc.getCourseNumber() + " " + cc.getName());
		}
		for (OnlineCourse oc: oCourses) {
			System.out.println(oc.getDepartment().getDepartmentName()+oc.getCourseNumber() + " " + oc.getName());
		}
	}
	
	public void printStaffList() {
		for(Staff sf: staff) {
			System.out.println(sf.getName());
		}
	}
	
}
