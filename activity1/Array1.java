import java.util.Scanner;
public class Array1 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        int[] nums = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};

        for (int i = 0; i < nums.length; i++) {
         System.out.println(nums[i]);
        }
         
         System.out.print("Enter index: ");
         int index = input.nextInt();
         
          for (int i = 0; i < nums.length; i++) {
              if (index == nums[i]){
                  System.out.println(i);
              }
              
              
          }
         
        }
    }
