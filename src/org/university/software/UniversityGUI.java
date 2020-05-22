package org.university.software;

import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.*;

import org.university.hardware.Department;
import org.university.people.Student;

public class UniversityGUI extends JFrame{
	private University univ1;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu studentMenu;
	private JMenu adminMenu;
	
	// File submenus
	private JMenuItem fileSaveData;
	private JMenuItem fileLoadData;
	private JMenuItem fileExit;
	
	// Student submenus
	private JMenuItem addCourse;
	private JMenuItem dropCourse;
	private JMenuItem printSchedule;
	
	// Admin submenu
	private JMenuItem printAllInfo;
	
	public UniversityGUI(String windowTitle, University univ) {
		super(windowTitle);
		
		// univ.printAll();
		univ1 = univ;
		
		setSize(500,400);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		add(new JLabel("<HTML><center>Welcome to the University." +
				"<BR>Choose an action from the above menus.</center></HTML>"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildGUI();
		setVisible(true);
	}
	
	
	public void buildGUI() {
		menuBar = new JMenuBar();
		
		// File, Student, Admin Menu
		fileMenu = new JMenu("File");
		studentMenu = new JMenu("Student");
		adminMenu = new JMenu("Administrators");
		
		///////////// FILE ///////////////////
		// submenu
		fileSaveData = new JMenuItem("Save");
		fileLoadData = new JMenuItem("Load");
		fileExit = new JMenuItem("Exit");
		// listener
		fileSaveData.addActionListener(new MenuListener());
		fileLoadData.addActionListener(new MenuListener());
		fileExit.addActionListener(new MenuListener());
		// add submenu
		fileMenu.add(fileSaveData);
		fileMenu.add(fileLoadData);
		fileMenu.add(fileExit);
		menuBar.add(fileMenu);
		
		////////////// STUDENT ////////////////////
		// submenu
		addCourse = new JMenuItem("Add Course");
		dropCourse = new JMenuItem("Drop Course");
		printSchedule = new JMenuItem("Print Schedule");
		// listener
		addCourse.addActionListener(new MenuListener());
		dropCourse.addActionListener(new MenuListener());
		printSchedule.addActionListener(new MenuListener());
		// add submenu
		studentMenu.add(addCourse);
		studentMenu.add(dropCourse);
		studentMenu.add(printSchedule);
		menuBar.add(studentMenu);
		
		///////////////// ADMIN //////////////////
		// submenu
		printAllInfo = new JMenuItem("Print All Info");
		// listener
		printAllInfo.addActionListener(new MenuListener());
		// add submenu
		adminMenu.add(printAllInfo);
		menuBar.add(adminMenu);
		
		setJMenuBar(menuBar);
	}
	
	private class MenuListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JMenuItem source = (JMenuItem)(e.getSource());
			if(source.equals(fileSaveData)) {
				if(univ1 != null) {
					University.saveData(univ1);
				}
			}
			else if(source.equals(fileLoadData)) {
				univ1 = University.loadData();
			}
			else if(source.equals(fileExit)) {
				System.exit(0);
			}
			else if(source.equals(addCourse)) {
				handleStudentAddCourse();
			}
			else if(source.equals(dropCourse)) {
				handleStudentDropCourse();
			}
			else if(source.equals(printSchedule)) {
				 handlePrintStudentSchedule();
			}
			else if(source.equals(printAllInfo)) {
				handleAdminPrint();
			}
		}
		
		private void handleStudentAddCourse() {
			String studentNameInput, deptNameInput;
			int courseNumInput;
			Student student;
			
			JTextField studentName = new JTextField();
			JTextField departmentName = new JTextField();
			JTextField courseNumber = new JTextField();
				
			
			Object[]fields = {
				"Student Name:", studentName,
				"Department:", departmentName,
				"Course #:", courseNumber
				
			};
			
			JOptionPane.showConfirmDialog(null, fields, "Add Course", JOptionPane.OK_CANCEL_OPTION);
			
			boolean flag = false;
			boolean conflict = false;
			boolean available = false;
			CampusCourse cCourse = null;
			OnlineCourse oCourse = null;
			String cname = "";
			studentNameInput = studentName.getText();
			deptNameInput = departmentName.getText();
			courseNumInput = Integer.parseInt(courseNumber.getText());
			
			student = getStudentObj(studentNameInput);
			
			// if student doesnt exist
			if(student == null) {
				JOptionPane.showMessageDialog(null, "Student \"" + studentNameInput + "\" doesn't exist", "Error adding student to course", JOptionPane.OK_OPTION);
			}
			// if student exist
			else {
				// if department doesnt exist
				if (getDeptObj(deptNameInput) == false) {
					JOptionPane.showMessageDialog(null, "Department \"" + deptNameInput + "\" doesn't exist", "Error adding student to course", JOptionPane.OK_OPTION);
				}
				// if department exist
				else {
					if(isCCourse(courseNumInput)) {
						cCourse = getCCourseObj(deptNameInput, courseNumInput);
						conflict = student.detectConflict(cCourse);
						available = cCourse.availableTo(student);
						flag = true;
					}
					else if(isOCourse(courseNumInput)) {
						oCourse = getOCourseObj(deptNameInput, courseNumInput);
						flag = false;
					}
					// if cCourse and oCourse doesnt exist
					if ((cCourse == null && oCourse == null)) {
						JOptionPane.showMessageDialog(null, "Course \"" + deptNameInput + courseNumInput + "\" doesn't exist", "Error adding student to course", JOptionPane.OK_OPTION);
					}
					// if course exist
					else if((cCourse != null || oCourse != null)) {
						
						CustomPrintStream printStream = new CustomPrintStream(); 
						PrintStream standard = System.out;
				        System.setOut(printStream); 
						if(flag) {
							student.addCourse(cCourse);
							cname = cCourse.getName();
							if(!conflict && available) {
								JOptionPane.showMessageDialog(null, "Success you have added "+cname, "Success", JOptionPane.PLAIN_MESSAGE);
							}
							
						}
						else {
							student.addCourse(oCourse);
							cname = oCourse.getName();
							if(student.getCampusUnit()>=6) {
								JOptionPane.showMessageDialog(null, "Success you have added "+cname, "Success", JOptionPane.PLAIN_MESSAGE);
							}
							
						}
						
				        System.setOut(standard);
					}
				}
			}
				
		}
		
		private void handleStudentDropCourse() {
			String studentNameInput, deptNameInput;
			int courseNumInput;
			Student student;
			
			JTextField studentName = new JTextField();
			JTextField departmentName = new JTextField();
			JTextField courseNumber = new JTextField();
				
			
			Object[]fields = {
				"Student Name:", studentName,
				"Department:", departmentName,
				"Course #:", courseNumber
				
			};
			
			JOptionPane.showConfirmDialog(null, fields, "Drop Course", JOptionPane.OK_CANCEL_OPTION);
			
			boolean flag = false;
			CampusCourse cCourse = null;
			OnlineCourse oCourse = null;
			studentNameInput = studentName.getText();
			deptNameInput = departmentName.getText();
			courseNumInput = Integer.parseInt(courseNumber.getText());
			
			student = getStudentObj(studentNameInput);
			
			// if student doesnt exist
			if(student == null) {
				JOptionPane.showMessageDialog(null, "Student \"" + studentNameInput + "\" doesn't exist", "Error adding student to course", JOptionPane.OK_OPTION);
			}
			// student exist
			else {
				// if department doesnt exist
				if (getDeptObj(deptNameInput) == false) {
					JOptionPane.showMessageDialog(null, "Department \"" + deptNameInput + "\" doesn't exist", "Error adding student to course", JOptionPane.OK_OPTION);
				}
				// if department exist
				else {
					if(isCCourse(courseNumInput)) {
						cCourse = getCCourseObj(deptNameInput, courseNumInput);
						flag = true;
					}
					else if(isOCourse(courseNumInput)) {
						oCourse = getOCourseObj(deptNameInput, courseNumInput);
						flag = false;
					}
					// if cCourse and oCourse doesnt exist
					if ((cCourse == null && oCourse == null)) {
						JOptionPane.showMessageDialog(null, "Course \"" + deptNameInput + courseNumInput + "\" doesn't exist", "Error adding student to course", JOptionPane.OK_OPTION);
					}
					// if course exist
					if((cCourse != null || oCourse != null)) {
						
						CustomPrintStream printStream = new CustomPrintStream(); 
						PrintStream standard = System.out;
				        System.setOut(printStream); 
						if(flag) {
							student.dropCourse(cCourse);			
						}
						else {
							student.dropCourse(oCourse);			
						}
						
				        System.setOut(standard);
					}
				}
			}

		}
		
		private void handlePrintStudentSchedule() {
			String studentName;
			
			studentName = JOptionPane.showInputDialog(null, "Student Name: ", "Print Student Schedule", JOptionPane.QUESTION_MESSAGE);
			if(studentName != null)
			{
				//If you put nothing but click okay
				if(studentName.trim().equals("")) {
					JOptionPane.showMessageDialog(null, 
												"Please enter correct Student name", 
												"Error Student doesn't exist", 
												JOptionPane.PLAIN_MESSAGE);
				}
				else {
					Student student = getStudentObj(studentName);
					
					if(student == null) {
						JOptionPane.showMessageDialog(null,
													"Student  \""+studentName+"\" doesn't exist.",
													"Error ",
													JOptionPane.PLAIN_MESSAGE);
					}
					//If we found the student, print
					else {
						JTextArea textArea = new JTextArea(20, 50); 
						textArea.setEditable(false);
						
						// scroll bar
						JScrollPane scrollBar = new JScrollPane(textArea);
						//Always vertical
						scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
						
						// PrintStream will be used as our new "out" for System.out.print and println statements
						PrintStream stream = new PrintStream(new WindowOutputStream(textArea));
						PrintStream standard = System.out; 
						System.setOut(stream); 
						
						student.printSchedule();
						
						JOptionPane.showMessageDialog(null, scrollBar, "Student " + studentName + "'s Schedule", JOptionPane.PLAIN_MESSAGE);
						System.setOut(standard); 
						
					}
				}
			}
		}
		
		private void handleAdminPrint() {
			
			JTextArea textArea = new JTextArea(20, 50); 
			textArea.setEditable(false);
			
			// scroll bar
			JScrollPane scrollBar = new JScrollPane(textArea);
			// vertical scroll
			scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			
			
			PrintStream stream = new PrintStream(new WindowOutputStream(textArea));
			PrintStream standard = System.out; 
			System.setOut(stream); //Set the new "out"
			
			// calls the University's printAll() function
			univ1.printAll();
			
			JOptionPane.showMessageDialog(null, scrollBar, "Printing University Info", JOptionPane.PLAIN_MESSAGE);
			System.setOut(standard);
		}
	}
	
