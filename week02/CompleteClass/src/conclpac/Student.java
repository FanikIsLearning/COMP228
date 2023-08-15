package conclpac;

public class Student {
	//instance variable declaration
	String name;
	int stId;
	//no arg. constructor
	public Student()
	{
	}
	//one argument constructor
	public Student(String stName)
	{
	name = stName;
	}
	//2-arg constructor
	public Student(String name, int stId)
	{
	this.name = name;
	this.stId = stId;
	}
	//setters
	public void setName(String name)
	{
	this.name = name;
	}
	public void setStId(int id)
	{
	stId = id;
	}
	public void setAll(String name, int Stid)
	{
	this.stId = Stid;
	this.name = name;
	}
	//getters
	public String getName()
	{
	return name;
	}
	public int getStId()
	{
	return stId;
	}
	//overridden toString() to get the complete object state
	@Override
	public String toString()
	{
	return ("Name: " + name + "\n" + "Student ID: " + stId + "\n");
	}
}
