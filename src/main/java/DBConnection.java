import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    public Connection con;
    //String url ="jdbc:mysql://localhost:3306/GestionRestaurant";
    //String url = "jdbc:postgresql://localhost:5432/GestionRestaurant";
    String url = "jdbc:postgresql://localhost:5432/Projet_GL.sql";
    //String BD = "GestionRestaurant";
    String BD = "Projet_GL";
    String user = "root";
    String pass = "";
    
    public Connection mkDataBase() throws SQLException{
        try {
          //�tape 1: charger la classe de driver
        	//Class.forName("com.mysql.jdbc.Driver");
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, pass);
           
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
     
        }
        return con;
    }
 
}
