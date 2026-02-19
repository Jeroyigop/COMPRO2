import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GradeMenu {
    static String[] names;
    static double[][] healthData;


    public static void main(String[] args) {
        names = new String[50];
        healthData = new double[50][3];

        Scanner sc = new Scanner(System.in);
       

        for (int r = 0; r < 5; r++) {
            System.out.print("Enter Subject: ");
            names[r] = sc.nextLine();

            System.out.print("Enter Prelim: ");
            try {
                healthData[r][0] = sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid number");
            }

            System.out.print("Enter Midterm: ");
            try {
                healthData[r][1] = sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid number");
            }
            System.out.print("Enter Finals: ");
            try{
                healthData[r][2] = sc.nextDouble();
            }catch(InputMismatchException e){
                System.out.println("Invalid Number");
            }

            sc.nextLine();
            System.out.println();
            
        }
        writeData();
    
    
    }

    public static void writeData() {
        StringBuilder sb = new StringBuilder();

        sb.append("Subject,Prelim,Midterm,Finals\n");
        for (int r = 0; r < names.length; r++) {
            if(names[r] == null) 
                break;

            sb.append(names[r]);
            for (int c = 0; c < healthData[r].length; c++) {
                sb.append(",").append(healthData[r][c]);
            }
            sb.append("\n");
        }

        try(BufferedWriter bw = new BufferedWriter(new FileWriter("data.csv"))){
            bw.write(sb.toString());
            bw.flush();
        }catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println(sb.toString());
    }
}