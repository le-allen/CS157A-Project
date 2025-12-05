
import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

class Inserter {
    public void insertData(Connection conn, String table, String[] values) {
        String fields = "";
        try {
            if (table.equalsIgnoreCase("pet")) {
                String sql = "INSERT INTO Pet (PetID, ShelterID, Name, Species, Age, Status) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                fields = "PetID, ShelterID, Name, Species, Age, Status";

                // For names that have spaces
                String name = "";
                if (values.length > 6) {
                    for (int i = 2; i < values.length - 3; i++) {
                        name += values[i] + " ";
                    }
                }
                // Messes up if species has spaces but I cant think of any

                ps.setInt(1, Integer.parseInt(values[0]));
                ps.setInt(2, Integer.parseInt(values[1]));
                ps.setString(3, name.trim());
                ps.setString(4, values[values.length - 3]);
                ps.setInt(5, Integer.parseInt(values[values.length - 2]));
                ps.setString(6, values[values.length - 1]);

                int rowsAffected = ps.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected. " + Arrays.toString(values));

            } else if (table.equalsIgnoreCase("medicalhistory")) {
                String sql = "INSERT INTO MedicalHistory (PetID, HealthStatus, VaccinationStatus) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                fields = "PetID, HealthStatus, VaccinationStatus";

                ps.setInt(1, Integer.parseInt(values[0]));
                ps.setString(2, values[1]);
                ps.setBoolean(3, Boolean.parseBoolean(values[2]));

                int rowsAffected = ps.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected. " + Arrays.toString(values));
            } else if (table.equalsIgnoreCase("adopter")) {
                String sql = "INSERT INTO Adopter (AdopterID, Name, Number) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                fields = "AdopterID, Name, Number"; // Pray number does not have spaces

                String name = "";
                if (values.length > 6) {
                    for (int i = 2; i < values.length - 1; i++) {
                        name += values[i] + " ";
                    }
                }

                ps.setInt(1, Integer.parseInt(values[0]));
                ps.setString(2, name.trim());
                ps.setString(3, values[values.length - 1]);

                int rowsAffected = ps.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected. " + Arrays.toString(values));
            } else if (table.equalsIgnoreCase("adoption")) {
                conn.setAutoCommit(false);

                String sql = "INSERT INTO Adoption (PetID, AdopterID, AdoptionDate) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                fields = "PetID, AdopterID, AdoptionDate";

                String sql_check = "SELECT * FROM PET WHERE PetID = ?";
                PreparedStatement ps_check = conn.prepareStatement(sql_check);
                
                ps_check.setInt(1, Integer.parseInt(values[0]));
                ResultSet rs_check = ps_check.executeQuery();

                if (!rs_check.next()){
                    System.out.println("Please enter a valid PetID. Adoption insertion failed, rollback.");
                    conn.rollback();
                }
                
                ps.setInt(1, Integer.parseInt(values[0]));
                ps.setInt(2, Integer.parseInt(values[1]));
                ps.setDate(3, Date.valueOf(values[2]));

                int rowsAffected = ps.executeUpdate();
                conn.commit();
                System.out.println(rowsAffected + " row(s) affected. " + Arrays.toString(values));
            } else if (table.equalsIgnoreCase("shelter")) {
                String sql = "INSERT INTO Shelter (ShelterID, Address) VALUES (?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                fields = "ShelterID, Address";

                ps.setInt(1, Integer.parseInt(values[0]));

                // Re assemble string for address
                String address = "";
                for (int i = 1; i < values.length; i++) {
                    address += " " + values[i];
                }
                
                ps.setString(2, address);

                int rowsAffected = ps.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected. " + Arrays.toString(values));
            } else if (table.equalsIgnoreCase("fosterhome")) {
                String sql = "INSERT INTO FosterHome (FosterID, Address, Number) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                fields = "FosterID, Address, Number";

                String address = values[1];
                for (int i = 1; i < values.length - 1; i++) {
                    address += " " + values[i + 1];
                }

                ps.setInt(1, Integer.parseInt(values[0]));
                ps.setString(2, address);
                ps.setString(3, values[values.length - 1]);

                int rowsAffected = ps.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected. " + Arrays.toString(values));
            } else if (table.equalsIgnoreCase("fosterassignment")) {
                String sql = "INSERT INTO FosterAssignemnt (FosterID, PetID, StartDate, EndDate) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                fields = "FosterID, PetID, StartDate, EndDate";

                ps.setInt(1, Integer.parseInt(values[0]));
                ps.setInt(2, Integer.parseInt(values[1]));
                ps.setDate(3, Date.valueOf(values[2]));
                ps.setDate(4, Date.valueOf(values[3]));

                int rowsAffected = ps.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected. " + Arrays.toString(values));
            } else {
                System.out.println("Unknown table: " + table);
            }
        } catch (SQLException e) {
            System.out.println("SQL error on insert: " + e.getMessage());
            System.out.println("For table: " + table + " with values: " + Arrays.toString(values));
            System.out.println("Expected fields for " + table + "(in exact order): " + fields);
        }
    }
}

