package PE2;

import java.util.*;
/*
Author: Mehrshad Farahbakhsh
*/

public class CSD {

	ChairPerson onlyChairPerson; 
	Faculty Program; 
	List<Faculty> facultyList; 
	List<Student> studentList;
	List<UGrad> undergradStudents;
	List<Grad> gradStudents;
	List<ProgramDirector> programDirectors;
	int computerScienceCounter;
	int softwareEngineeringCounter;
	int digitalTechnologyCounter;

	public CSD(ChairPerson chair) {
		this.onlyChairPerson = chair;
		this.facultyList = new ArrayList<Faculty>();
		this.studentList = new ArrayList<Student>();
		this.undergradStudents = new ArrayList<UGrad>();
		this.gradStudents = new ArrayList<Grad>();
		this.programDirectors = new ArrayList<ProgramDirector>();
		this.computerScienceCounter = 0;
		this.softwareEngineeringCounter = 0;
		this.digitalTechnologyCounter = 0;	
	}

	public ChairPerson getChairPerson() {
		return this.onlyChairPerson;
	}
	
	/**
	 * This method hires a new faculty member
	 * @param faculty is the new faculty member
	 * @throws NoSpaceException is thrown when there is no space for faculty member to be hired
	 */

	public void HireFaculty(Faculty faculty) throws NoSpaceException {
		//check faculty doesn't exist in faculty list
		if (this.facultyList.contains(faculty)) {
			return;
		}
		//check there is a space for new faculty member
		if (facultyList.size() < 70) {
			//add new faculty member to the faculty list
			this.facultyList.add(faculty);
			if (faculty.getProgram() == "Computer Science") {
				//increase the number of computer science members
				this.computerScienceCounter++;
			}
			if (faculty.getProgram() == "Software Engineering") {
				//increase the number of software engineering members
				this.softwareEngineeringCounter++;
			}
			if (faculty.getProgram() == "Digital Technology") {
				//increase the number of digital technology members
				this.digitalTechnologyCounter++;
			}
		} else {
			//throw NoSpaceException because there is no space
			throw new NoSpaceException("Faculty List is full. It cannot be hired");
		}
	}

	/**
	 * This method retires a faculty 
	 * @param faculty is going to be retired
	 * @throws NoSpecialtyException is thrown when a faculty member is retiring 
	 * but no other faculty is available with the same specialty 
	 */
	public void RetireFaculty(Faculty faculty) throws NoSpecialtyException {
		boolean done = false;
		//check faculty exists in faculty list
		if (!this.facultyList.contains(faculty)) {
			return;
		}
		//check there is at least one faculty in faculty list
		if (facultyList.size() > 1) {
			//check there is at least one member of that program
			if (faculty.getProgram() == "Computer Science" && this.computerScienceCounter > 1) {
				this.facultyList.remove(faculty);
				done = true;
			}
			else if (faculty.getProgram() == "Software Engineering" && this.softwareEngineeringCounter > 1) {
				this.facultyList.remove(faculty);
				done = true;
			}
			else if (faculty.getProgram() == "Digital Technology" && this.digitalTechnologyCounter > 1) {
				this.facultyList.remove(faculty);
				done = true;
			}
			else {
				throw new NoSpecialtyException("No Specialty");
			}
			//if the retired process was successful, 
			//then assign the students of the retired faculty to the next available faculty member 
			if (done) {
				for (int i = 0; i < faculty.getTAs().size(); i++) {
					for (int j = 0; j < this.facultyList.size(); j++) {
						if(this.facultyList.get(j).getTAs().size() < 5) {
							this.facultyList.get(j).addTA(faculty.getTAs().get(i));
							break;
						}
					}
				}
				for (int i = 0; i < faculty.getAssignedStudentList().size(); i++) {
					for (int j = 0; j < this.facultyList.size(); j++) {
						if(this.facultyList.get(j).getAssignedStudentList().size() < 8) {
							this.facultyList.get(j).addAdvisingUgrads(faculty.getAssignedStudentList().get(i));
							break;
						}
					}
				}
			}
		} 
	}
	
	/**
	 * This method add a new program director to the program director list
	 * @param p is a new program director
	 * @throws NoSpaceException is thrown when there is no space for new program director to be added
	 */
	public void addProgramDirector(ProgramDirector p) throws NoSpaceException {
		//check it doesn't exist in program director list
		if (this.programDirectors.contains(p)) {
			return;
		}
		//check there is space for a new program director
		if (this.programDirectors.size() < 3) {
			this.programDirectors.add(p);
		} else {
			throw new NoSpaceException("No Space");
		}
	}
	
