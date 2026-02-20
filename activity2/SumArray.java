import java.util.Scanner;

public class SumArray {

    public static void main(String[] args) {
        try (Scanner kbd = new Scanner(System.in)) {

            System.out.println("Enter a 3-by-4 matrix row by row:");
            double[][] matrix1 = new double[3][4];

            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 4; c++) {
                    matrix1[r][c] = kbd.nextDouble();
                }
            }

            for (int c = 0; c < 4; c++) {
                System.out.println("Sum of the elements at column " + c +
                        " is " + sumColumn(matrix1, c));
            }


            System.out.println("\nEnter a 4-by-4 matrix row by row:");
            double[][] matrix2 = new double[4][4];

            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    matrix2[r][c] = kbd.nextDouble();
                }
            }

            System.out.println("Sum of the elements in the major diagonal is "
                    + sumMajorDiagonal(matrix2));
        }
    }

    public static double sumColumn(double[][] m, int columnIndex) {
        double sum = 0;
        for (int i = 0; i < m.length; i++) {
            sum += m[i][columnIndex];
        }
        return sum;
    }

    public static double sumMajorDiagonal(double[][] m) {
        double sum = 0;
        for (int i = 0; i < m.length; i++) {
            sum += m[i][i];
        }
        return sum;
    }
}