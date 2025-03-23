package Initial;

import java.io.*;// to read/write data through the network
import java.net.*; // to initiate connection using socket
import java.util.Scanner;

public class StudentTerminal {
    public static void main(String[] args) {
        String serverHost = "localhost";
        int port = 5000;//Socket port (same as grading server port)

        try (Socket socket = new Socket(serverHost, port);/*initiate connection
                with grading server on localhost:5000 */
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);// to send StudentID
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));/*used to
                know server response*/
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter Your Student ID: ");
            int studentId = scanner.nextInt();
            out.println(studentId);// send student id to the server through socket

            System.out.println("\nGrades Retreived is:\n");
            String response;
            while ((response = in.readLine()) != null) {
                // loop to read every line received from server
                System.out.println(response);
            }// stop when server close the connection 

        } 
        catch (IOException excp) {
            System.out.println("Connection Failed: " + excp.getMessage());
        }
    }
}