	/**
	 * 
	 * @return the faculty member list
	 */

	public List<Faculty> getFaculty() {
		return this.facultyList;
	}
	/**
	 * 
	 * @return the number of faculty members in list
	 */
	public int getNumOfFaculty() {
		return this.facultyList.size();
	}

	/**
	 * This method admits a new undergrad student and assign to a faculty
	 * @param s is a new undergrad student
	 * @throws NoSpaceException is thrown when there is no space for a new undergrad student
	 */
	public void AdmitStudent(UGrad s) throws NoSpaceException {
		//check there is space for a new undergrad student
		if (this.undergradStudents.size() < 500) {
			//check it doesn't exist in undergrad student list
			if (this.undergradStudents.contains(s)) {
				return;
			}
			//assigning and advising
			for (int i = 0; i < this.facultyList.size(); i++) {
				if (this.facultyList.get(i).addAdvisingUgrads(s)) {
					this.undergradStudents.add(s);
					s.setAdvisor(this.facultyList.get(i));
					return;
				}
			}
		} else {
			throw new NoSpaceException("No Space Available");
		}
	}
	
	/**
	 * This method hires TA
	 * @param s is a grad student who is going to be hired as TA
	 * @throws NoSpaceException is thrown when there is no space for a new TA
	 */

	public void HireTA(Grad s) throws NoSpaceException {
		//check the number of grad students
		if (this.gradStudents.size() < 150) {
			//check it doesn't exist
			if (this.gradStudents.contains(s)) {
				return;
			}
			//add grad student to the TA and set its advisor
			for (int i = 0; i < this.facultyList.size(); i++) {
				if (this.facultyList.get(i).addTA(s)) {
					this.gradStudents.add(s);
					s.setAdvisor(this.facultyList.get(i));
					return;
				}
			}
		} else {
			throw new NoSpaceException("No Space Available");
		}
	}
	

	/**
	 * This method clears undergrad student information from the university's record
	 * @param s is undergrad student who is going to be graduated
	 */
	public void AlumnusUGrad(UGrad s) {
		//go through each undergrad student list
		for (int i = this.undergradStudents.size() - 1; i >= 0; i--) {
			//find target student from the undergrad student list
			if (s == this.undergradStudents.get(i)) {
				//remove it from the list
				this.undergradStudents.remove(i);
				//go through each faculty 
				for (int j = 0; j < this.facultyList.size(); j++) {
					//get advising ugrad list from each faculty  
					List<UGrad> eachUgradList = this.facultyList.get(j).getAdvisingUgrads();
					//go through the list
					for (int k = eachUgradList.size() - 1; k >= 0; k--) {
						//find it from the list
						if (s == eachUgradList.get(k)) {
							//remove it from the list
							eachUgradList.remove(k);
						}
					}
				}
				return;
			}
		}
	}

	/**
	 * This method clears grad student information from the university's record
	 * @param s is grad student who is going to be graduated
	 * @throws NoTAException is thrown when it is not in TA list
	 */
	public void AlumnusGrad(Grad s) throws NoTAException {
		//go through each grad student list
		for (int i = this.gradStudents.size() - 1; i >= 0; i--) {
			//find target student from the grad student list
			if (s == this.gradStudents.get(i)) {
				//remove it from the list
				this.gradStudents.remove(i);
				//go through each faculty
				for (int j = 0; j < this.facultyList.size(); j++) {
					//get TA list from each faculty
					List<Grad> eachTAList = this.facultyList.get(j).getTAs();
					//go through the list
					for (int k = eachTAList.size() - 1; k >= 0; k--) {
						//find it from the list
						if (s == eachTAList.get(k)) {
							//remove it from the list
							eachTAList.remove(k);
						}
						if (eachTAList.size() == 0) {
							// if it is not in TA list, throw exception
							throw new NoTAException("No TA Available");
						}
					}
				}
				return;
			}
		}
	}
	
