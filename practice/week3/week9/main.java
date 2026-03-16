public class Main {
    public static void main(String [] args){
        Triangle t1 = new Triangle();
        System.out.println("Default Triangle");
        System.out.println(t1);
        System.out.printf("Area: %.2f%n", t1.getArea());
        System.out.printf("Perimater: %.2f%n", + t1.getPerimeter());
        System.out.println("Color: " + t1.getColor());
        System.out.println("Filled: " + t1.isFilled());

        System.out.println("");

        Triangle t2 = new Triangle(3.1, 4.2, 5.3);
        t2.setColor("blue");
        t2.setFilled(true);

        System.out.println("Custom Triangle");
        System.out.println(t2);
        System.out.printf("Area: %.2f%n", t2.getArea());
        System.out.printf("Perimeter: %.2f%n", t2.getPerimeter());
        System.out.printf("Color: ", t2.getColor());
        System.out.printf("Filled: ",  t2.isFilled());

        System.out.println();

        System.out.printf("Side 1:", + t2.getSide1());
        System.out.printf("Side 2:", + t2.getSide2());
        System.out.printf("Side 3:", +  t2.getSide3());
    }
}
