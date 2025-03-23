package Initial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author ahmad
 */

public class StudentPortal {
    public static void displayStudentGrades(int studentId) {
        String sqlQuery = """
                        SELECT course.Course_Name, enrollment.Grade
                        FROM Enrollments AS enrollment
                        JOIN Courses AS course ON enrollment.Course_Num = course.Course_Num
                        WHERE enrollment.Student_ID = ?
                """;

        try (Connection conn = Database_Conn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {

            stmt.setInt(1, studentId);
            ResultSet result = stmt.executeQuery();

            System.out.println("\nYour Courses and Grades:");

            boolean found = false;
            while (result.next()) {
                found = true;
                String courseName = result.getString("Course_Name");
                double grade = result.getDouble("Grade");

                System.out.println("" + courseName + ": " + grade);
            }

            if (!found) {
                System.out.println("You Are Not Registered In Any Courses.");
            }

        } 
        catch (SQLException excp) {
            System.out.println("Couldn't Fetch Any Grades" + excp.getMessage());
        }
    }
}
