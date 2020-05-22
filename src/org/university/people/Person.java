package org.university.people;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import org.university.hardware.Department;
import org.university.software.*;

public abstract class Person implements Serializable{
	protected String name;
	protected ArrayList<String> schedule;
	protected ArrayList<Integer> tempSched;
	protected ArrayList<CampusCourse> campusCourseList;
	protected ArrayList<OnlineCourse> onlineCourseList;
	public CampusCourse conflictCourse;
	protected ArrayList<String> conflictTime;

	public Person() {
		name = "unknown";
		tempSched = new ArrayList<Integer>();
		schedule = new ArrayList<String>();
		campusCourseList = new ArrayList<CampusCourse>();
		onlineCourseList = new ArrayList<OnlineCourse>();
	}
	
	public abstract void addCourse(CampusCourse cCourse);
	
	public abstract void addCourse(OnlineCourse oCourse);
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<CampusCourse> getCampusCourseList(){
		return campusCourseList;
	}
	
	public ArrayList<OnlineCourse> getOnlineCourseList(){
		return onlineCourseList;
	}
	
	public boolean detectConflict(CampusCourse acourse) {
		conflictTime = new ArrayList<String>();
		boolean found = false;
		for (CampusCourse c: campusCourseList) {
			for(Integer sched: c.getSchedule()) {
				for (Integer sched2: acourse.getSchedule()) {
					if (sched.compareTo(sched2) == 0) {
						conflictCourse = c;
						conflictTime.add(convertSched(sched));
						found = true;
					}
				}
			}
		}
		
		if(found == true) {
			for(String time: conflictTime) {
				System.out.println(acourse.getDepartment().getDepartmentName()+acourse.getCourseNumber() + " course cannot be added to " +
						this.getName() + "'s Schedule. " + acourse.getDepartment().getDepartmentName() + acourse.getCourseNumber() +
						" conflicts with " + conflictCourse.getDepartment().getDepartmentName() + conflictCourse.getCourseNumber() + 
						". Conflicting time slot is " + time + ".");
			}
		}
		
		return found;
	}
	
	public void printSchedule() {
		sortSched();
		for (int i = 0; i < schedule.size(); i++) {
			System.out.println(schedule.get(i));
		}
		if (onlineCourseList.size() != 0) {
			for(int i = 0; i < onlineCourseList.size(); i++) {
				System.out.println(onlineCourseList.get(i).getCourseNumber() + " " + onlineCourseList.get(i).getName());
			}
		}
	}
	
	public void sortSched() {
		Collections.sort(tempSched);
		schedule.clear();
		for (Integer sched: tempSched) {
			for(CampusCourse course: campusCourseList) {
				for(Integer sched2: course.getSchedule()) {
					if (sched.compareTo(sched2) == 0) {
						schedule.add(convertSched(sched) + " " + course.getDepartment().getDepartmentName() + course.getCourseNumber() + " " + course.getName());
					}
				}
			}
		}
		// removing duplicate sched
		int size = schedule.size();
		if(size!=0) {
			int half = size / 2; //2
			String temp = schedule.get(size/2);
			if(schedule.get(0).compareTo(temp)==0) {
				for (int i = size - 1; i >= half; i--) {
					schedule.remove(i);
				}
			}
		}
		
	}
	
	public String convertSched(int num) {
		String[] Week = {"Mon", "Tue", "Wed", "Thu", "Fri"};
		String[] Slot = {
				"8:00am to 9:15am",
				"9:30am to 10:45am",
				"11:00am to 12:15pm",
				"12:30pm to 1:45pm",
				"2:00pm to 3:15pm",
				"3:30pm to 4:45pm"
		};
		Integer num2 = num;
		String temp = num2.toString();
		String left = String.valueOf(temp.charAt(0));
		String right = String.valueOf(temp.charAt(2));
		int idx1 = Integer.parseInt(left);
		int idx2 = Integer.parseInt(right);
		String day = Week[idx1-1];
		String time = Slot[idx2-1];
		String result = day + " " + time;
		return result;
	}

	public abstract void setDepartment(Department department);
	
}
