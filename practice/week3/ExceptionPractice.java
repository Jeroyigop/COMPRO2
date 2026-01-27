package practice.week3;

import java.util.Scanner;

public class ExceptionPractice {
    public static void main(String[] args) {
        inputNumber();
    }


        public static int inputNumber(){
            Scanner sc = new Scanner(System.in);
            int number = 0;
            boolean value = false;

            try{ System.out.println("enter a number ");
            number = sc.nextInt();
        System.out.println("you entered: " + number);
        } catch (Exception e){
            System.out.println("Invalid input, please enter an integer.");
        }
        return number;

        
        
        

    
    }
    

    
}