	/**
	 * This method extracts all the grad students information stored in the department record as a list of grad students
	 * @return all the grad students information stored in the department record as a list of grad student
	 */
	public List<Grad> ExtractAllGradDetails() {
		List<Grad> sortedList = new ArrayList<Grad>(this.gradStudents);
		Collections.sort(sortedList, new Student());
		return sortedList;
	}
	/**
	 * This method extracts all the undergrad students information stored in 
	 * the university student's record as a sorted list of students according to the student's full name
	 * @return all the undergrad students information stored in 
	 * the university student's record as a sorted list of students according to the student's full name
	 */
	public List<UGrad> ExtractAllUGradDetails() {
		List<UGrad> sortedList = new ArrayList<UGrad>(this.undergradStudents);
		Collections.sort(sortedList, new SortByName());
		return sortedList;
	}
	/**
	 * This method extracts all the information of faculty members stored in 
	 * the university faculty records as a sorted list of faculty to the faculty's full name
	 * @return all the information of faculty members stored in 
	 * the university faculty records as a sorted list of faculty to the faculty's full name
	 */
	public List<Faculty> ExtractAllFacultyDetails() {
		List<Faculty> sortedList = new ArrayList<Faculty>(this.facultyList);
		Collections.sort(sortedList, new SortByName());
		return sortedList;

	}
	/**
	 * This method extracts all the faculty information belonging to a particular program 
	 * as a sorted list of faculty members according to the faculty's full name
	 * @param faculty is a faculty that we want to get information from it
	 * @return the faculty information belonging to a particular program 
	 * as a sorted list of faculty members according to the faculty's full name
	 */
	public List<Faculty> ExtractFacultyDetails(String faculty) {
		List<Faculty> sortedList = new ArrayList<Faculty>();
		for (int i = 0; i < this.facultyList.size(); i++) {
			//go through each faculty in faculty member list
			if (faculty == this.facultyList.get(i).program) {
				//find the particular faculty (param) and then add it to sortedList variable
				sortedList.add(this.facultyList.get(i));
			}
		}
		Collections.sort(sortedList, new SortByName());
		return sortedList;
	}
	/**
	 * This method extracts all the students information that are advisees of a particular faculty 
	 * as a sorted list of students according to the student's full name
	 * @param faculty is a faculty that we want to get information from it
	 * @return all the students information that are advisees of a particular faculty 
	 * as a sorted list of students according to the student's full name
	 */
	public List<UGrad> ExtractAdviseesDetails(Faculty faculty) {
		List<UGrad> sortedList = new ArrayList<UGrad>(faculty.getAdvisingUgrads());
		Collections.sort(sortedList, new SortByName());
		return sortedList;
	}
	/**
	 * This method extracts all the grad student information assigned to a particular faculty as a list of TA
	 * @param faculty is a faculty that we want to get information from it
	 * @return all the grad student information assigned to a particular faculty as a list of TA
	 */
	public List<Grad> ExtractTAsDetails(Faculty faculty) {
		List<Grad> sortedList = new ArrayList<Grad>(faculty.getTAs());
		Collections.sort(sortedList, new SortByName());
		return sortedList;
	}
	/**
	 * @return the number of undergrad students
	 */
	public Integer getNumOfUGradStudents() {
		return this.undergradStudents.size();
	}
	/**
	 * @return the number of grad students
	 */
	public Integer getNumOfGradStudents() {
		return this.gradStudents.size();
	}

}

class SortByName implements Comparator<Person> {
	@Override
	/**
	 * This method override the compare method to get full name (first name , last name) from 2 person and compare it
	 */
	public int compare(Person a, Person b) {
		String str1 = a.firstName + ", " + a.lastName;
		String str2 = b.firstName + ", " + b.lastName;
		return str1.compareTo(str2);
	}
}

class Person {
	public String firstName;
	public String lastName;
	public int age;
	public String address;
	public String gender;
	public Person() {
		this.firstName = "";
		this.lastName = "";
		this.age = 0;
		this.gender = "";
		this.address = "";
	}
	public Person(String firstName, String lastName, int age, String gender, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.address = address;
	}
}

class Academics extends Person {
	static int employeeId = 99;
	double salary;
	public Academics() {
		super();
	}
	public Academics(String firstName, String lastName, int age, String gender, String address) {
		super(firstName, lastName, age, gender, address);
		Academics.employeeId++;
	}
}

class Administrator extends Academics {
	public Administrator() {
		super();
	}
	public Administrator(String firstName, String lastName, int age, String gender, String address) {
		super(firstName, lastName, age, gender, address);
	}
}

