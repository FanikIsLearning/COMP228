package conpac;

public class PracWithConstructors {
	//instance variables, aka fields, properties, or attributes
	private String name;
	private int age;
	//putting back the default constructor since we created at least one custom-made constructor
	public PracWithConstructors()
	{
	}
	//custom constructor
	public PracWithConstructors(String name, int age)
	{
	this.name = name;
	this.age = age;
	}
	// overriding the built-in toString() to display the object state
	public String toString()
	{
	return "\nName: " + name + "\nAge: " + age;
	}
	//the main driver
	public static void main(String[] args)
	{
		//creating an object of class PracWithConstructors using the default constructor supplied automatically by Java
		PracWithConstructors myPracObj1 = new PracWithConstructors();
		//Displaying the object-state of myPracObj1
		System.out.print(myPracObj1);
		//creating another object with the custom contructor
		PracWithConstructors myPracObj2 = new PracWithConstructors("Syed",
		10);
		//printing a blanks line
		System.out.println();
		//Displaying the object-state of myPracObj1
		System.out.print(myPracObj2);
	}
}

