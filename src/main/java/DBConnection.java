import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    public Connection con;
    String url = "jdbc:postgresql://localhost:5432/Projet_GL.sql";
    String BD = "Projet_GL";
    String user = "root";
    String pass = "";
    
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