	private Student getStudentObj(String name)
	{		
		for (Department d: univ1.getDeptList()) {
			for (Student s: d.getStudentList()) {
				if ((s.getName()).equals(name)) {
					return s;
				}
			}
		}
		return null;
    }
	
	private CampusCourse getCCourseObj(String adept, int cNum) {
		for (Department d: univ1.getDeptList()) {
			if (d.getDepartmentName().compareTo(adept)==0) {
				for (CampusCourse cc: d.getCampusCourseList()) {
					if (cc.getCourseNumber() == cNum) {
						return cc;
					}
				}
			}
		}
		return null;
	}
	
	private OnlineCourse getOCourseObj(String adept, int cNum) {
		for (Department d: univ1.getDeptList()) {
			if (d.getDepartmentName().compareTo(adept)==0) {
				for (OnlineCourse oc: d.getOnlineCourseList()) {
					if (oc.getCourseNumber() == cNum) {
						return oc;
					}
				}
			}
		}
		return null;
	}
	
	private boolean isCCourse(int num) {
		for (Department d: univ1.getDeptList()) {
			for(CampusCourse cc: d.getCampusCourseList()) {
				if(cc.getCourseNumber() == num) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isOCourse(int num) {
		for (Department d: univ1.getDeptList()) {
			for(OnlineCourse oc: d.getOnlineCourseList()) {
				if(oc.getCourseNumber() == num) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean getDeptObj(String adept) {
		for (Department d: univ1.getDeptList()) {
			if(d.getDepartmentName().compareTo(adept)==0) {
				return true;
			}
		}
		return false;
	}
	
	private class WindowOutputStream extends OutputStream{
		private JTextArea text;
		public WindowOutputStream(JTextArea t) {
			text = t;
		}
		
		public void write(int a) throws IOException{
			text.append(String.valueOf((char)a));
			text.setCaretPosition(text.getDocument().getLength());
		}
	}
	
	public class CustomPrintStream extends PrintStream {  
	    public CustomPrintStream() {  
	        super(new ByteArrayOutputStream());  
	    }  

	  
	    public void println(String msg) {  
	        JOptionPane.showMessageDialog(null, msg);  
	    }  
	}  
	
}
