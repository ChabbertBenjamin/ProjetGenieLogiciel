import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    public Connection con;
    String url = "jdbc:postgresql://100.74.37.114/GL_Projet_G15";
    String user = "m1user1_10";
    String pass = "m1user1_10";
  
    public DBConnection() {
    	
    	try {
			mkDataBase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public Connection mkDataBase() throws SQLException{
        try {
          //étape 1: charger la classe de driver
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, pass);
           
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
 
}
