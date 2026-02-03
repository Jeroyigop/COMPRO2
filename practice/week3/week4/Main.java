package practice.week3.week4;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileWriter;

public class Main{
    public static void main(String[] args){
        StringBuilder sb = new StringBuilder();
        
        System.out.println("Enter something: ");
        try(Scanner sc = new Scanner(System.in)){
            sb.append(sc.nextLine());
        }catch(InputMismatchException e){
            System.out.println("Invalid input");
        }


        //try-with-resource 
        try(FileWriter fw = new FileWriter("data.txt")){
            fw.write(sb.toString());
            System.out.println("Data is saved...");
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}