public class main {

    public static void main(String[] args) throws Exception {
        String url = null;
        String username = null;
        String password = null;

        Properties properties = new Properties();
        FileInputStream inputStream = null;
        try {

            inputStream = new FileInputStream("app.properties");
            properties.load(inputStream);

            url = properties.getProperty("database.url");
            username = properties.getProperty("database.username");
            password = properties.getProperty("database.password");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Class.forName("com.mysql.cj.jdbc.Driver");

        System.out.println("Hello, Welcome to the CS157a Pet Adoption System!");
        Scanner scnr = new Scanner(System.in);
        while (true) {
            System.out.println("What would you like to do today?");
            System.out.println("Please Enter One of the Following: View Data, Insert, Update, Delete, Exit");

            String input = scnr.nextLine();

            if (input.equalsIgnoreCase("view data")) {
                System.out.println();
                while (!input.equalsIgnoreCase("back")) {
                    System.out.println("Which data would you like to view?");
                    System.out.println("Tables: \n" + "\t1. Pet \n" + "\t2. MedicalHistory \n" + "\t3. Adopter \n" + "\t4. Adoption \n" + "\t5. Shelter \n" + "\t6. FosterHome \n" + "\t7. FosterAssignment \n");
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
                        System.out.println("----------------------------------------------------------------------");

                        if (input.equalsIgnoreCase("pet") || input.equalsIgnoreCase("1")) {

                            System.out.println("Here is the " + input + " data:");
                            System.out.println("PetID:" + "\t" + "ShelterID:" + "\t" + "Name:" + "\t" + "Species:" + "\t" + "Age:" + "\t" + "Status:" + "\t");
                            while (rs.next()) {
                                System.out.println(rs.getInt("PetID") + "\t"
                                        + rs.getInt("ShelterID") + "\t\t"
                                        + rs.getString("Name") + "\t"
                                        + rs.getString("Species") + "\t\t"
                                        + rs.getInt("Age") + "\t"
                                        + rs.getString("Status") + "\t");
                            }
                        } else if (input.equalsIgnoreCase("medicalhistory") || input.equalsIgnoreCase("2")) {
                            System.out.println("Here is the " + input + " data:");
                            System.out.println("PetID:" + "\t" + "HealthStatus:" + "\t\t" + "VaccinationStatus:" + "\t");
                            while (rs.next()) {
                                System.out.println(rs.getInt("PetID") + "\t"
                                        + rs.getString("HealthStatus") + "\t\t\t"
                                        + rs.getBoolean("VaccinationStatus") + "\t");
                            }
                        } else if (input.equalsIgnoreCase("adopter") || input.equalsIgnoreCase("3")) {
                            System.out.println("Here is the " + input + " data:");
                            System.out.println("AdopterID:" + "\t" + "Name:" + "\t\t" + "Number:" + "\t");
                            while (rs.next()) {
                                System.out.println(rs.getInt("AdopterID") + "\t\t"
                                        + rs.getString("Name") + "\t"
                                        + rs.getString("Number") + "\t");
                            }
                        } else if (input.equalsIgnoreCase("adoption") || input.equalsIgnoreCase("4")) {
                            System.out.println("Here is the " + input + " data:");
                            System.out.println("PetID:" + "\t" + "AdopterID:" + "\t" + "AdoptionDate:" + "\t");
                            while (rs.next()) {
                                System.out.println(rs.getInt("PetID") + "\t"
                                        + rs.getInt("AdopterID") + "\t\t"
                                        + rs.getDate("AdoptionDate") + "\t");
                            }
                        } else if (input.equalsIgnoreCase("shelter") || input.equalsIgnoreCase("5")) {
                            System.out.println("Here is the " + input + " data:");
                            System.out.println("ShelterID:" + "\t" + "Address:" + "\t");
                            while (rs.next()) {
                                System.out.println(rs.getInt("ShelterID") + "\t\t"
                                        + rs.getString("Address") + "\t");
                            }
                        } else if (input.equalsIgnoreCase("fosterhome") || input.equalsIgnoreCase("6")) {
                            System.out.println("Here is the " + input + " data:");
                            System.out.println("FosterID:" + "\t" + "Address:" + "\t\t\t\t" + "Number:" + "\t");
                            while (rs.next()) {
                                System.out.println(rs.getInt("FosterID") + "\t\t"
                                        + rs.getString("Address") + "\t\t"
                                        + rs.getString("Number") + "\t");
                            }
                        } else if (input.equalsIgnoreCase("fosterassignment") || input.equalsIgnoreCase("7")) {
                            System.out.println("Here is the " + input + " data:");
                            System.out.println("FosterID:" + "\t" + "PetID:" + "\t" + "StartDate:" + "\t" + "EndDate:" + "\t");
                            while (rs.next()) {
                                System.out.println(rs.getInt("FosterID") + "\t" +
                                                    rs.getInt("PetID") + "\t" + 
                                                    rs.getDate("StartDate") + "\t" + 
                                                    rs.getDate("EndDate") + "\t");
                            }
                        } else {
                            System.out.println("Sorry, that table does not exist. Please try again:");
                        }

                        System.out.println("----------------------------------------------------------------------");
                        System.out.println();
                        myCon.close();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }

            } else if (input.equalsIgnoreCase("insert")) {
                System.out.println();
                System.out.println("Please enter the following data for insertion: (Values must be in the exact format as the table schema)");
                System.out.print("table values[]: ");
                input = scnr.nextLine();
                String[] inputArr = input.split(" ");
                String table = inputArr[0];

                Connection myCon = DriverManager.getConnection(url, username, password);
                Inserter inserter = new Inserter();
                inserter.insertData(myCon, table, Arrays.copyOfRange(inputArr, 1, inputArr.length));
                scnr.next();
            } else if (input.equalsIgnoreCase("update")) {
                System.out.println();
                while (!input.equalsIgnoreCase("back")) {
                    System.out.println("Please enter the following table to update: ");
                    System.out.println("Tables: \n" + "\t1. Pet \n" + "\t2. MedicalHistory \n" + "\t3. Adopter \n" + "\t4. Adoption \n" + "\t5. Shelter \n" + "\t6. FosterHome \n" + "\t7. FosterAssignment \n");
                    System.out.println("*Type 'back' if you would like to return");
                    input = scnr.nextLine();

                    if (input.equalsIgnoreCase("back")) {
                        break;
                    }

                    if (input.equalsIgnoreCase("pet") || input.equalsIgnoreCase("1")) {
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
                            } else if (update.equalsIgnoreCase("Species")) {
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
                            } else if (update.equalsIgnoreCase("Age")) {
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
                            } else if (update.equalsIgnoreCase("Status")) {
                                System.out.println("Enter new status as written: (Available, Adopted, Fostered, Unavailable)");
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
                                } else {
                                    System.out.println("Inavlid Status: Please try again");
                                }
                            }

                            myCon.close();
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (input.equalsIgnoreCase("medicalhistory") || input.equalsIgnoreCase("2")) {
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
                                System.out.println("Enter new status as written: (Healthy, Sick, Injured, Chronic Condition)");
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
                                } else {
                                    System.out.println("Invalid Status: Please try again");
                                }
                            } else if (update.equalsIgnoreCase("VaccinationStatus")) {
                                System.out.println("Enter new status as written: (true, false)");
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
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (input.equalsIgnoreCase("adopter") || input.equalsIgnoreCase("3")) {
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
                            } else if (update.equalsIgnoreCase("Number")) {
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
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (input.equalsIgnoreCase("adoption") || input.equalsIgnoreCase("4")) {
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
                                System.out.println("Enter new date: (YYYY-MM-DD)");
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
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (input.equalsIgnoreCase("shelter") || input.equalsIgnoreCase("5")) {
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
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (input.equalsIgnoreCase("fosterhome") || input.equalsIgnoreCase("6")) {
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
                            } else if (update.equalsIgnoreCase("Number")) {
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
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (input.equalsIgnoreCase("fosterassignment") || input.equalsIgnoreCase("7")) {
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
                                System.out.println("Enter new date: (YYYY-MM-DD)");
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
                            } else if (update.equalsIgnoreCase("EndDate")) {
                                System.out.println("Enter new date: (YYYY-MM-DD)");
                                update = scnr.nextLine();
                                sql = "UPDATE FosterAssignment SET EndDate = ? WHERE FosterID = ? AND PetID = ?";
                                ps = myCon.prepareStatement(sql);
                                ps.setDate(1, java.sql.Date.valueOf(update));
                                ps.setInt(2, id);
                                ps.setInt(3, id2);
                                int rows = ps.executeUpdate();
                                if (rows > 0) {
                                    System.out.println("Update Successful!");
                                }
                            }
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            } else if (input.equalsIgnoreCase("delete")) {
                System.out.println();
                System.out.println("Please enter the following data for delete: ");
                System.out.print("Table: ");
                input = scnr.nextLine();

                Connection myCon = DriverManager.getConnection(url, username, password);
                String PK = "";
                int affectedRows = 0;

                try {

                    if (input.equalsIgnoreCase("pet") || input.equalsIgnoreCase("medicalhistory")) {
                        System.out.print("Please enter the corresponding PetID that you want to delete: ");
                        PK = scnr.nextLine();
                        String sql = "DELETE FROM " + input + " WHERE PetID = " + PK;
                        PreparedStatement delete = myCon.prepareStatement(sql);
                        affectedRows = delete.executeUpdate();

                    } else if (input.equalsIgnoreCase("adopter")) {
                        System.out.print("Please enter the corresponding AdopterID that you want to delete: ");
                        PK = scnr.nextLine();
                        String sql = "DELETE FROM " + input + " WHERE AdopterID = " + PK;
                        PreparedStatement delete = myCon.prepareStatement(sql);
                        affectedRows = delete.executeUpdate();

                    } else if (input.equalsIgnoreCase("shelter")) {
                        System.out.print("Please enter the corresponding ShelterID that you want to delete: ");
                        PK = scnr.nextLine();
                        String sql = "DELETE FROM " + input + " WHERE ShelterID = " + PK;
                        PreparedStatement delete = myCon.prepareStatement(sql);
                        affectedRows = delete.executeUpdate();

                    } else if (input.equalsIgnoreCase("fosterhome")) {
                        System.out.print("Please enter the corresponding FosterID that you want to delete: ");
                        PK = scnr.nextLine();
                        String sql = "DELETE FROM " + input + " WHERE FosterID = " + PK;
                        PreparedStatement delete = myCon.prepareStatement(sql);
                        affectedRows = delete.executeUpdate();

                    } else if (input.equalsIgnoreCase("adoption")) {
                        System.out.print("Please enter PetID: ");
                        int petID = Integer.parseInt(scnr.nextLine());

                        System.out.print("Please enter AdopterID: ");
                        int adopterID = Integer.parseInt(scnr.nextLine());

                        System.out.print("Please enter AdoptionDate in the format of (yyyy-mm-dd): ");
                        String adoptionDate = scnr.nextLine();

                        String sql = "DELETE FROM adoption WHERE PetID = ? AND AdopterID = ? AND AdoptionDate = ?";
                        PreparedStatement delete = myCon.prepareStatement(sql);
                        delete.setInt(1, petID);
                        delete.setInt(2, adopterID);
                        delete.setDate(3, java.sql.Date.valueOf(adoptionDate));
                        affectedRows = delete.executeUpdate();

                    } else if (input.equalsIgnoreCase("fosterassignment")) {
                        System.out.print("Please enter FosterID: ");
                        int fosterID = Integer.parseInt(scnr.nextLine());

                        System.out.print("Please enter PetID: ");
                        int petID = Integer.parseInt(scnr.nextLine());

                        System.out.print("Please enter StartDate in the format of (yyyy-mm-dd): ");
                        String startDate = scnr.nextLine();

                        String sql = "DELETE FROM fosterassignment WHERE FosterID = ? AND PetID = ? AND StartDate = ?";
                        PreparedStatement delete = myCon.prepareStatement(sql);
                        delete.setInt(1, fosterID);
                        delete.setInt(2, petID);
                        delete.setDate(3, java.sql.Date.valueOf(startDate));
                        affectedRows = delete.executeUpdate();
                    }
                } catch (SQLIntegrityConstraintViolationException e) {
                    System.out.println("Please only delete the shelter only when it is empty");
                }

                if (affectedRows > 0) {
                    System.out.println("Successfully deleted.");
                } else {
                    System.out.println("Didn't find the matching row.");
                }
            } else if (input.equalsIgnoreCase("exit")) {
                System.out.println("Thank you for using our pet adoption system");
                System.out.println("Now closing...");
                scnr.close();
                break;
            }

        }
        scnr.close();
    }
}
