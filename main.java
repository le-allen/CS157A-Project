import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws Exception {

        String url = "jdbc:mysql://localhost:3306/pet_project";
        String username = "root";
        String password = "yourpassword";
        Class.forName("com.mysql.cj.jdbc.Driver");

        System.out.println("Hello, Welcome to the CS157a Pet Adoption System!");
        System.out.println("What would you like to do today?");
        System.out.println("Please Enter One of the Following: View Data, Insert, Update, Delete, Run Transaction, Exit");
        Scanner scnr = new Scanner(System.in);

        String input = scnr.nextLine().trim();
        while(!input.equals("q")) {

            if (input.equalsIgnoreCase("view data")) {
                System.out.println("Which data would you like to view?");
                System.out.println();
                System.out.println("Tables: Pet, Medical History, Adopter, Adoption, Shelter, Foster Home, Foster Assignment");
                input = scnr.nextLine();
                
                System.out.println("Here is the " + input + " data:");
                Connection myCon = DriverManager.getConnection(url, username, password);
                
                String sql = "SELECT * FROM " + input;
                PreparedStatement ps = myCon.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()) {
                    System.out.println("");
                }
                
                myCon.close();
                
            }
            else if (input.equalsIgnoreCase("insert")) {
                System.out.println("Please enter the following data for insertion: ");
                System.out.print("Table: ");
                input = scnr.nextLine();
                String[] inputArr = input.split(" ");
                String table = inputArr[0];
                
                Connection myCon = DriverManager.getConnection(url, username, password);
                Inserter inserter = new Inserter();
                inserter.insertData(myCon, table, Arrays.copyOfRange(inputArr, 1, inputArr.length));
                scnr.next();
            }
            else if (input.equalsIgnoreCase("update")) {
                System.out.println("Please enter the following data for update: ");
                System.out.print("Table: ");
                input = scnr.nextLine();
                
            }
            else if (input.equalsIgnoreCase("delete")) {
                System.out.println("Please enter the following data for delete: ");
                System.out.print("Table: ");
                input = scnr.nextLine();
            }
            else if (input.equalsIgnoreCase("run transaction")) {
                
            }
            else if (input.equalsIgnoreCase("exit")) {
                System.out.println("Thank you for using our pet adoption system");
                System.out.println("Now closing...");
                scnr.close();
                return;
            }
            
            scnr.close();
        }
    }
}
    