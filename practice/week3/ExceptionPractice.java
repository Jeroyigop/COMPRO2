package practice.week3;

import java.util.Scanner

public class ExceptionPractice {
    public static void main(String[] args) {

        try{ System.out.println("enter a number ");
        int number = new Scanner(System.in).nextInt();
        System.out.println("you entered: " + number);
        } catch (Exception e){
            System.out.println("Invalid input, please enter an integer.");
        }

    
    }
    
    
}
