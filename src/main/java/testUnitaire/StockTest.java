package testUnitaire;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JComboBox;
import org.junit.Test;
import fr.ul.miage.restaurant.bdd.DBConnection;
import fr.ul.miage.restaurant.menu.InterfaceDirecteur;
import fr.ul.miage.restaurant.models.Stock;


public class StockTest {
	
	@Test
	public void getDataTest() {
		DBConnection.connection();
		int nbLignes = 0;
		ResultSet rs = null;
		Statement stmt;
		try {
			stmt = DBConnection.con.createStatement();
			rs = stmt.executeQuery("SELECT count(*) AS nbLignes FROM matierepremiere");
			rs.next();
			nbLignes = rs.getInt("nbLignes");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ArrayList<Stock> resultat = new InterfaceDirecteur().getData();
		int test = resultat.size();
		
		assertEquals(test,nbLignes);
	}
	
	
	@Test
	public void getListMatierePremiereTest() {
		DBConnection.connection();
		ResultSet rs = null;
		Statement stmt;
		JComboBox<String> list = new JComboBox<String>();
		try {
			stmt = DBConnection.con.createStatement();
			rs = stmt.executeQuery("SELECT nom FROM matierepremiere");
			while (rs.next()) {
				String name = rs.getString("nom");
				list.addItem(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JComboBox<String> resultat = new InterfaceDirecteur().getListMatierePremiere(new JComboBox<String>());
		assertNotNull(resultat);
		assertEquals(resultat.getItemCount(),list.getItemCount());

	}
}
