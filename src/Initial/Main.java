package Initial;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Student Grading System");
        System.out.print("Enter Your Username: ");
        String username = in.nextLine();

        System.out.print("Enter Your Password: ");
        String password = in.nextLine();

        User user = Auth_Service.login(username, password);

        if (user != null) {
            System.out.println("\nLogin Successfully");
            switch (user.getRole()) {
                case "STUDENT":
                    System.out.println("Hello " + user.getUsername() + " ,You are Student");
                    StudentPortal.displayStudentGrades(user.getUserId());
                    break;
                    
                case "INSTRUCTOR":
                    System.out.println("Hello " + user.getUsername()+ " ,You are Instructor");
                    InstructorPortal.showCourseStudents(user.getUserId());
                    System.out.print("\nDo you want to modify a grade? (Yes or No): ");
                    String option = in.nextLine();

                    if (option.equals("Yes")) {
                        System.out.print("Enter Student ID: ");
                        int studentId = Integer.parseInt(in.nextLine());

                        System.out.print("Enter Course Number: ");
                        int courseNum = Integer.parseInt(in.nextLine());

                        System.out.print("Enter New Grade: ");
                        double newGrade = Double.parseDouble(in.nextLine());

                        InstructorPortal.updateStudentGrade(user.getUserId(), studentId, courseNum, newGrade);
                    }
                    break;
                    
                case "ADMIN":
                    System.out.println("Hello " + user.getUsername() + " ,You are Admin");
                    break;
                    
                default:
                    System.out.println("Hello " + user.getUsername() + " ,You are User");
            }
        } 
        else {
            System.out.println("Invalid Username or Password, Please try again :)");
        }

        in.close();
    }
}
