package conclpac;

public class CompleteClass {
	public static void main(String[] args)
	{
	Student st1 = new Student();
	System.out.println(st1);
	st1.setName("Syed Yamin");
	st1.setStId(12345);
	System.out.println(st1);
	//modify the values of st1
	st1.setName("Ilia Nika");
	st1.setStId(7890);
	System.out.println(st1);
	//creating st2 with 1-arg constructor
	Student st2 = new Student("Petross Passass");
	System.out.println(st2);
	//creating st3 with 2-arg constructor
	Student st3 = new Student("Susan Reeves", 23454);
	System.out.println(st3);
	//getting the name only of st3
	String stName = st3.getName();
	System.out.println(stName);
	//creating st4 with no argument constructor
	Student st4 = new Student();
	System.out.println(st4);
	//setting all the values of st4 wiht setAll
	st4.setAll("Katsio Nagasaki", 6785);
	System.out.println(st4);
	}
}
