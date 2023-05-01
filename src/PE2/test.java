package PE2;

import PE2.CSD.Person;
import PE2.CSD.Student;

public class test {

	public static void main(String[] args) {
		abstract class Person {
			String firstName;
			String lastName;
			int age;
			String gender;
			String address;
		}
		
		abstract class Student extends Person {
			static int stdId = 1000;
		}
		
		class Grad extends Student {
			public Grad() {
				this.firstName = "";
				this.lastName = "";
				this.age = 0;
				this.gender = "";
				this.address = "";
			}
			
			public Grad(String firstName, String lastName, int age, String gender, String address) {
				this.firstName = firstName;
				this.lastName = lastName;
				this.age = age;
				this.gender = gender;
				this.address = address;
				stdId++;
			}
			
			@Override
			public String toString() {
				return "Graduate ["+stdId+"["+"["+this.firstName+", "+this.lastName+", "+this.age+", "+this.gender+", "+this.address+"]]]";
			}}
			Grad s = new Grad("Rebert", "Jack", 59, "Male", "Birchmount Road");
			System.out.println(s.toString());
			Grad d = new Grad("Rebert", "Jack", 59, "Male", "Birchmount Road");
			System.out.println(d.toString());
			Grad f = new Grad("Rebert", "Jack", 59, "Male", "Birchmount Road");
			System.out.println(f.toString());
//		String test = "Graduate [1000[[Rebert, Jack, 59, Male, Birchmount Road]]]";
//		if (test.compareTo(s.toString()) == 0) {
//			System.out.println("true");
//		}

	}

}
