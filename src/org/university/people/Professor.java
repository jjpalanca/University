package org.university.people;

import org.university.hardware.*;
import org.university.software.*;

public class Professor extends Employee{
	private double salary;
	private Department department;
	
	public Professor() {
		salary = 0.00;
		department = new Department();
	}
	
	public void setDept(Department dept) {
		department = dept;
	}
	
	public Department getDept() {
		return department;
	}
	
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public double getSalary() {
		return salary;
	}
	
	@Override
	public double earns() {
		return salary/26;
	}

	@Override
	public void raise(double percent) {
		salary += (salary * percent / 100);		
	}
	@Override
	public void addCourse(CampusCourse cCourse) {
		if (detectConflict(cCourse) == false) {
			if (profExist(cCourse) == true) {
				System.out.println("The professor " + this.getName() +" cannot be assigned to this campus course because professor " + cCourse.getProfessor().getName() + " is already assigned to the course " + cCourse.getName() +".");
			}
			else {
				campusCourseList.add(cCourse);
				cCourse.setProfessor(this);
				for (int i = 0; i < cCourse.getSchedule().size(); i++) {
					tempSched.add(cCourse.getSchedule().get(i));
				}
			}
			
		}
	}
	
	public void addCourse(OnlineCourse oCourse) {
		if (profExist(oCourse) == true) {
			System.out.println("The professor cannot be assigned to this online course because professor " + oCourse.getProfessor().getName() + " is already assigned to the online course " + oCourse.getName() +".");
		}
		else {
			onlineCourseList.add(oCourse);
			oCourse.setProfessor(this);
		}
	}
	
	public boolean profExist(Course acourse) {
		if (acourse.getProfessor().getName().compareTo("unknown") != 0) {	// checks to see if acourse has already a prof
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void setDepartment(Department department) {
		this.department = department;
		
	}
	
}
