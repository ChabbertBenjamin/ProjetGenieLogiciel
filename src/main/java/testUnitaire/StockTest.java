package testUnitaire;
import App.DBConnection;
import App.Stock;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class StockTest {
	
	@Test
	public void getDateTest() {
		DBConnection con = new DBConnection();
		int nbLignes = 0;
		ResultSet rs = null;
		Statement stmt;
		try {
			stmt = con.con.createStatement();
			rs = stmt.executeQuery("SELECT count(*) AS nbLignes FROM matierepremiere");
			rs.next();
			nbLignes = rs.getInt("nbLignes");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Stock s = new Stock();
		String[][] resultat = s.getData();
		int test = resultat.length;
		assertEquals(test,nbLignes);
	}
}
