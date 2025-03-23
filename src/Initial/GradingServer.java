package Initial;

import java.io.*;
import java.net.*;
import java.sql.*;// to handle database using JDBC

public class GradingServer {
    public static void main(String[] args) {
        int port = 5000;//Socket port( sama as student terminal port)

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Grading Server is running on port " + port);

            while (true) {
                Socket studentSocket = serverSocket.accept();
                System.out.println("Student connected successfully: " + studentSocket.getInetAddress());

                new Thread(() -> handleClient(studentSocket)).start();/* to handle every student 
                in independent thread so that the server does not stop receiving other students) */
            }

        } 
        catch (IOException excp) {
            System.out.println("Server Error: " + excp.getMessage());
        }
    }

    private static void handleClient(Socket socket) {/*called when new students connect*/
        try (// receive student id
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String line = in.readLine();
            int studentId = Integer.parseInt(line);
            
            // connect to database and retreive grade using SELECT query
            Connection conn = Database_Conn.getConnection();
            String sql = """
                    SELECT course.Course_Name, enrollment.Grade
                    FROM Enrollments AS enrollment
                    JOIN Courses AS course ON enrollment.Course_Num = course.Course_Num
                    WHERE enrollment.Student_ID = ?
                """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentId);
            ResultSet result = stmt.executeQuery();

            boolean found = false;
            while (result.next()) {
                found = true;
                String course = result.getString("Course_Name");
                double grade = result.getDouble("Grade");
                out.println("Course: " + course + " | Grade: " + grade);
            }

            if (!found) {
                out.println("No grades found to be dispalyed for Student ID: " + studentId);
            }

            conn.close();
            socket.close();

        } 
        catch (Exception excp) {
            System.out.println("Client Handling Error: " + excp.getMessage());
        }
    }
}
