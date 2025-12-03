import java.sql.*;
import java.util.Scanner;

public class main {
    
    public static void main(String[] args) throws Exception {

        String url = "jdbc:mysql://localhost:3306/pet_project";
        String username = "root";
        String password = "C3TK13@sql";
        Class.forName("com.mysql.cj.jdbc.Driver");

        System.out.println("Hello, Welcome to the CS157a Pet Adoption System!");
        Scanner scnr = new Scanner(System.in);
        while (true) {
            System.out.println("What would you like to do today?");
            System.out.println("Please Enter One of the Following: View Data, Insert, Update, Delete, Run Transaction, Exit");
            

            String input = scnr.nextLine();

            if (input.equalsIgnoreCase("view data")) {
                while (!input.equalsIgnoreCase("back")) {
                    System.out.println("Which data would you like to view?");
                    System.out.println("Tables: Pet, MedicalHistory, Adopter, Adoption, Shelter, FosterHome, FosterAssignment");
                    System.out.println("*Type 'back' if you would like to return");
                    input = scnr.nextLine();

                    if (input.equalsIgnoreCase("back")) {
                        break;
                    }

                    try (Connection myCon = DriverManager.getConnection(url, username, password)) {

                        String sql = "SELECT * FROM " + input;
                        PreparedStatement ps = myCon.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery();
                        System.out.println();

                        if (input.equalsIgnoreCase("pet")) {
                            System.out.println("Here is the " + input + " data:");
                            System.out.println("PetID:" + "\t" + "ShelterID:" + "\t" + "Name:" + "\t" + "Species:" + "\t" + "Age:" + "\t" + "Status:" + "\t");
                            while (rs.next()) {
                                System.out.println(rs.getInt("PetID") + "\t" +
                                                    rs.getInt("ShelterID") + "\t" + 
                                                    rs.getString("Name") + "\t" + 
                                                    rs.getString("Species") + "\t" + 
                                                    rs.getInt("Age") + "\t" + 
                                                    rs.getString("Status") + "\t");
                            }
                        }
                        else if (input.equalsIgnoreCase("medicalhistory")) {
                            System.out.println("Here is the " + input + " data:");
                            System.out.println("PetID:" + "\t" + "HealthStatus:" + "\t" + "VaccinationStatus:" + "\t");
                            while (rs.next()) {
                                System.out.println(rs.getInt("PetID") + "\t" +
                                                    rs.getString("HealthStatus") + "\t" + 
                                                    rs.getBoolean("VaccinationStatus") + "\t");
                            }
                        }
                        else if (input.equalsIgnoreCase("adopter")) {
                            System.out.println("Here is the " + input + " data:");
                            System.out.println("AdopterID:" + "\t" + "Name:" + "\t" + "Number:" + "\t");
                            while (rs.next()) {
                                System.out.println(rs.getInt("AdopterID") + "\t" +
                                                    rs.getString("Name") + "\t" + 
                                                    rs.getString("Number") + "\t");
                            }
                        }
                        else if (input.equalsIgnoreCase("adoption")) {
                            System.out.println("Here is the " + input + " data:");
                            System.out.println("PetID:" + "\t" + "AdopterID:" + "\t" + "AdoptionDate:" + "\t");
                            while (rs.next()) {
                                System.out.println(rs.getInt("PetID") + "\t" +
                                                    rs.getInt("AdopterID") + "\t" + 
                                                    rs.getDate("AdoptionDate") + "\t");
                            }
                        }
                        else if (input.equalsIgnoreCase("shelter")) {
                            System.out.println("Here is the " + input + " data:");
                            System.out.println("ShelterID:" + "\t" + "Address:" + "\t");
                            while (rs.next()) {
                                System.out.println(rs.getInt("ShelterID") + "\t" +
                                                    rs.getString("Address") + "\t");
                            }
                        }
                        else if (input.equalsIgnoreCase("fosterhome")) {
                            System.out.println("Here is the " + input + " data:");
                            System.out.println("FosterID:" + "\t" + "Address:" + "\t" + "Number:" + "\t");
                            while (rs.next()) {
                                System.out.println(rs.getInt("FosterID") + "\t" +
                                                    rs.getString("Address") + "\t" +
                                                    rs.getString("Number") + "\t");
                            }
                        }
                        else if (input.equalsIgnoreCase("fosterassignment")) {
                            System.out.println("Here is the " + input + " data:");
                            System.out.println("FosterID:" + "\t" + "PetID:" + "\t" + "StartDate:" + "\t" + "EndDate:" + "\t");
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

                        System.out.println();
                        myCon.close();
                    }
                    catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }

            }
            else if (input.equalsIgnoreCase("insert")) {
                System.out.println("Please enter the following data for insertion: ");
                System.out.print("Table: ");
                input = scnr.nextLine();

            }
            else if (input.equalsIgnoreCase("update")) {
                while (!input.equalsIgnoreCase("back")) {
                    System.out.println("Please enter the following table to update: ");
                    System.out.println("Tables: Pet, MedicalHistory, Adopter, Adoption, Shelter, FosterHome, FosterAssignment");
                    System.out.println("*Type 'back' if you would like to return");
                    input = scnr.nextLine();

                    if (input.equalsIgnoreCase("back")) {
                        break;
                    }

                    if (input.equalsIgnoreCase("pet")) {
                        System.out.println("Which pet would you like to update: ");
                        System.out.print("PetID: ");
                        int id = scnr.nextInt();
                        scnr.nextLine();

                        try (Connection myCon = DriverManager.getConnection(url, username, password)) {
                            String sql = "SELECT * FROM " + input + " WHERE PetID = ?";
                            PreparedStatement ps = myCon.prepareStatement(sql);
                            ps.setInt(1, id);
                            ResultSet rs = ps.executeQuery();

                            while (rs.next()) {
                                System.out.println("PetID: " + rs.getInt("PetID"));
                                System.out.println("ShelterID: " + rs.getInt("ShelterID"));
                                System.out.println("Name: " + rs.getString("Name"));
                                System.out.println("Species: " + rs.getString("Species"));
                                System.out.println("Age: " + rs.getInt("Age"));
                                System.out.println("Status: " + rs.getString("Status"));
                            }

                            System.out.println();
                            System.out.println("What would you like to update?");
                            System.out.println("Name - Species - Age - Status");
                            String update = scnr.nextLine();

                            if (update.equalsIgnoreCase("Name")) {
                                System.out.print("Enter new name: ");
                                update = scnr.nextLine();
                                sql = "UPDATE Pet SET Name = ? WHERE PetID = ?";
                                ps = myCon.prepareStatement(sql);
                                ps.setString(1, update);
                                ps.setInt(2, id);
                                int rows = ps.executeUpdate();
                                if (rows > 0) {
                                    System.out.println("Update Successful!");
                                }
                            }
                            else if (update.equalsIgnoreCase("Species")) {
                                System.out.print("Enter new Species: ");
                                update = scnr.nextLine();
                                sql = "UPDATE Pet SET Species = ? WHERE PetID = ?";
                                ps = myCon.prepareStatement(sql);
                                ps.setString(1, update);
                                ps.setInt(2, id);
                                int rows = ps.executeUpdate();
                                if (rows > 0) {
                                    System.out.println("Update Successful!");
                                }
                            }
                            else if (update.equalsIgnoreCase("Age")) {
                                System.out.print("Enter new Age: ");
                                update = scnr.nextLine();
                                sql = "UPDATE Pet SET Age = ? WHERE PetID = ?";
                                ps = myCon.prepareStatement(sql);
                                ps.setInt(1, Integer.parseInt(update));
                                ps.setInt(2, id);
                                int rows = ps.executeUpdate();
                                if (rows > 0) {
                                    System.out.println("Update Successful!");
                                }
                            }
                            else if (update.equalsIgnoreCase("Status")) {
                                System.out.print("Enter new status as written: (Available, Adopted, Fostered, Unavailable)");
                                update = scnr.nextLine();
                                if (update.equalsIgnoreCase("available") || update.equalsIgnoreCase("adopted") || update.equalsIgnoreCase("fostered") || update.equalsIgnoreCase("unavailable")) {
                                    sql = "UPDATE Pet SET Status = ? WHERE PetID = ?";
                                    ps = myCon.prepareStatement(sql);
                                    ps.setString(1, update);
                                    ps.setInt(2, id);
                                    int rows = ps.executeUpdate();
                                    if (rows > 0) {
                                        System.out.println("Update Successful!");
                                    }
                                }
                                else {
                                    System.out.println("Inavlid Status: Please try again");
                                }
                            }

                            myCon.close();
                        }
                        catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    else if (input.equalsIgnoreCase("medicalhistory")) {
                        System.out.println("Which pet would you like to update: ");
                        System.out.print("PetID: ");
                        int id = scnr.nextInt();
                        scnr.nextLine();

                        try (Connection myCon = DriverManager.getConnection(url, username, password)) {
                            String sql = "SELECT * FROM " + input + " WHERE PetID = ?";
                            PreparedStatement ps = myCon.prepareStatement(sql);
                            ps.setInt(1, id);
                            ResultSet rs = ps.executeQuery();

                            while (rs.next()) {
                                System.out.println("PetID: " + rs.getInt("PetID"));
                                System.out.println("HealthStatus: " + rs.getString("HealthStatus"));
                                System.out.println("VaccinationStatus: " + rs.getBoolean("VaccinationStatus"));
                            }

                            System.out.println();
                            System.out.println("What would you like to update?");
                            System.out.println("HealthStatus - VaccinationStatus");
                            String update = scnr.nextLine();

                            if (update.equalsIgnoreCase("HealthStatus")) {
                                System.out.print("Enter new status as written: (Healthy, Sick, Injured, Chronic Condition");
                                update = scnr.nextLine();
                                if (update.equalsIgnoreCase("Healthy") || update.equalsIgnoreCase("Sick") || update.equalsIgnoreCase("Injured") || update.equalsIgnoreCase("Chronic condition")) {
                                    sql = "UPDATE MedicalHistory SET HealthStatus = ? WHERE PetID = ?";
                                    ps = myCon.prepareStatement(sql);
                                    ps.setString(1, update);
                                    ps.setInt(2, id);
                                    int rows = ps.executeUpdate();
                                    if (rows > 0) {
                                        System.out.println("Update Successful!");
                                    }
                                }
                                else {
                                    System.out.println("Invalid Status: Please try again");
                                }
                            }
                            else if (update.equalsIgnoreCase("VaccinationStatus")) {
                                System.out.print("Enter new status as written: (true, false)");
                                update = scnr.nextLine();
                                sql = "UPDATE MedicalHistory SET VaccinationStatus = ? WHERE PetID = ?";
                                ps = myCon.prepareStatement(sql);
                                ps.setBoolean(1, Boolean.parseBoolean(update));
                                ps.setInt(2, id);
                                int rows = ps.executeUpdate();
                                if (rows > 0) {
                                    System.out.println("Update Successful!");
                                }
                            }
                        }
                        catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    else if (input.equalsIgnoreCase("adopter")) {
                        System.out.println("Which adopter would you like to update: ");
                        System.out.print("AdopterID: ");
                        int id = scnr.nextInt();
                        scnr.nextLine();

                        try (Connection myCon = DriverManager.getConnection(url, username, password)) {
                            String sql = "SELECT * FROM " + input + " WHERE AdopterID = ?";
                            PreparedStatement ps = myCon.prepareStatement(sql);
                            ps.setInt(1, id);
                            ResultSet rs = ps.executeQuery();

                            while (rs.next()) {
                                System.out.println("AdopterID: " + rs.getInt("AdopterID"));
                                System.out.println("Name: " + rs.getString("Name"));
                                System.out.println("Number: " + rs.getString("Number"));
                            }

                            System.out.println();
                            System.out.println("What would you like to update?");
                            System.out.println("Name - Number");
                            String update = scnr.nextLine();

                            if (update.equalsIgnoreCase("Name")) {
                                System.out.print("Enter new name: ");
                                update = scnr.nextLine();
                                sql = "UPDATE Adopter SET Name = ? WHERE AdopterID = ?";
                                ps = myCon.prepareStatement(sql);
                                ps.setString(1, update);
                                ps.setInt(2, id);
                                int rows = ps.executeUpdate();
                                if (rows > 0) {
                                    System.out.println("Update Successful!");
                                }
                            }
                            else if (update.equalsIgnoreCase("Number")) {
                                System.out.print("Enter new number as XXX-XXXX: ");
                                update = scnr.nextLine();
                                sql = "UPDATE Adopter SET Number = ? WHERE AdopterID = ?";
                                ps = myCon.prepareStatement(sql);
                                ps.setString(1, update);
                                ps.setInt(2, id);
                                int rows = ps.executeUpdate();
                                if (rows > 0) {
                                    System.out.println("Update Successful!");
                                }
                            }
                        }
                        catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    else if (input.equalsIgnoreCase("adoption")) {
                        System.out.println("Which adoption would you like to update: ");
                        System.out.print("PetID: ");
                        int id = scnr.nextInt();
                        scnr.nextLine();

                        System.out.print("AdopterID: ");
                        int id2 = scnr.nextInt();
                        scnr.nextLine();

                        try (Connection myCon = DriverManager.getConnection(url, username, password)) {
                            String sql = "SELECT * FROM " + input + " WHERE PetID = ? AND AdopterID = ?";
                            PreparedStatement ps = myCon.prepareStatement(sql);
                            ps.setInt(1, id);
                            ps.setInt(2, id2);
                            ResultSet rs = ps.executeQuery();

                            while (rs.next()) {
                                System.out.println("PetID: " + rs.getInt("PetID"));
                                System.out.println("AdopterID: " + rs.getString("AdopterID"));
                                System.out.println("AdoptionDate: " + rs.getString("AdoptionDate"));
                            }

                            System.out.println();
                            System.out.println("What would you like to update?");
                            System.out.println("AdoptionDate");
                            String update = scnr.nextLine();

                            if (update.equalsIgnoreCase("AdoptionDate")) {
                                System.out.print("Enter new date: (YYYY-MM-DD)");
                                update = scnr.nextLine();
                                sql = "UPDATE Adoption SET AdoptionDate = ? WHERE PetID = ? AND AdopterID = ?";
                                ps = myCon.prepareStatement(sql);
                                ps.setDate(1, java.sql.Date.valueOf(update));
                                ps.setInt(2, id);
                                ps.setInt(3, id2);
                                int rows = ps.executeUpdate();
                                if (rows > 0) {
                                    System.out.println("Update Successful!");
                                }
                            }
                        }
                        catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    else if (input.equalsIgnoreCase("shelter")) {
                        System.out.println("Which shelter would you like to update: ");
                        System.out.print("ShelterID: ");
                        int id = scnr.nextInt();
                        scnr.nextLine();

                        try (Connection myCon = DriverManager.getConnection(url, username, password)) {
                            String sql = "SELECT * FROM " + input + " WHERE ShelterID = ?";
                            PreparedStatement ps = myCon.prepareStatement(sql);
                            ps.setInt(1, id);
                            ResultSet rs = ps.executeQuery();

                            while (rs.next()) {
                                System.out.println("ShelterID: " + rs.getInt("ShelterID"));
                                System.out.println("Address: " + rs.getString("Address"));
                            }

                            System.out.println();
                            System.out.println("What would you like to update?");
                            System.out.println("Address");
                            String update = scnr.nextLine();

                            if (update.equalsIgnoreCase("Address")) {
                                System.out.print("Enter new address: ");
                                update = scnr.nextLine();
                                sql = "UPDATE Shelter SET Address = ? WHERE ShelterID = ?";
                                ps = myCon.prepareStatement(sql);
                                ps.setString(1, update);
                                ps.setInt(2, id);
                                int rows = ps.executeUpdate();
                                if (rows > 0) {
                                    System.out.println("Update Successful!");
                                }
                            }
                        }
                        catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    else if (input.equalsIgnoreCase("fosterhome")) {
                        System.out.println("Which foster home would you like to update: ");
                        System.out.print("FosterID: ");
                        int id = scnr.nextInt();
                        scnr.nextLine();

                        try (Connection myCon = DriverManager.getConnection(url, username, password)) {
                            String sql = "SELECT * FROM " + input + " WHERE FosterID = ?";
                            PreparedStatement ps = myCon.prepareStatement(sql);
                            ps.setInt(1, id);
                            ResultSet rs = ps.executeQuery();

                            while (rs.next()) {
                                System.out.println("FosterID: " + rs.getInt("FosterID"));
                                System.out.println("Address: " + rs.getString("Address"));
                                System.out.println("Number: " + rs.getString("Number"));
                            }

                            System.out.println();
                            System.out.println("What would you like to update?");
                            System.out.println("Address - Number");
                            String update = scnr.nextLine();

                            if (update.equalsIgnoreCase("Address")) {
                                System.out.print("Enter new address: ");
                                update = scnr.nextLine();
                                sql = "UPDATE FosterHome SET Address = ? WHERE FosterID = ?";
                                ps = myCon.prepareStatement(sql);
                                ps.setString(1, update);
                                ps.setInt(2, id);
                                int rows = ps.executeUpdate();
                                if (rows > 0) {
                                    System.out.println("Update Successful!");
                                }
                            }
                            else if (update.equalsIgnoreCase("Number")) {
                                System.out.print("Enter new number as XXX-XXXX: ");
                                update = scnr.nextLine();
                                sql = "UPDATE FosterHome SET Number = ? WHERE FosterID = ?";
                                ps = myCon.prepareStatement(sql);
                                ps.setString(1, update);
                                ps.setInt(2, id);
                                int rows = ps.executeUpdate();
                                if (rows > 0) {
                                    System.out.println("Update Successful!");
                                }
                            }
                        }
                        catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    else if (input.equalsIgnoreCase("fosterassignment")) {
                        System.out.println("Which foster assignment would you like to update: ");
                        System.out.print("FosterID: ");
                        int id = scnr.nextInt();
                        scnr.nextLine();

                        System.out.print("PetID: ");
                        int id2 = scnr.nextInt();
                        scnr.nextLine();

                        try (Connection myCon = DriverManager.getConnection(url, username, password)) {
                            String sql = "SELECT * FROM " + input + " WHERE FosterID = ? AND PetID = ?";
                            PreparedStatement ps = myCon.prepareStatement(sql);
                            ps.setInt(1, id);
                            ps.setInt(2, id2);
                            ResultSet rs = ps.executeQuery();

                            while (rs.next()) {
                                System.out.println("FosterID: " + rs.getInt("FosterID"));
                                System.out.println("PetID: " + rs.getInt("PetID"));
                                System.out.println("StartDate: " + rs.getString("StartDate"));
                                System.out.println("EndDate: " + rs.getString("EndDate"));
                            }

                            System.out.println();
                            System.out.println("What would you like to update?");
                            System.out.println("StartDate - EndDate");
                            String update = scnr.nextLine();

                            if (update.equalsIgnoreCase("StartDate")) {
                                System.out.print("Enter new date: (YYYY-MM-DD)");
                                update = scnr.nextLine();
                                sql = "UPDATE FosterAssignment SET StartDate = ? WHERE FosterID = ? AND PetID = ?";
                                ps = myCon.prepareStatement(sql);
                                ps.setDate(1, java.sql.Date.valueOf(update));
                                ps.setInt(2, id);
                                ps.setInt(3, id2);
                                int rows = ps.executeUpdate();
                                if (rows > 0) {
                                    System.out.println("Update Successful!");
                                }
                            }
                            else if (update.equalsIgnoreCase("EndDate")) {
                                System.out.print("Enter new date: (YYYY-MM-DD)");
                                update = scnr.nextLine();
                                sql = "UPDATE FosterAssignment SET EndDate = ? WHERE FosterID = ? AND PetID = ?";
                                ps = myCon.prepareStatement(sql);
                                ps.setDate(id2, java.sql.Date.valueOf(update));
                                ps.setInt(2, id);
                                ps.setInt(3, id2);
                                int rows = ps.executeUpdate();
                                if (rows > 0) {
                                    System.out.println("Update Successful!");
                                }
                            }
                        }
                        catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
            else if (input.equalsIgnoreCase("delete")) {
                System.out.println("Please enter the following data for delete: ");
                System.out.print("Table: ");
                input = scnr.nextLine();
            }
            else if (input.equalsIgnoreCase("run transaction")) {
                Connection myCon = DriverManager.getConnection(url, username, password);
                myCon.setAutoCommit(false);
                try {
                    System.out.println("Start transaction: ");
                    // Transaction 

                    // Statement 1
                    PreparedStatement adopt = myCon.prepareStatement("INSERT INTO Adoption VALUES (?, ?, ?)");
                    adopt.setInt(1, 1);
                    adopt.setInt(2, 205);
                    adopt.setDate(3, java.sql.Date.valueOf("2025-11-22"));
                    adopt.executeUpdate();
                    // Statement 2
                    PreparedStatement medical = myCon.prepareStatement("UPDATE MedicalHistory SET HealthStatus=?, VaccinationStatus=? WHERE PetID=?");
                    medical.setString(1, "Healthy");
                    medical.setBoolean(2, true);
                    medical.setInt(3, 1);
                    medical.executeUpdate();

                    myCon.commit();
                    System.out.println("Transaction committed.");

                } catch (SQLException e) {
                    System.out.println("Roll back transaction.");
                    myCon.rollback();
                } finally {
                    System.out.println("Table Adoption Updated: ");
                    PreparedStatement viewAdopt = myCon.prepareStatement("SELECT * FROM Adoption");
                    ResultSet rs = viewAdopt.executeQuery();

                    while (rs.next()) {
                        System.out.println(rs.getInt("PetID") + "\t" +
                                            rs.getInt("AdopterID") + "\t" + 
                                            rs.getDate("AdoptionDate") + "\t");
                    }

                    System.out.println("Table MedicalHistory Updated: ");
                    PreparedStatement viewMedical = myCon.prepareStatement("SELECT * FROM MedicalHistory");
                    rs = viewMedical.executeQuery();
                    while (rs.next()) {
                        System.out.println(rs.getInt("PetID") + "\t" +
                                            rs.getString("HealthStatus") + "\t" + 
                                            rs.getBoolean("VaccinationStatus") + "\t");
                    }
                }
            }
            else if (input.equalsIgnoreCase("exit")) {
                System.out.println("Thank you for using our pet adoption system");
                System.out.println("Now closing...");
                scnr.close();
                break;
            }

        }
        scnr.close();
    }
}
