import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.*;

public class Stock {
	JTextField idplat, prix;

	JTable cart;

	// ArrayList<MatierePremiere> listMatierePremiere = new ArrayList<>();

	private JFrame mainFrame;
	private DBConnection con;

	public Stock() {
		this.con = new DBConnection();
		prepareGUI();

	}

	private void prepareGUI() {
		mainFrame = new JFrame("Stock");
		mainFrame.setSize(700, 600);
		mainFrame.getContentPane().setBackground(Color.gray);

		JPanel jp2 = new JPanel();
		jp2.setSize(400, 400);

		String[] columnNames = { "Nom ", "Quantité" };
		String[][] donnees = getData();
		cart = new JTable(donnees, columnNames);
		cart.setSize(300, 450);
		jp2.setLayout(new FlowLayout());

		jp2.add(new JScrollPane(cart, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

		JButton buy = new JButton("Acheter des matières premières");
		buy.setSize(40, 50);
		jp2.add(buy);

		mainFrame.add(jp2);
		mainFrame.setSize(600, 550);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		cart.setEnabled(false);
	}

	public void showButtonDemo() {
		
	}
	
	public String[][] getData(){
		ResultSet rs = null;
		int nbLignes=0;
		try {
			Statement stmt = con.con.createStatement();
			rs = stmt.executeQuery("SELECT count(*) AS nbLignes FROM matierepremiere ");
	    	rs.next();
	    	nbLignes = rs.getInt("nbLignes");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[][] donnees = new String[nbLignes][2];
		
		
		String requete = "SELECT * FROM matierepremiere";
		try {
			Statement stmt = con.con.createStatement();
			rs = stmt.executeQuery(requete);
			int compteur =0;
			while (rs.next()) {
				donnees[compteur][0] = rs.getString("nom");
				donnees[compteur][1] = rs.getString("quantite");
				compteur++;
			}
			rs.close();
			stmt.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return donnees;
	}

}