package testUnitaire;
import App.DBConnection;
import App.InterfaceAssistantService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import org.junit.Test;

public class TestTableAssistantService {
	
	@Test
	public void getDataTest() {
		DBConnection con = new DBConnection();
		int nbLignes = 0;
		ResultSet rs = null;
		Statement stmt;
		try {
			stmt = con.con.createStatement();
			rs = stmt.executeQuery("SELECT count(*) AS nbLignes FROM tables");
			rs.next();
			nbLignes = rs.getInt("nbLignes");
			rs.close();
			stmt.close();
			con.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		InterfaceAssistantService as = new InterfaceAssistantService();
		String[][] resultat = as.getData();
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
			rs = stmt.executeQuery("SELECT idTable FROM tables WHERE statut='sale'");
			while (rs.next()) {
				String name = rs.getString("idTable");
				list.addItem(name);
			}
			rs.close();
			stmt.close();
			con.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		InterfaceAssistantService as = new InterfaceAssistantService();
		JComboBox<String> resultat = as.getListTableToSet(new JComboBox<String>());
		assertNotNull(resultat);
		assertEquals(resultat.getItemCount(),list.getItemCount());

	}
}
