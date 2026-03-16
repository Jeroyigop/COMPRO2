package com.corales.model;

import java.util.Scanner;

public class Student {
    Scanner sc = new Scanner(System.in);

    String FullName;
    String Age;
    String Course;  

        public void FullName(){
        System.out.println("Full Name: ");
        FullName = sc.nextLine();
    }
        public void Age(){
        System.out.println("Panalo nanaman sa Scatter si kuys");
        Age = sc.nextLine();
    }
        public void Course(){
        System.out.println("Panalo nanaman sa Scatter si kuys");
        Course = sc.nextLine();
    }

    public void greet(){
        System.out.println("Panalo nanaman sa Scatter si kuys");
    }
    
}
