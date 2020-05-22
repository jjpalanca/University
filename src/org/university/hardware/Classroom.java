package org.university.hardware;

import org.university.software.*;

import java.io.Serializable;
//import org.university.people.*;
import java.util.ArrayList;
import java.util.Collections;

public class Classroom implements Serializable{
	private String roomNumber;
	public ArrayList<CampusCourse> courses;
	private ArrayList<Integer> tempSched;
	private ArrayList<String> schedule;
	
	public Classroom() {
		roomNumber = "unknown";
		courses = new ArrayList<CampusCourse>();
		tempSched = new ArrayList<Integer>();
		schedule = new ArrayList<String>();
	}
	
	public void setRoomNumber(String roomNum) {
		roomNumber = roomNum;
	}
	
	public String getRoomNumber() {
		return roomNumber;
	}
	
	public void addCourse(CampusCourse acourse) {
		courses.add(acourse);
		boolean found = false;
		for(Integer sched: acourse.getSchedule()) {
			for(int t: tempSched) {
				if(sched.compareTo(t)==0) {
					found =  true;
					break;				}
			}
			if(found==false) {
				tempSched.add(sched);
			}
//			tempSched.add(sched);
			//System.out.println(sched);
		}
	}
	
	public ArrayList<CampusCourse> getCourseList(){
		return courses;
	}
	
	public void printSchedule() {
		sortSched();
		for (String sched: schedule) {
			System.out.println(sched);
		}
//		int size = schedule.size();
//		String temp = schedule.get(size/2);
//		//System.out.println(schedule.size());
//		if(schedule.get(0).compareTo(temp)!=0) {
//			for (String sched: schedule) {
//				System.out.println(sched);
//			}
//		}
//		else {
//			for(int i = 0; i < size/2; i ++) {
//				System.out.println(schedule.get(i));
//			}
//		}
		
	}
	
	public void sortSched() {
		Collections.sort(tempSched);
//		for(Integer s: tempSched) {
//			System.out.println(s);
//		}
		for (Integer sched: tempSched) {
			for(CampusCourse course: courses) {
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
}
