import java.util.*;

public class Activity1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] theater = new int[5][8];

        theater[2][5] = 1;
        theater[0][0] = 1;

        boolean showSeats = false;

        while (true) {
            System.out.print("BOOK SEAT Y/N?: ");
            char choice = sc.next().charAt(0);

            if (choice == 'Y' || choice == 'y') {
                showSeats = true;
                break;
            } 
            else if (choice == 'N' || choice == 'n') {
                showSeats = false;
                break;
            } 
            else {
                System.out.println("Invalid input");
            }
        }

    
        if (!showSeats) {
            System.out.println("Booking cancelled.");
            return;
        }

        for (int r = 0; r < theater.length; r++) {
            for (int c = 0; c < theater[r].length; c++) {
                System.out.printf("%-2d", theater[r][c]);
            }
            System.out.println();
        }

 
        int bookedSeats = 0;
        for (int r = 0; r < theater.length; r++) {
            for (int c = 0; c < theater[r].length; c++) {
                if (theater[r][c] == 1) bookedSeats++;
            }
        }

        System.out.println("\nBooked seats: " + bookedSeats);
    }
}