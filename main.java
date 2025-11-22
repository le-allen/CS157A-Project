import java.sql.*;
import java.util.Scanner;

public class main {
    
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, Welcome to the CS157a Pet Adoption System!");
        System.out.println("What would you like to do today?");
        System.out.println("Please Enter One of the Following: View Data, Insert, Update, Delete, Run Transaction, Exit");
        Scanner scnr = new Scanner(System.in);

        String input = scnr.nextLine();

        if (input.equalsIgnoreCase("view data")) {

        }
        else if (input.equalsIgnoreCase("insert")) {

        }
        else if (input.equalsIgnoreCase("update")) {

        }
        else if (input.equalsIgnoreCase("delete")) {

        }
        else if (input.equalsIgnoreCase("run transaction")) {

        }
        else if (input.equalsIgnoreCase("exit")) {
            System.out.println("Thank you for using our pet adoption system");
            System.out.println("Now closing...");
            scnr.close();
            return;
        }

        String url = "jdbc:mysql://localhost:3306/pet_project";
        String username = "root";
        String password = "yourpassword";
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection myCon = DriverManager.getConnection(url, username, password);

        
    }
}
