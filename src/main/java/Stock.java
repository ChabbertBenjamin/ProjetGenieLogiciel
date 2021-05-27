import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class Stock {

	private JTable cart;
	private JPanel controlPanel;
	private JLabel headerLabel;
	private JFrame mainFrame;
	private JFrame buyFrame;
	private DBConnection con;
	GridLayout experimentLayout = new GridLayout(0, 3);

	private JLabel firstNameEmploye, lastNameEmplye, login, password;

	public Stock() {
		this.con = new DBConnection();
		prepareGUI();

	}

	private void prepareGUI() {
		mainFrame = new JFrame("Stock");
		mainFrame.setSize(600, 550);

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

		buy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispose();
				BuyStock();
			}
		});

		mainFrame.add(jp2);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		cart.setEnabled(false);
	}

	public void BuyStock() {
		buyFrame = new JFrame("Acheter");
		buyFrame.setSize(400, 300);
		buyFrame.setLayout(new GridLayout(3, 1));
		buyFrame.getContentPane().setBackground(Color.gray);

		headerLabel = new JLabel("Acheter matière première", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));
		headerLabel.setForeground(Color.white);
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		buyFrame.setLocationRelativeTo(null);
		buyFrame.add(headerLabel);
		buyFrame.add(controlPanel);
		buyFrame.setVisible(true);




		// JComboBox pour sélectionner l'employé à modifier
		JComboBox<String> listMatierePremiere = new JComboBox<String>();

		ResultSet résultats = null;
		String requete = "SELECT nom FROM matierepremiere";
		try {
			Statement stmt = con.con.createStatement();
			résultats = stmt.executeQuery(requete);
			while (résultats.next()) {
				String name = résultats.getString("nom");
				listMatierePremiere.addItem(name);
			}
			résultats.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		JLabel quantite = new JLabel(" Entrer quantité ");
		final JTextField textFieldQuantite = new JTextField();
		textFieldQuantite.setSize(100, 40);

		JButton buy = new JButton("Acheter");
		buy.setSize(40, 50);
		
		buy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nom = (String) listMatierePremiere.getSelectedItem();
				if(!textFieldQuantite.getText().equals("")) {
					try {
						String requete = "SELECT quantite FROM matierepremiere WHERE nom='"+nom+"'";
						ResultSet résultats = null;
	        			Statement stmt = con.con.createStatement();
	        			résultats = stmt.executeQuery(requete);
	        			int quantiteDB=0;
	        			while (résultats.next()) {
	        				quantiteDB = résultats.getInt("quantite");
	        			}
						int intQuantite = Integer.parseInt(textFieldQuantite.getText()) + quantiteDB ;  
						if(Integer.parseInt(textFieldQuantite.getText()) > 0) {
							PreparedStatement pst;
		        			pst = con.mkDataBase().prepareStatement("UPDATE matierepremiere SET quantite="+intQuantite+" WHERE nom='" +nom + "'");
		        			pst.execute();
							JOptionPane.showMessageDialog(null,"Achat effectué");
						}else {

							JOptionPane.showMessageDialog(null,"Impossible d'acheter une quantité negative");
						}
					}catch (Exception e1) {
						System.out.println(e1.getMessage());
					}
				}else {
					JOptionPane.showMessageDialog(null,"Insérer une quantité à acheter");
				}
			}
		});
		

		JButton back = new JButton("Retour à la visualisation");
		back.setSize(40, 50);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buyFrame.dispose();
				new Stock();
			}
		});

		JPanel jp = new JPanel(null);
		jp.add(listMatierePremiere);
		jp.add(quantite);
		jp.add(textFieldQuantite);
		jp.add(buy);
		jp.setSize(500, 500);
		jp.setLayout(experimentLayout);
		buyFrame.add(back);
		controlPanel.add(jp);

	}

	public String[][] getData() {
		ResultSet rs = null;
		int nbLignes = 0;
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
			int compteur = 0;
			while (rs.next()) {
				donnees[compteur][0] = rs.getString("nom");
				donnees[compteur][1] = rs.getString("quantite");
				compteur++;
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return donnees;
	}

}