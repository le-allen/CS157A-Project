import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class Inserter {
    public void insertData(Connection conn, String table, String[] values) {
        String fields = "";
        try {
            if (table.equalsIgnoreCase("pet")) {
                String sql = "INSERT INTO Pet (PetID, ShelterID, Name, Species, Age, Status) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                fields = "PetID, ShelterID, Name, Species, Age, Status";

                ps.setInt(1, Integer.parseInt(values[0]));
                ps.setInt(2, Integer.parseInt(values[1]));
                ps.setString(3, values[2]);
                ps.setString(4, values[3]);
                ps.setInt(5, Integer.parseInt(values[4]));
                ps.setString(6, values[5]);

                int rowsAffected = ps.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected. " + Arrays.toString(values));
            } else if(table.equalsIgnoreCase("medical mistory")) {
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
                fields = "AdopterID, Name, Number";

                ps.setInt(1, Integer.parseInt(values[0]));
                ps.setString(2, values[1]);
                ps.setString(3, values[2]);

                int rowsAffected = ps.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected. " + Arrays.toString(values));
            } else if (table.equalsIgnoreCase("adoption")) {
                String sql = "INSERT INTO Adoption (PetID, AdopterID, AdoptionDate) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                fields = "PetID, AdopterID, AdoptionDate";

                ps.setInt(1, Integer.parseInt(values[0]));
                ps.setInt(2, Integer.parseInt(values[1]));
                ps.setDate(3, Date.valueOf(values[2]));

                int rowsAffected = ps.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected. " + Arrays.toString(values));
            } else if (table.equalsIgnoreCase("shelter")) {
                String sql = "INSERT INTO Shelter (ShelterID, Address) VALUES (?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                fields = "ShelterID, Address";

                ps.setInt(1, Integer.parseInt(values[0]));
                ps.setString(2, values[1]);

                int rowsAffected = ps.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected. " + Arrays.toString(values));
            } else if (table.equalsIgnoreCase("foster home")) {
                String sql = "INSERT INTO FosterHome (FosterID, Address, Number) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                fields = "FosterID, Address, Number";

                ps.setInt(1, Integer.parseInt(values[0]));
                ps.setString(2, values[1]);
                ps.setString(3, values[2]);

                int rowsAffected = ps.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected. " + Arrays.toString(values));
            } else if (table.equalsIgnoreCase("foster assignment")) {
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
            System.out.println("Try checking formating. Expected fields for " + table + "(in exact order): " + fields);
        }
    }
}
