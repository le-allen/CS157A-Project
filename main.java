import java.sql.*;

public class main {
    
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/cs157a";
        String username = "root";
        String password = "yourpassword";
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection myCon = DriverManager.getConnection(url, username, password);

        
    }
}
