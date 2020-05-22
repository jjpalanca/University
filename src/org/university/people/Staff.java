package org.university.people;

import org.university.hardware.*;
import org.university.software.*;

public class Staff extends Employee{
	private Department department;
	private double payRate;
	private int hoursWorked;
	private int tuition;
	private int campusUnits;
	private int onlineUnits;
	private int currentUnits;
	private int unitsCompleted;
	private int reqCredits;
	
	public Staff() {
		setDepartment(new Department());
		setPayRate(0.00);
		setMonthlyHours(0);
		setCampusUnits(0);
		setOnlineUnits(0);
		setCurrentUnits(0);
		setUnitsCompleted(0);
		setReqCredits(0);
		tuition = 0;
	};

	@Override
	public double earns() {
		return hoursWorked * payRate;
	}

	@Override
	public void raise(double percent) {
		payRate += (payRate * percent / 100);
	}
	
	@Override
	public void addCourse(CampusCourse cCourse) {
		if (campusCourseList.size() == 1) {
			System.out.println(campusCourseList.get(0).getDepartment().getDepartmentName()+campusCourseList.get(0).getCourseNumber() +" is removed from "+
					this.getName() + "'s schedule(Staff can only take one class at a time). " + cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber() + 
					" has been added to " + this.getName() + "'s Schedule.");
			campusCourseList.get(0).removeStudent(this);
			campusCourseList.remove(0);
			tempSched.remove(0);
		}
		
		if (onlineCourseList.size() == 1) {
			System.out.println(onlineCourseList.get(0).getDepartment().getDepartmentName()+onlineCourseList.get(0).getCourseNumber() +" is removed from "+
					this.getName() + "'s schedule(Staff can only take one class at a time). " + cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber() + 
					" has been added to " + this.getName() + "'s Schedule.");
			onlineCourseList.get(0).removeStudent(this);
			onlineCourseList.remove(0);
		}
		
		campusCourseList.add(cCourse);
		cCourse.addStudent(this);
		for (int i = 0; i < cCourse.getSchedule().size(); i++) {
			tempSched.add(cCourse.getSchedule().get(i));
		}
		
		setCampusUnits(cCourse.getCreditUnits());
		setOnlineUnits(0);
		setCurrentUnits(campusUnits);
		calcTuitionFee();
	}
	
	@Override
	public void addCourse(OnlineCourse oCourse) {
		if (campusCourseList.size() == 1) {
			System.out.println(campusCourseList.get(0).getDepartment().getDepartmentName()+campusCourseList.get(0).getCourseNumber() +" is removed from "+
					this.getName() + "'s schedule(Staff can only take one class at a time). " + oCourse.getDepartment().getDepartmentName() + oCourse.getCourseNumber() + 
					" has been added to " + this.getName() + "'s Schedule.");
			campusCourseList.get(0).removeStudent(this);
			campusCourseList.remove(0);
			tempSched.remove(0);
		}
		
		if (onlineCourseList.size() == 1) {
			System.out.println(onlineCourseList.get(0).getDepartment().getDepartmentName()+onlineCourseList.get(0).getCourseNumber() +" is removed from "+
					this.getName() + "'s schedule(Staff can only take one class at a time). " + oCourse.getDepartment().getDepartmentName() + oCourse.getCourseNumber() + 
					" has been added to " + this.getName() + "'s Schedule.");
			onlineCourseList.get(0).removeStudent(this);
			onlineCourseList.remove(0);			
		}
		
		onlineCourseList.add(oCourse);
		oCourse.addStudent(this);
		setCampusUnits(0);
		setOnlineUnits(oCourse.getCreditUnits());
		setCurrentUnits(onlineUnits);
		calcTuitionFee();
	}

	public int getTuitionFee() {
		calcTuitionFee();
		return tuition;
	}

	public void calcTuitionFee() {
		if (campusUnits != 0 && onlineUnits == 0) {
			tuition = campusUnits * 300;
		}
		else if(campusUnits == 0 && onlineUnits != 0) {
			if (onlineUnits == 3) {
				tuition = 2000;
			}
			else if(onlineUnits == 4) {
				tuition = 3000;
			}
		}
		else {
			tuition = 0;
		}
			
	}
	
	public Department getDepartment() {
		return department;
	}
	
	@Override
	public void setDepartment(Department department) {
		this.department = department;
	}

	public double getPayRate() {
		return payRate;
	}

	public void setPayRate(double payRate) {
		this.payRate = payRate;
	}

	public int getHoursWorked() {
		return hoursWorked;
	}

	public void setMonthlyHours(int hrs) {
		hoursWorked = hrs;
	}

	public int getCampusUnits() {
		return campusUnits;
	}

	public void setCampusUnits(int campusUnits) {
		this.campusUnits = campusUnits;
	}

	public int getOnlineUnits() {
		return onlineUnits;
	}

	public void setOnlineUnits(int onlineUnits) {
		this.onlineUnits = onlineUnits;
	}

	public int getCurrentUnits() {
		return currentUnits;
	}

	public void setCurrentUnits(int currentUnits) {
		this.currentUnits = currentUnits;
	}

	public int getUnitsCompleted() {
		return unitsCompleted;
	}

	public void setUnitsCompleted(int unitsCompleted) {
		this.unitsCompleted = unitsCompleted;
	}
	
	public int requiredToGraduate() {
		int temp = reqCredits - (currentUnits + unitsCompleted);
		return temp;
	}

	public void setReqCredits(int reqCredits) {
		this.reqCredits = reqCredits;
	}

}
