package org.university.software;

import java.util.ArrayList;
import java.util.Collections;

import org.university.hardware.*;
import org.university.people.*;

public class CampusCourse extends Course {
	private ArrayList<Integer> schedule;
	private Classroom classroom;
	
	public CampusCourse() {
		schedule = new ArrayList<Integer>();
		classroom = new Classroom();
	}
	
	@Override
	public boolean availableTo(Student aStudent) {
		if (numberOfStudents >= maxStudents) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public ArrayList<Integer> getSchedule(){
		return schedule;
	}
	
	public void setSchedule(int sched) {
		schedule.add(sched);
	}
	
	public void setRoomAssigned(Classroom aRoom) {
		ArrayList<CampusCourse> c = new ArrayList<CampusCourse>();
		c = aRoom.courses;
		Course conflictCourse = new CampusCourse();
		String conflictTime = new String();
		boolean found = false;
		for (CampusCourse course: c) {
			for (Integer sched: course.getSchedule()) {
				for (Integer sched2: this.getSchedule()) {
					if (sched.compareTo(sched2) == 0) {
						found = true;
						conflictCourse = course;
						conflictTime = convertSched(sched);
						break;
					}
				}
			}
		}
		if(found == false) {
			aRoom.addCourse(this);
			classroom = aRoom;
		}
		else {
			System.out.println(this.getDepartment().getDepartmentName() + this.getCourseNumber() + " conflicts with " +
					conflictCourse.getDepartment().getDepartmentName() + conflictCourse.getCourseNumber()+". Conflicting time slot "+
					conflictTime + ". " + this.getDepartment().getDepartmentName() + this.getCourseNumber() + " course cannot be added to "+
					aRoom.getRoomNumber() + "'s Schedule.");
		}
	}
	
	public void printSchedule() {
		Collections.sort(getSchedule());
		for (int i = 0; i < schedule.size(); i++) {
			System.out.println(convertSched(schedule.get(i)) + " " + classroom.getRoomNumber());
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

}
