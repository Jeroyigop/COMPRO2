package practice.week3.week4;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileWriter;
public class Name {
    public static void main(String[] args){
    StringBuilder sb = new StringBuilder();


    int age, PNumber;
    String FirstName, Email, LastName;

try(Scanner sc = new Scanner(System.in)){
        System.out.println("Enter your FirstName: ");
        sb.append("First Name: ");
        sb.append(sc.nextLine()).append("\n");
        
        System.out.println("Enter your LastName: ");
        sb.append("Last Name: ");
        sb.append(sc.nextLine()).append("\n");

        System.out.println("Enter your Age: ");
        sb.append("Age: ");
        sb.append(sc.nextLine()).append("\n");

        System.out.println("Enter your Email: ");
        sb.append("Email: ");
        sb.append(sc.nextLine()).append("\n");

        System.out.println("Enter your Phone Number: ");
        sb.append("Phone Number : ");
        sb.append(sc.nextLine()).append("\n");
                }catch(InputMismatchException e){
            System.out.println("Invalid input");
        }


        try(FileWriter fw = new FileWriter("data.txt")){
            fw.write(sb.toString());
            System.out.println("Data is saved...");
        }catch(IOException e){
            System.out.println(e.getMessage());
        }

    
    
    }
    
}
 