package org.university.software;


import org.university.hardware.*;
import org.university.people.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

//import university.Classrom;
//import university.Classroom;

import java.util.ArrayList;

public class University implements Serializable{
	private String name;
	public ArrayList<Department> departmentList;
	public ArrayList<Classroom> classroomList;
	
	public University() {
		name = "University of Arizona";
		departmentList = new ArrayList<Department>();
		classroomList = new ArrayList<Classroom>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String aName) {
		name = aName;
	}
	
	public ArrayList<Department> getDeptList(){
		return departmentList;
	}
	
	public void printDepartmentList() {
		for (int i = 0; i < departmentList.size(); i++) {
			System.out.println(departmentList.get(i).getDepartmentName());
		}
	}
	
	public void printStudentList() {
		for (int i = 0; i < departmentList.size(); i++) {
			for (int j = 0; j < departmentList.get(i).getStudentList().size(); j++) {
				String studentName = departmentList.get(i).getStudentList().get(j).getName();
				System.out.println(studentName);
			}
		}
	}
	
	public void printProfessorList() {
		for(Department dept: departmentList) {
			for (Person p: dept.getProfessorList()) {
				System.out.println(p.getName());
			}
		}
	}
	
	public void printStaffList() {
		for(Department dept: departmentList) {
			for (Person sf: dept.getStaffList()) {
				System.out.println(sf.getName());
			}
		}
	}
	
	public void printClassroomList() {
		for(Classroom cr: classroomList) {
			System.out.println(cr.getRoomNumber());
		}
	}
	
	public void printCourseList() {
		for(Department dept: departmentList) {
			for (CampusCourse cc: dept.getCampusCourseList()) {
				System.out.println(cc.getDepartment().getDepartmentName()+cc.getCourseNumber()+" "+cc.getName());
			}
		}
		for(Department dept: departmentList) {
			for (OnlineCourse oc: dept.getOnlineCourseList()) {
				System.out.println(oc.getDepartment().getDepartmentName()+oc.getCourseNumber()+" "+oc.getName());
			}
		}
	}
	
	public static void saveData(University univ) {
		FileOutputStream fileOut = null;
		ObjectOutputStream objOut= null;

		try 
		{
			fileOut = new FileOutputStream( "C:\\Users\\jjpal\\eclipse-workspace\\University_Palanca\\root\\university.ser" );		//the Employee object makes its way to serial data in the file Employee.ser
			objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(univ);
			objOut.close();
			fileOut.close();
	     }	
		
		catch(IOException i)
	    {
			i.printStackTrace();
	    }	
	}
	
	public static University loadData() {
		FileInputStream fileIn = null;
		ObjectInputStream objIn = null;
		
		University unv = null;
		
		try {
			fileIn = new FileInputStream("C:\\Users\\jjpal\\eclipse-workspace\\University_Palanca\\root\\\\university.ser");
			objIn = new ObjectInputStream(fileIn);
			unv = (University) objIn.readObject();
			objIn.close();
			fileIn.close();
		}
		catch(IOException i) {
			i.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return unv;
	}
	
	public void printAll() {
		System.out.println("\nList of departments:");
		this.printDepartmentList();
		
		System.out.println("\nClassroom list:");
		this.printClassroomList();
		System.out.println("");
		
		for (int i = 0; i < this.departmentList.size(); i++) {
			System.out.println("The professor list for department " + this.departmentList.get(i).getDepartmentName());
			this.departmentList.get(i).printProfessorList();
//			for(Professor prof: departmentList.get(i).getProfessorList()) {
//				System.out.println(prof.getName());
//			}
			System.out.println("");
		}
		for (int i = 0; i < this.departmentList.size(); i++) {
			System.out.println("The course list for department " + this.departmentList.get(i).getDepartmentName());
			this.departmentList.get(i).printCourseList();
			System.out.println("");
		}
//		System.out.println("The schedule for classroom " + classroomList.get(0).getRoomNumber());
//		//printClassroomList();
//		classroomList.get(0).printSchedule();
//		System.out.println("");
		for(int i = 0; i < this.classroomList.size(); i++) {
			System.out.println("The schedule for classroom " + this.classroomList.get(i).getRoomNumber());
			//printClassroomList();
//			for (int j=0;j<classroomList.get(i).getCourseList().size();j++) {
//				System.out.println(classroomList.get(i).getCourseList().get(j).getName() + classroomList.get(i).getCourseList().get(j).getCourseNumber());
//			}
			//
			this.classroomList.get(i).printSchedule();
			System.out.println("");
		}
		for (Department dept: departmentList) {
			
			System.out.println("Department " + dept.getDepartmentName() + "\n");
			
			System.out.println("Printing Professor Schedules:\n");
			for(Professor prof: dept.getProfessorList()) {
				System.out.println("The Schedule for Prof. "+prof.getName() + ":");
				prof.printSchedule();
				System.out.println("");
			}
			
			System.out.println("Printing Student Schedules:\n");
			for(Student st: dept.getStudentList()) {
				System.out.println("The schedule for Student "+st.getName() + ":");
				st.printSchedule();
				System.out.println("");
			}
			
			System.out.println("Printing Staff Schedules:");
			if (dept.getStaffList().size() == 0) {
				System.out.println("\n");
			}
			else {
				for(Staff staff: dept.getStaffList()) {
					System.out.println("\nThe schedule for Employee "+staff.getName() + ":");
					staff.printSchedule();
					System.out.println("");
					System.out.println("Staff: " + staff.getName() + " earns " + staff.earns() + " this month\n");
				}
			}
			
			//System.out.println("\n");
			System.out.println("The rosters for courses offered by " + dept.getDepartmentName() + "\n");
			for(CampusCourse c: dept.getCampusCourseList()) {
				System.out.println("The roster for course "+ dept.getDepartmentName()+c.getCourseNumber());
				c.printStudentRoster();
				System.out.println("");
			}
		}
	}
}










