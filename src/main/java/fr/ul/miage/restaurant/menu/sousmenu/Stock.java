package fr.ul.miage.restaurant.menu.sousmenu;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

import fr.ul.miage.restaurant.bdd.DBConnection;

public class Stock {

	private JTable cart;
	private JPanel controlPanel;
	private JLabel headerLabel;
	private JFrame mainFrame, buyFrame, manageFrame;

	private DBConnection con;
	GridLayout experimentLayout = new GridLayout(0, 3);

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

		JButton buy = new JButton("Acheter des matiéres premiéres");
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
		buyFrame.setSize(600, 400);
		buyFrame.setLayout(new GridLayout(3, 1));
		buyFrame.getContentPane().setBackground(Color.gray);

		headerLabel = new JLabel("Acheter matiére premiére", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));
		headerLabel.setForeground(Color.white);
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		buyFrame.setLocationRelativeTo(null);
		buyFrame.add(headerLabel);
		buyFrame.add(controlPanel);
		buyFrame.setVisible(true);

		// JComboBox pour sélectionner l'employé é modifier
		JComboBox<String> list = new JComboBox<String>();
		JComboBox<String> listMatierePremiere = getListMatierePremiere(list);

		JLabel quantite = new JLabel(" Entrer quantité ");
		final JTextField textFieldQuantite = new JTextField();
		textFieldQuantite.setSize(100, 40);

		JButton buy = new JButton("Acheter");
		buy.setSize(40, 50);

		buy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String nom = (String) listMatierePremiere.getSelectedItem();
				if (!textFieldQuantite.getText().equals("")) {
					try {
						String requete = "SELECT quantite FROM matierepremiere WHERE nom='" + nom + "'";
						ResultSet rs = null;
						Statement stmt = con.con.createStatement();
						rs = stmt.executeQuery(requete);
						int quantiteDB = 0;
						while (rs.next()) {
							quantiteDB = rs.getInt("quantite");
						}
						int intQuantite = Integer.parseInt(textFieldQuantite.getText()) + quantiteDB;
						if (Integer.parseInt(textFieldQuantite.getText()) > 0) {
							PreparedStatement pst;
							pst = con.mkDataBase().prepareStatement(
									"UPDATE matierepremiere SET quantite=" + intQuantite + " WHERE nom='" + nom + "'");
							pst.execute();
							JOptionPane.showMessageDialog(null, "Achat effectué");
						} else {

							JOptionPane.showMessageDialog(null, "Impossible d'acheter une quantité negative");
						}
					} catch (Exception e1) {
						System.out.println(e1.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Insérer une quantité é acheter");
				}
			}
		});

		JButton newMatierePremiere = new JButton("Gérer matiére premiére");
		newMatierePremiere.setSize(40, 50);
		newMatierePremiere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buyFrame.dispose();
				newMatierePremiere();
			}
		});

		JButton back = new JButton("Retour é la visualisation");
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
		jp.add(newMatierePremiere);
		jp.setSize(500, 500);
		jp.setLayout(experimentLayout);
		buyFrame.add(back);
		controlPanel.add(jp);
		
		buyFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				new Stock();
			}
		});

	}

	public JComboBox<String> getListMatierePremiere(JComboBox<String> list) {

		ResultSet rs = null;
		String requete = "SELECT nom FROM matierepremiere";
		try {
			Statement stmt = con.con.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				String name = rs.getString("nom");
				list.addItem(name);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return list;
	}

	public void newMatierePremiere() {
		manageFrame = new JFrame("gérer");
		manageFrame.setSize(600, 400);
		manageFrame.setLayout(new GridLayout(3, 1));
		manageFrame.getContentPane().setBackground(Color.gray);

		headerLabel = new JLabel("Créer/Modifier matiére premiére", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));
		headerLabel.setForeground(Color.white);
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		manageFrame.setLocationRelativeTo(null);
		manageFrame.add(headerLabel);
		manageFrame.add(controlPanel);
		manageFrame.setVisible(true);

		JLabel nom = new JLabel(" Entrer nom ");
		final JTextField nomMatierePremiere = new JTextField();
		nomMatierePremiere.setSize(100, 40);
		
		JComboBox<String> list = new JComboBox<String>();
		list.addItem("");
		JComboBox<String> listMatierePremiere = getListMatierePremiere(list);

		JButton modif = new JButton("Créer");
		modif.setSize(40, 50);
		modif.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if (listMatierePremiere.getSelectedItem().toString().equals("")) {
					// Requete de création
					PreparedStatement pst;
					try {
						pst = con.mkDataBase().prepareStatement("INSERT INTO matierepremiere (nom, quantite) values (?,0)");
						pst.setString(1, nomMatierePremiere.getText());
						pst.execute();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(null, "Création effectué");
					
					
				} else {
					// Requete de modification
					PreparedStatement pst;
					try {
						pst = con.mkDataBase().prepareStatement("UPDATE matierepremiere SET nom='"+nomMatierePremiere.getText()+"' WHERE nom='"+listMatierePremiere.getSelectedItem().toString()+"'");
						pst.execute();
						JOptionPane.showMessageDialog(null, "Modification effectué");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
				manageFrame.dispose();
				BuyStock();
				
				
			}
		});

		

		// Lorsqu'on change la selection de la matiére premiére
		listMatierePremiere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Si aucune matiére premiére n'est sélectionné
				if (listMatierePremiere.getSelectedItem().toString().equals("")) {
					nomMatierePremiere.setText("");
					modif.setText("Créer");
				} else {
					nomMatierePremiere.setText((String) listMatierePremiere.getSelectedItem());
					modif.setText("Modifier");
				}
			}
		});

		JPanel jp = new JPanel(null);
		jp.add(listMatierePremiere);
		jp.add(nom);
		jp.add(nomMatierePremiere);
		jp.add(modif);
		jp.setSize(500, 500);
		jp.setLayout(experimentLayout);
		controlPanel.add(jp);
		
		manageFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				BuyStock();
			}
		});
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