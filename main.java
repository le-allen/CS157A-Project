import java.sql.*;
import java.util.Scanner;

public class main {
    
    public static void main(String[] args) throws Exception {

        String url = "jdbc:mysql://localhost:3306/pet_project";
        String username = "root";
        String password = "C3TK13@sql";
        Class.forName("com.mysql.cj.jdbc.Driver");

        System.out.println("Hello, Welcome to the CS157a Pet Adoption System!");
        System.out.println("What would you like to do today?");
        System.out.println("Please Enter One of the Following: View Data, Insert, Update, Delete, Run Transaction, Exit");
        Scanner scnr = new Scanner(System.in);

        String input = scnr.nextLine();

        if (input.equalsIgnoreCase("view data")) {
            System.out.println("Which data would you like to view?");
            System.out.println();
            System.out.println("Tables: Pet, Medical History, Adopter, Adoption, Shelter, Foster Home, Foster Assignment");
            input = scnr.nextLine();

            Connection myCon = DriverManager.getConnection(url, username, password);

            String sql = "SELECT * FROM " + input;
            PreparedStatement ps = myCon.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (input.equalsIgnoreCase("pet")) {
                System.out.println("Here is the " + input + " data:");
                while (rs.next()) {
                    System.out.println(rs.getInt("PetID") + "\t" +
                                        rs.getInt("ShelterID") + "\t" + 
                                        rs.getString("Name") + "\t" + 
                                        rs.getString("Species") + "\t" + 
                                        rs.getInt("Age") + "\t" + 
                                        rs.getString("Status") + "\t");
                }
            }
            else if (input.equalsIgnoreCase("medical history")) {
                System.out.println("Here is the " + input + " data:");
                while (rs.next()) {
                    System.out.println(rs.getInt("PetID") + "\t" +
                                        rs.getString("HealthStatus") + "\t" + 
                                        rs.getBoolean("VaccinationStatus") + "\t");
                }
            }
            else if (input.equalsIgnoreCase("adopter")) {
                System.out.println("Here is the " + input + " data:");
                while (rs.next()) {
                    System.out.println(rs.getInt("AdopterID") + "\t" +
                                        rs.getString("Name") + "\t" + 
                                        rs.getString("Number") + "\t");
                }
            }
            else if (input.equalsIgnoreCase("adoption")) {
                System.out.println("Here is the " + input + " data:");
                while (rs.next()) {
                    System.out.println(rs.getInt("PetID") + "\t" +
                                        rs.getInt("AdopterID") + "\t" + 
                                        rs.getDate("AdoptionDate") + "\t");
                }
            }
            else if (input.equalsIgnoreCase("shelter")) {
                System.out.println("Here is the " + input + " data:");
                while (rs.next()) {
                    System.out.println(rs.getInt("ShelterID") + "\t" +
                                        rs.getString("Address") + "\t");
                }
            }
            else if (input.equalsIgnoreCase("foster home")) {
                System.out.println("Here is the " + input + " data:");
                while (rs.next()) {
                    System.out.println(rs.getInt("FosterID") + "\t" +
                                        rs.getString("Address") + "\t" +
                                        rs.getString("Number") + "\t");
                }
            }
            else if (input.equalsIgnoreCase("foster assignment")) {
                System.out.println("Here is the " + input + " data:");
                while (rs.next()) {
                    System.out.println(rs.getInt("FosterID") + "\t" +
                                        rs.getInt("PetID") + "\t" + 
                                        rs.getDate("StateDate") + "\t" + 
                                        rs.getDate("EndDate") + "\t");
                }
            }
            else {
                System.out.println("Sorry, that table does not exist. Please try again:");
            }
            myCon.close();

        }
        else if (input.equalsIgnoreCase("insert")) {
            System.out.println("Please enter the following data for insertion: ");
            System.out.print("Table: ");
            input = scnr.nextLine();

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
