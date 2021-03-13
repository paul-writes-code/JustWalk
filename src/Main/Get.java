package Main;
/* @(#)Get.java
 *
 *
 * @T.Bennitz
 * @version 2.00 2012/11/19
 *
 *	Class to get user input.
	 *	Get.string()
	 *	Get.integer()
	 *	Get.real()
	 *	Get.character()
	 *	Get.random(int,int)
	 *	Get.cosine (double,double,double)
 *
 */
import java.util.Scanner;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Get {
	
	//Global Variable Declaration
	public static Scanner input = new Scanner (System.in);
	public static String eol;
	private static boolean inputWorks;
	private static String userIn;

	//Get.string
	public static String string () {
	 	return input.nextLine();
	 }
	
	//Get.integer
	public static int integer() {
		int i = 0;
		userIn = "";

		do {
			userIn = input.nextLine();
			try {
				inputWorks = true;
				i = Integer.parseInt (userIn);
			}
			catch (NumberFormatException nfe){
				System.err.println ("Not a valid. Please enter an integer.");
				inputWorks = false;
			}
		} while (inputWorks == false);
		return i;
	}
	
	public static int integer(int low, int high) {
		int i = 0;
		userIn = "";

		do {
			userIn = input.nextLine();
			try {
				inputWorks = true;
				i = Integer.parseInt (userIn);
			}
			catch (NumberFormatException nfe){
				System.err.println ("Not a valid. Please enter an integer between "+low+" & "+high+".");
				inputWorks = false;
			}
		} while (inputWorks == false||i <low || i>high);
		return i;
	}

	//Get.real
	public static double real (){
		double d = 0;
		userIn = "";

		do {
			userIn = input.nextLine();
			try {
				inputWorks = true;
				d = Double.parseDouble (userIn);
			}
			catch (NumberFormatException nfe){
				System.err.println ("Not a valid. Please enter an double.");
				inputWorks = false;
			}
		} while (inputWorks == false);
		return d;
	}

	//Get.character
	public static char character(){
		return Get.string().charAt(0);
	}
	
	public static char character(int i){
		return Get.string().charAt(i);
	}

	//Get.random
	public static int random(int high, int low){
		return (int)(low + Math.random()*(high-low+1));
	}
	
	public static double random(double high, double low){
		return (double)(low + Math.random( )*(high-low+1));
	}

	//Get.cosine
	public static double cosine (double b, double c, double A) {
    	double a;
    	a= Math.pow(b,2) + Math.pow(c,2) - (2*(b*c)) * Math.cos(Math.toRadians(A));
    	a=Math.sqrt(a);
    	return a;
    }

	//Get.divide
	public static boolean divide (int x, int y){
		int high, low;
		int extra;

		high = Math.max (x,y);
		low = Math.min (x,y);
		extra = high % low;
		inputWorks = (extra==0);
		return inputWorks;
	}
	
	public static void date () {
	   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	   
	   //get current date time with Date()
	   Date date = new Date();
	   System.out.println(dateFormat.format(date));
	  }
}

