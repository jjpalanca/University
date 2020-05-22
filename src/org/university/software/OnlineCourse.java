package org.university.software;

import org.university.people.Student;

public class OnlineCourse extends Course{

	@Override
	public boolean availableTo(Student aStudent) {
		if (numberOfStudents >= maxStudents) {
			return false;
		}
		else {
			return true;
		}
	}

}
