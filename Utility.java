import java.util.Scanner; //import scanner library.
import java.util.Random;
import java.lang.Math.*;
import java.io.FileWriter;
import java.io.IOException;

//class contain method to perform functions including inputting strings and integers, printing information and generating random number
public class Utility {
    //methods to input data
    public static String input(String message) { //method for inputting String values.
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextLine();
    }
    
    //method to input integer values
    public static int inputInt(String message) { //method for inputting integer values.
        return Integer.parseInt(input(message));
    }   
    
    //method to print information
    public static void print(String message) {
        System.out.println(message);
    }
    
    
    //method to get random number
    public static int getRandomNumber(int max, int min) {
        double x = (Math.random() * ((max - min) + 1)) + min;
        return (int)x;
    }
    
    //method to deal with NumberFormatException
    public static boolean isInt(String userInput) {
        try {
            int input = Integer.parseInt(userInput);            
            return true;
            
        }
        catch(NumberFormatException ex) {              
            return false;
        }
    }
    
    
}