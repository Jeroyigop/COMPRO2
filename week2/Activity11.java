package COMPRO2.week2;

public class Activity11 {
    public static void main(String[] args) {
        int[] theaterRow = new int[8];
        theaterRow[3] = 1;
        for(int i = 0; i < theaterRow.length; i++){
            System.out.println(theaterRow[i]);            
        }

        int availSeats = 0;
        for(int i = 0; i < theaterRow.length; i++){
            if(theaterRow[i] == 0) availSeats++;                     
        }
                
        System.out.println("Available seats: " + availSeats);
    }
}