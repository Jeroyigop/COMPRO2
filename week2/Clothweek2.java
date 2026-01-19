Package week2;

public class Clothweek2 {
    
    public static void main(String[] args) {

        String[][] clothColors = new String[2][3];

        //1st row
        clothColors[0][0] = "red";
        clothColors[0][1] = "blue";
        clothColors[0][2] = "green";

         //2nd row
        clothColors[1][0] = "orange";
        clothColors[1][1] = "yellow";
        clothColors[1][2] = "violet";

        String[][] clotchColors2 = {
            {"red", "blue", "green"},
            {"orange", "yellow", "violet"}
        };

        for (int r = 0; r < clotchColors2.length; r++ ){
            for(int c = 0; c < clotchColors2[r].length; c++){
                System.out.printf("%s", clotchColors2[r][c]);
            }
            System.out.println();
        }

    }
}
