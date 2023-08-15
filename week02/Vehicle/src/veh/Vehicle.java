package veh;

public class Vehicle {
	//instance variables
	int passengers;
	int fuelCap;
	int kmpl;
	//overloaded constructors
	//Since I have created custom constructor, the default constructor must
	//be typed in if I want to
	//use the default constructor. If you use default constructor to create
	//an object with default values
	//for all its instance variables.
	public Vehicle()
	{
	}
	//user-defined constructor (3-argument, during the creation of object,
	//we can load the object with
	//values for all the instance variables
	public Vehicle(int pass, int fuelCap, int kmp)
	{
	passengers = pass;
	this.fuelCap = fuelCap;
	kmpl = kmp;
	}
	//setter (a method) that sets (modifies) the object values that is the
	//values of all its instance variable
	//can be set by calling this method in association of the object after
	//the creation of the object
	public void setVehicle(int pass, int fc, int kmp)
	{
	passengers = pass;
	fuelCap= fc;
	kmpl = kmp;
	}
	//one of the getters. If called with the created object, it returns the
	//current value of the
	//instance variable passengers
	public int passengers()
	{
	return passengers;
	}
	//methods that calculates something and returns the value
	public int range()
	{
	return kmpl * fuelCap;
	}
	public double fuelNeeded(int kms)
	{
	return (double) kms / kmpl;
	}
	//This is the overidden method toString(). We need this to display all
	//the values of the instance variable
	//with the help of println(). The existence of this overidden
	//toString() allows us to use println and
	//provide the object as the argument as if the object is a primitive
	//data type
	public String toString()
	{
	return "\nNo of passengers: " + passengers + "\nFuel Capacity: "
	+ fuelCap + "\nkms per liter: " + kmpl;
	}
	public static void main(String[] args)
	{
	// create a vehicle object with the default constructor
	//it has only the default values for the instance variables
	Vehicle miniVan1 = new Vehicle();
	System.out.println("The default values of miniVan1: " +
	miniVan1);
	//setting values of the instance variables one by one.
	miniVan1.passengers = 7;
	miniVan1.fuelCap=60;
	miniVan1.kmpl = 20;
	//printing values of the instance variables one by one.
	System.out.println("\nprinting values of the instance variables one by one: ");
	System.out.println("\nPassengers (miniVan1): "+
	miniVan1.passengers);
	System.out.println("\nFuel Capacity (miniVan1) :"+
	miniVan1.fuelCap);
	System.out.println("\nKilometers/liter (miniVan1) :"+
	miniVan1.kmpl);
	//using a 3-argument constructor to create the object
	//this time during the object creation, these values will be
	//assigned to all the instatance variaables.
	Vehicle miniVan2 = new Vehicle(5, 50, 25);
	//printing the values one by one.
	System.out.println("\nPrinting values of the instance variables one-by-one again: ");
	System.out.println("\nPassengers (miniVan2): "+ 
	miniVan2.passengers);
	System.out.println("\nFuel Capacity (miniVan2) :"+
	miniVan2.fuelCap);
	System.out.println("\nKilometers/liter (miniVan2) :"+
	miniVan2.kmpl);
	//now changing the values of the instance variables by calling
	//the 3-argument setVehicle method
	miniVan2.setVehicle(7,45,30);
	//Printing the changed values one by one
	System.out.println("\nPrinting the changed values of the instance variables one-by-one one more time: ");
	System.out.println("\nPassengers (miniVan2): "+
	miniVan2.passengers);
	System.out.println("\nFuel Capacity (miniVan2) :"+
	miniVan2.fuelCap);
	System.out.println("\nKilometers/liter (miniVan2) :"+
	miniVan2.kmpl);
	System.out.println("No of passengers: " + miniVan2.passengers());
	//printing the object state with the overidden toString
	System.out.println("\nPrinting all the values of the instance variables using overridden toString() : ");
	System.out.println(miniVan2);
	double liters;
	int dist = 252;
	liters = miniVan2.fuelNeeded(dist);
	System.out.println("\nTo cover " + dist + " kms the miniVan2 needs " + liters + " liters of fuel.");
	}

}
