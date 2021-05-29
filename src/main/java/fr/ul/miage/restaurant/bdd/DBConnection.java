package fr.ul.miage.restaurant.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
	private static String url = "jdbc:postgresql://100.74.37.114/GL_Projet_G15";
	private static String user = "m1user1_10";
	private static String pass = "m1user1_10";

	public static Connection con;

	public static void connection() {
		try {
			// étape 1: charger la classe de driver
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
