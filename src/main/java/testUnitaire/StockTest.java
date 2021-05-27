package testUnitaire;
import App.DBConnection;
import App.Stock;
import junit.framework.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class StockTest {
	
	@Test
	public void getDataTest() {
		DBConnection con = new DBConnection();
		int nbLignes = 0;
		ResultSet rs = null;
		Statement stmt;
		try {
			stmt = con.con.createStatement();
			rs = stmt.executeQuery("SELECT count(*) AS nbLignes FROM matierepremiere");
			rs.next();
			nbLignes = rs.getInt("nbLignes");
			rs.close();
			stmt.close();
			con.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Stock s = new Stock();
		String[][] resultat = s.getData();
		int test = resultat.length;
		assertEquals(test,nbLignes);
	}
	
	
	@Test
	public void getListMatierePremiereTest() {
		DBConnection con = new DBConnection();
		ResultSet rs = null;
		Statement stmt;
		JComboBox<String> list = new JComboBox<String>();
		try {
			stmt = con.con.createStatement();
			rs = stmt.executeQuery("SELECT nom FROM matierepremiere");
			while (rs.next()) {
				String name = rs.getString("nom");
				list.addItem(name);
			}
			rs.close();
			stmt.close();
			con.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Stock s = new Stock();
		JComboBox<String> resultat = s.getListMatierePremiere(new JComboBox<String>());
		assertNotNull(resultat);
		assertEquals(resultat.getItemCount(),list.getItemCount());

	}
}
