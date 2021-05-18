import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utile {

	public static void getSQLServerConnection() throws Exception
  	{
		try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost/ProjetGenieLogiciel", "postgres", "root")) {

            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
  }
	 
	 
	 
}
