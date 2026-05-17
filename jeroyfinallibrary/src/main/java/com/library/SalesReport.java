package com.library;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class SalesReport {

    public static void exportReport() {

        try {
            BufferedWriter writer =
                new BufferedWriter(
                    new FileWriter("sales_report.csv"));

            writer.write("Product,Quantity,Total\n");
            writer.write("Laptop,2,50000\n");
            writer.write("Milk,5,500\n");

            writer.close();

            System.out.println("CSV Report Generated");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
