import java.util.*;
import java.io.*;

public class AttendanceApp {

    public static void main(String[] args) {

        ArrayList<Student> students = new ArrayList<>();

        addStudent(students, name: "John");
        addStudent(students, name: "Doe");

        try {
            recordAttendance(students, studentName: "John", mark: 1);
            recordAttendance(students, studentName: "John", mark: 0);
            recordAttendance(students, studentName: "doe", mark: 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        displayAllStudents(students);

        saveStudents(students, filename: "attendance.txt");
    }

    public static void addStudent(ArrayList<Student> students, String name) {
        students.add(new Student(name));
    }

    public static void recordAttendance(ArrayList<Student> students, String studentName, int mark) throws Exception {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(studentName)) {
                s.addAttendanceMark(mark);
                return;
            }
        }

        throw new Exception("Student not found: " + studentName);
    }

    public static double getAttendancePercentage(Student student) {
        ArrayList<Integer> marks = student.getAttendanceMarks();

        if (marks.isEmpty())
            return 0;

        int present = 0;

        for (int mark : marks) {
            if (mark == 1)
                present++;
        }

        return (present * 100.0) / marks.size();
    }

    public static String getDisplayInfo(Student student) {
        return "Name: " + student.getName() +
               ", Attendance: " +
               String.format("%.2f", getAttendancePercentage(student)) + "%";
    }

    public static void displayAllStudents(ArrayList<Student> students) {
        for (Student s : students) {
            System.out.println(getDisplayInfo(s));
        }
    }

    public static void saveStudents(ArrayList<Student> students, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Student s : students) {
                StringBuilder sb = new StringBuilder();
                sb.append(s.getName());

                for (int mark : s.getAttendanceMarks()) {
                    sb.append(",").append(mark);
                }

                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}