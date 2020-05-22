package org.university.people;

import java.util.ArrayList;
//import java.util.Collections;
import org.university.hardware.*;
import org.university.software.*;

public class Student extends Person{
	private Department department;
	private int campusUnits;
	private ArrayList<Integer> onlineUnits;
	private int currentUnits;
	private int unitsCompleted;
	private int reqCredits;
	private int tuition;
	
	public Student() {
		department = new Department();
		campusUnits = 0;
		onlineUnits = new ArrayList<Integer>();
		currentUnits = 0;
		unitsCompleted = 0;
		reqCredits = 0;
		tuition = 0;
	}
	
	public Department getDepartment() {
		return department;
	}
	
	public int getCampusUnit() {
		return campusUnits;
	}
	
	public void setDepartment(Department dept) {
		department = dept;
	}
	
	@Override
	public void addCourse(CampusCourse cCourse) {
		if (detectConflict(cCourse) == false) {
			if(cCourse.availableTo(this) == true) {
				campusUnits += cCourse.getCreditUnits();
				currentUnits += cCourse.getCreditUnits();
				campusCourseList.add(cCourse);
				cCourse.addStudent(this);
				for (int i = 0; i < cCourse.getSchedule().size(); i++) {
					tempSched.add(cCourse.getSchedule().get(i));
				}
			}
			else {
				System.out.println(this.getName() + " can't add Campus Course " + cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber() +
						" " + cCourse.getName() + ". Because this Campus course has enough student.");
			}
		}
	}
	
	@Override
	public void addCourse(OnlineCourse oCourse) {
		if (campusUnits >= 6) {
			onlineCourseList.add(oCourse);
			oCourse.addStudent(this);
			onlineUnits.add(oCourse.getCreditUnits());
			currentUnits += oCourse.getCreditUnits();
		}
		else {
			System.out.println("Student " + this.getName() + " has only " + campusUnits + " on campus credits enrolled. "
					+ "Should have at least 6 credits registered before registering online courses.");
			System.out.println(this.getName() + " can't add online Course " + oCourse.getDepartment().getDepartmentName() + oCourse.getCourseNumber() + 
					 " " + oCourse.getName() + ". Because this student doesn't have enough Campus course credit.");
		}
	}
	
	public void calcTuiton() {
		int campusFee = campusUnits * 300;
		int onlineFee = 0;
		for(Integer cred: onlineUnits) {
			if (cred == 3) {
				onlineFee += 2000;
			}
			else if(cred == 4) {
				onlineFee += 3000;
			}
		}
		tuition = campusFee + onlineFee;
	}	
	
	public int getTuitionFee() {
		calcTuiton();
		return tuition;
	}

	
	public void setCompletedUnits(int units) {
		this.unitsCompleted = units;
	}
	
	public int getCompletedUnits() {
		return unitsCompleted;
	}
	
	public void setRequiredCredits(int creds) {
		reqCredits = creds;
	}
	
	public int requiredToGraduate() {
		int temp = reqCredits - (currentUnits + unitsCompleted);
		return temp;
	}
	
	public void dropCourse(CampusCourse cCourse) {
		boolean found = false;
		for (int i = 0; i < getCampusCourseList().size(); i++) {
			if(getCampusCourseList().get(i).getName().compareTo(cCourse.getName()) == 0) {
				found = true;
			}
		}
		if(found == true) {
			if (onlineCourseList.size() > 0) {
				int temp = campusUnits - cCourse.getCreditUnits();
				if (temp >= 6) {
					campusUnits -= cCourse.getCreditUnits();
					currentUnits -= cCourse.getCreditUnits();
					campusCourseList.remove(cCourse);
					cCourse.removeStudent(this);
					for(int i = 0; i < tempSched.size(); i++) {
						for (int j = 0; j < cCourse.getSchedule().size(); j++) {
							if (tempSched.get(i).compareTo(cCourse.getSchedule().get(j)) == 0) {
								tempSched.remove(i);
								i -= 1;
								break;
							}
						}
					}
				}
				else {
					System.out.println(this.getName() + " can't drop this CampusCourse, because this student doesn't have enough campus course credit to hold the online course");
				}
			}
			else {
				campusUnits -= cCourse.getCreditUnits();
				currentUnits -= cCourse.getCreditUnits();
				campusCourseList.remove(cCourse);
				cCourse.removeStudent(this);
				for(int i = 0; i < tempSched.size(); i++) {
					for (int j = 0; j < cCourse.getSchedule().size(); j++) {
						if (tempSched.get(i).compareTo(cCourse.getSchedule().get(j)) == 0) {
							tempSched.remove(i);
							i -= 1;
							break;
						}
					}
				}
			}
		}
		else {
			System.out.println("The course " + cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber() + " could not be dropped because " + this.getName() + " is not enrolled in " 
					+ cCourse.getDepartment().getDepartmentName()  + cCourse.getCourseNumber() + ".");
		}
	}
	
	public void dropCourse(OnlineCourse oCourse) {
		boolean found = false;
		for (int i = 0; i < getOnlineCourseList().size(); i++) {
			if(getOnlineCourseList().get(i).getName().compareTo(oCourse.getName()) == 0) {
				found = true;
			}
		}
		if (found == true) {
			onlineCourseList.remove(oCourse);
			for(int i = 0; i < onlineUnits.size(); i++) {
				if (onlineUnits.get(i).compareTo(oCourse.getCreditUnits()) == 0) {
					onlineUnits.remove(i);
				}
			}
			currentUnits -= oCourse.getCreditUnits();
		}
		else {
			System.out.println("The course " + oCourse.getDepartment().getDepartmentName() + oCourse.getCourseNumber() + " could not be dropped because " + this.getName() + " is not enrolled in " 
					+ oCourse.getDepartment().getDepartmentName()  + oCourse.getCourseNumber() + ".");
		}
	}

	
}
