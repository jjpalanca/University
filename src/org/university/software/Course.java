package org.university.software;

import org.university.hardware.*;
import org.university.people.*;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Course implements Serializable{
	private ArrayList<Person> studentRoster;
	protected int numberOfStudents;
	protected int maxStudents;
	private int courseNumber;
	private String name;
	private Department department;
	private int credits;
	private Professor professor;
	
	public Course() {
		studentRoster = new ArrayList<Person>();
		numberOfStudents = 0;
		maxStudents = 0;
		courseNumber = 1;
		name = "unknown";
		department = new Department();
		credits = 0;
		professor = new Professor();
	}
	
	public void setMaxCourseLimit(int limit) {
		maxStudents = limit;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String aName) {
		name = aName;
	}
	
	public ArrayList<Person> getStudentRoster(){
		return studentRoster;
	}
	
	public void addStudent(Person person) {
		studentRoster.add(person);
		numberOfStudents++;
	}
	
	public void addStudentToRoster(Person person) {
		studentRoster.add(person);
		numberOfStudents++;
	}
	
	public void removeStudent(Person person) {
		studentRoster.remove(person);
		numberOfStudents--;
	}
	
	public void setProfessor(Professor prof) {
		professor = prof;
	}
	
	public Professor getProfessor() {
		return professor;
	}
	
	public int getCourseNumber() {
		return courseNumber;
	}
	
	public void setCourseNumber(int number) {
		courseNumber = number;
	}
	
	public Department getDepartment() {
		return department;
	}
	
	public void setDepartment(Department dept) {
		department = dept;
	}
	
	public abstract boolean availableTo(Student aStudent);

	public int getCreditUnits() {
		return credits;
	}

	public void setCreditUnits(int credits) {
		this.credits = credits;
	}

	public void printStudentRoster() {
		for(Person st: studentRoster) {
			System.out.println(st.getName());
		}
	}
	
}
