package COMPRO2.week2;

import java.util.Scanner;

public class ArrayMed { 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double[][] matrix = new double[3][4];

        System.out.println("Enter a 3-by-4 matrix row by row:");


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = sc.nextDouble();
            }

    }
}






}