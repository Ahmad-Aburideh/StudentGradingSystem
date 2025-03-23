package Initial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ahmad
 */
public class InstructorPortal {

    public static void showCourseStudents(int instructorId) {
        String sql = """
            SELECT course.Course_Num, course.Course_Name, user.Userame AS StudentName, enrollment.Grade
            FROM Courses course
            JOIN Enrollments enrollment ON course.Course_Num = enrollment.Course_Num
            JOIN Users user ON enrollment.Student_ID = user.User_ID
            WHERE course.Instructor_ID = ?
            ORDER BY course.Course_Name;
        """;

        try (Connection conn = Database_Conn.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, instructorId);
            ResultSet result = stmt.executeQuery();

            String currentCourse = "";
            boolean hasRows = false;

            while (result.next()) {
                hasRows = true;
                String courseName = result.getString("Course_Name");
                String studentName = result.getString("StudentName");
                double grade = result.getDouble("Grade");

                if (!courseName.equals(currentCourse)) {
                    currentCourse = courseName;
                    System.out.println("\nCourse: " + courseName);
                }

                System.out.println("Student: " + studentName + " | Grade: " + grade);
            }

            if (!hasRows) {
                System.out.println("You don't have any registerd students.");
            }

        } catch (SQLException e) {
            System.out.println("Error in displaying course info:" + e.getMessage());
        }
    }

    public static void updateStudentGrade(int instructorId, int studentId, int courseNum, double grade) {
        String checkSql = "SELECT * FROM Courses WHERE Course_Num = ? AND Instructor_ID = ?";
        String updateSql = "UPDATE Enrollments SET Grade = ? WHERE Student_ID = ? AND Course_Num = ?";

        try (Connection conn = Database_Conn.getConnection(); 
                PreparedStatement checkStmt = conn.prepareStatement(checkSql); 
                PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
            checkStmt.setInt(1, courseNum);
            checkStmt.setInt(2, instructorId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("You are Not Authorized to Modify this course.");
                return;
            }

            
            updateStmt.setDouble(1, grade);
            updateStmt.setInt(2, studentId);
            updateStmt.setInt(3, courseNum);

            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Grade Modified Successfully.");
            } 
            else {
                System.out.println("️No matching Student/Course found.");
            }

        } 
        catch (SQLException excp) {
            System.out.println("Error: " + excp.getMessage());
        }
    }

}
