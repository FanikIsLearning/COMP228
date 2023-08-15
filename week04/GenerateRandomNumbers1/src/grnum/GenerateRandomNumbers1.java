package grnum;

import java.util.Random;

public class GenerateRandomNumbers1 {

	public static void main(String args[])
	{
		Random rand = new Random();
		
		int rand_int1 = rand.nextInt(1000);
		int rand_int2 = rand.nextInt(1000);
		
		System.out.println("Random num1:" + rand_int1);
		System.out.println("Random num1:" + rand_int2);
	}
}