class Faculty extends Academics implements Comparable {
	String program;
	List<UGrad> assignedStudentList; //list for assigned student
	List<Grad> TAs; //list for TAs
	int employeeId;
	public Faculty(String firstName, String lastName, int age, String gender, String address) {
		super(firstName, lastName, age, gender, address);
		employeeId = Academics.employeeId;
		assignedStudentList = new ArrayList<UGrad>();
		TAs = new ArrayList<Grad>();
	}
	public List<UGrad> getAdvisingUgrads() {
		return this.assignedStudentList;
	}
	public int getNumOfAdvisingUGrads() {
		return this.assignedStudentList.size();
	}
	public List<UGrad> getAssignedStudentList() {
		return assignedStudentList;
	}
	public List<Grad> getTAs() {
		return this.TAs;
	}
	public int getNumOfTAs() {
		return this.TAs.size();
	}
	/**
	 * This method add undergrad student to the advising undergrad student list
	 * @param s is a new undergrad student
	 * @return true if it is added, otherwise returns false
	 */
	public boolean addAdvisingUgrads(UGrad s) {
		//check it doesn't exist in assigned student list and its availability for a new undergrad student
		if (!this.assignedStudentList.contains(s) && this.assignedStudentList.size() < 8) {
			this.assignedStudentList.add(s);
			return true;
		}
		return false;
	}
	/**
	 * This method add grad student to the TA list
	 * @param s is a new grad student
	 * @return true if it is added, otherwise returns false
	 */
	public boolean addTA(Grad s) {
		//check it doesn't exist in TA list and its availability for a new grad student
		if (!this.TAs.contains(s) && this.TAs.size() < 5) {
			this.TAs.add(s);
			return true;
		}
		return false;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public Integer getEmployeeID() {
		return this.employeeId;
	}
	public String getProgram() {
		return program;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public String toString() {
		return "Faculty " + this.program + "[[" + this.employeeId + ", " + this.salary + "[" + this.firstName + ", "
				+ this.lastName + ", " + this.age + ", " + this.gender + ", " + this.address + "]]]";
	}
	@Override
	public int compareTo(Object o) {
		return 0;
	}
}

class ProgramDirector extends Administrator {
	List<Faculty> assignedStudentList; //list for assigned student 
	String program; //name of program
	public ProgramDirector() {
		super();
	}
	public ProgramDirector(String firstName, String lastName, int age, String gender, String address) {
		super(firstName, lastName, age, gender, address);
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getProgram() {
		return this.program;
	}
	public void setAssignedStudentList(List<Faculty> assignedStudentList) {
		this.assignedStudentList = assignedStudentList;
	}
	public List<Faculty> getAssignedStudentList() {
		return assignedStudentList;
	}
}

class ChairPerson extends Administrator {
	int employeeId;
	public ChairPerson() {
		super();
	}
	public ChairPerson(String firstName, String lastName, int age, String gender, String address) {
		super(firstName, lastName, age, gender, address);
		employeeId = Academics.employeeId;
	}
	public Integer getEmployeeID() {
		return this.employeeId;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public double getSalary() {
		return this.salary;
	}
	public String toString() {
		return "Chair Person [[[" + this.employeeId + ", " + this.salary + "[" + this.firstName + ", " + this.lastName + ", "
				+ this.age + ", " + this.gender + ", " + this.address + "]]]]";
	}
}

class Student extends Person implements Comparator<Person> {
	static int studentId = 999;
	Faculty advisor;
	public int getStudentId() {
		return studentId;
	}
	public Student() {
		super();
	}
	public Student(String firstName, String lastName, int age, String gender, String address) {
		super(firstName, lastName, age, gender, address);
		Student.studentId++;
	}
	public void setAdvisor(Faculty f) {
		this.advisor = f;
	}
	public Faculty getAdvisor() {
		return this.advisor;
	}
	@Override
	/*
	 * This method override compare method and it gets 2 person and compare their first name
	 */
	public int compare(Person a, Person b) {
		return a.firstName.compareTo(b.firstName);
	}
}

class UGrad extends Student {
	int studentId;
	public UGrad(String firstName, String lastName, int age, String gender, String address) {
		super(firstName, lastName, age, gender, address);
		studentId = Student.studentId;
	}
	public int getStudentId() {
		return this.studentId;
	}
	public String toString() {
		return "Undergraduate [" + this.studentId + "[[" + this.firstName + ", " + this.lastName + ", " + this.age + ", "
				+ this.gender + ", " + this.address + "]]]";
	}
}

class Grad extends Student {
	public int studentId;
	public Grad(String firstName, String lastName, int age, String gender, String address) {
		super(firstName, lastName, age, gender, address);
		studentId = Student.studentId;
	}
	public int getStudentId() {
		return this.studentId;
	}
	public String toString() {
		return "Graduate [" + this.studentId + "[[" + this.firstName + ", " + this.lastName + ", " + this.age + ", "
				+ this.gender + ", " + this.address + "]]]";
	}
}
class NoSpaceException extends Exception {
	public NoSpaceException() {
		super();
	}
	public NoSpaceException(String message) {
		super(message);
	}
}
class NoTAException extends Exception {
	public NoTAException() {
		super();
	}
	public NoTAException(String message) {
		super(message);
	}
}
class NoSpecialtyException extends Exception {
	public NoSpecialtyException() {
		super();
	}
	public NoSpecialtyException(String message) {
		super(message);
	}
}