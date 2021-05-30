package fr.ul.miage.restaurant.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import fr.ul.miage.restaurant.bdd.DBConnection;
import fr.ul.miage.restaurant.models.Commande;
import fr.ul.miage.restaurant.models.Plat;
import fr.ul.miage.restaurant.models.Table;

public class InterfaceServeur {

	private JFrame mainFrame, detailFrame,commandeFrame;
	private JPanel controlPanel;
	private int idEmploye;
	

	public InterfaceServeur(int idEmploye) {
		this.idEmploye = idEmploye;
		mainFrame = new JFrame("Serveur");
		init();
		launch();
		mainFrame.setVisible(true);
	}

	private void init() {
		// Création des composants
		mainFrame.setBounds(100, 100, 700, 400);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.getContentPane().setBackground(Color.GRAY);
		JLabel headerLabel = new JLabel("Serveur", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));
		headerLabel.setForeground(Color.white);
		controlPanel = new JPanel(new java.awt.GridBagLayout());
		// Ajout des composant
		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.setLocationRelativeTo(null);
		// On ferme la connection à la BDD lorsqu'on ferme la fenêtre
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				try {
					DBConnection.con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					System.exit(0);
				}
			}
		});
	}

	public void launch() {
		
		GridBagConstraints labCnst = new GridBagConstraints();
		labCnst.fill = GridBagConstraints.NONE;
	    labCnst.insets = new Insets(3, 3, 3, 3);
	    labCnst.anchor = GridBagConstraints.LINE_START;
	    int i = 0;
	    int j = 0;
		try {
			Statement stmt = DBConnection.con.createStatement();
			ArrayList<Table> listeTable = new ArrayList<Table>();
			ArrayList<JButton> listButton = new ArrayList<JButton>();
			// On récupere les tables correspondant à l'employé
			ResultSet rs = stmt.executeQuery(
					"SELECT idtable, statut, nbcouverts, etage, idemploye from tables WHERE idemploye =" + idEmploye);
			while (rs.next()) {
				listeTable.add(new Table(rs.getInt("idtable"), rs.getString("statut"), rs.getInt("nbcouverts"),
						rs.getInt("etage"), rs.getInt("idemploye")));
				JButton buttonTable = new JButton("Table numéro : " + rs.getInt("idtable"));
				if (rs.getString("statut").equals("propre")) {
					buttonTable.setBackground(Color.GREEN);
				}
				if (rs.getString("statut").equals("occupe")) {
					buttonTable.setBackground(Color.YELLOW);
				}
				if (rs.getString("statut").equals("sale")) {
					buttonTable.setBackground(Color.RED);
				}
				if (rs.getString("statut").equals("reserve")) {
					buttonTable.setBackground(Color.ORANGE);
				}
				buttonTable.setBorder(BorderFactory.createBevelBorder(0));
				listButton.add(buttonTable);
				labCnst.gridy  = j;
				labCnst.gridx  = i;
				controlPanel.add(buttonTable,labCnst);
				i++;
				if(i==5) {
					j++;
					i=0;
				}
				buttonTable.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						detailTable(listeTable.get(retourIndexButton(listButton, buttonTable)));
					}
				});
			
				
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void detailTable(Table table) {
		detailFrame = new JFrame("Détails de la table");
		detailFrame.setSize(700, 600);
		detailFrame.setLayout(new GridLayout(3, 1));
		detailFrame.getContentPane().setBackground(Color.gray);

		JLabel headerLabel = new JLabel("Détails de la table", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));

		JPanel controlPanel = new JPanel(new java.awt.GridBagLayout());
		GridBagConstraints labCnst = new GridBagConstraints();
		detailFrame.add(headerLabel);
		detailFrame.add(controlPanel);
		labCnst.fill = GridBagConstraints.NONE;
	    labCnst.insets = new Insets(5, 5, 5, 5);
	    labCnst.anchor = GridBagConstraints.FIRST_LINE_START;

		JLabel lidTable = new JLabel("Numéro : " + table.getIdtable());
		JLabel lstatut = new JLabel("Statut : " + table.getStatut());
		JLabel lnbcouverts = new JLabel("Nombre de couverts : " + table.getNbcouverts());
		JLabel letage = new JLabel("Etage : " + table.getEtage());
		JLabel lidEmploye = new JLabel("Employé(e) affecté(e) : " + table.getIdemploye());
		JLabel lNettoyer = new JLabel("La table a besoin d'être nettoyée");
		JButton btnRetour = new JButton("Retour");
		JButton btnVoirRepas = new JButton("Voir le repas en cours");
		JButton btnAjout = new JButton("Saisir une commande");
		JButton btnPayerAddition = new JButton("Faire payer l'addition");
		JButton btnInstallerClient = new JButton("Installer des clients");
		labCnst.gridx  = 0;
	    labCnst.gridy = 0;
		controlPanel.add(lidTable,labCnst);
		labCnst.gridx  = 0;
	    labCnst.gridy = 1;
		controlPanel.add(lstatut,labCnst);
		labCnst.gridx  = 0;
	    labCnst.gridy = 2;
		controlPanel.add(lnbcouverts,labCnst);
		labCnst.gridx  = 0;
	    labCnst.gridy = 3;
		controlPanel.add(letage,labCnst);
		labCnst.gridx  = 0;
	    labCnst.gridy = 4;
		controlPanel.add(lidEmploye,labCnst);
		labCnst.gridx  = 0;
	    labCnst.gridy = 5;
		controlPanel.add(btnRetour,labCnst);
		labCnst.gridx  = 2;
	    labCnst.gridy = 5;
		controlPanel.add(btnAjout,labCnst);
		labCnst.gridx  = 3;
	    labCnst.gridy = 5;
	    controlPanel.add(btnVoirRepas,labCnst);
	    labCnst.gridx  = 2;
	    labCnst.gridy = 5;
		controlPanel.add(btnInstallerClient,labCnst);
		labCnst.gridx  = 4;
	    labCnst.gridy = 5;
		controlPanel.add(btnPayerAddition,labCnst);
		labCnst.gridx  = 2;
	    labCnst.gridy = 5;
		controlPanel.add(lNettoyer,labCnst);
		btnVoirRepas.setVisible(false);
		lNettoyer.setVisible(false);
		btnPayerAddition.setVisible(false);
		if (table.getStatut().equals("occupe")) {
			btnAjout.setVisible(true);
			btnVoirRepas.setVisible(true);
			btnPayerAddition.setVisible(true);
			btnInstallerClient.setVisible(false);
		} else if(table.getStatut().equals("sale")){
			btnAjout.setVisible(false);
			btnInstallerClient.setVisible(false);
			lNettoyer.setVisible(true);
		}else {
			btnAjout.setVisible(false);
			btnInstallerClient.setVisible(true);
		}
		detailFrame.add(headerLabel);
		detailFrame.add(controlPanel);
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				detailFrame.dispose();
			}
		});
		btnVoirRepas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				voirRepas(table);
			}
		});
		btnAjout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saisirCommande(table);
			}
		});
		btnInstallerClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				installerClient(table);
			}
		});
		btnPayerAddition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				payerAddition(table);
			}
		});
		detailFrame.setLocationRelativeTo(null);
		detailFrame.setVisible(true);
	}
	public void voirRepas(Table table) {
		JFrame voirRepasFrame = new JFrame("Repas en cours");
		voirRepasFrame.setSize(700, 600);
		voirRepasFrame.setLayout(new GridLayout(0, 1));
		voirRepasFrame.getContentPane().setBackground(Color.gray);
		JLabel headerLabel = new JLabel("Repas en cours", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));
		JPanel controlPanel = new JPanel();
		JPanel btnPanel = new JPanel();
		JButton btnRetour = new JButton("Retour");
		btnPanel.add(btnRetour);
		voirRepasFrame.add(headerLabel);
		voirRepasFrame.add(controlPanel);
		voirRepasFrame.add(btnPanel);
		
		
	


		String[] columnNames = { "Nom ", "Prix", "Date", "Menu enfant","Addition" };
		String[][] donnees = getDataRepas(table);
		JTable cart = new JTable(donnees, columnNames);
		
		cart.setSize(300, 450);
		cart.setAutoCreateRowSorter(true);
		cart.setEnabled(false);
		controlPanel.add(new JScrollPane(cart, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		voirRepasFrame.add(btnRetour);
		voirRepasFrame.setLocationRelativeTo(null);
		voirRepasFrame.setVisible(true);
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				voirRepasFrame.dispose();
			}
		});
		
	}
	public String[][] getDataRepas(Table table){
		int compteur = 0;
		String[][] donnees = new String[10][5];
		try {
			Statement stmt = DBConnection.con.createStatement();
			ResultSet rs1 = stmt.executeQuery("select MAX(idrepasclient) as id FROM repasclient where idtable = "+table.getIdtable());
			while(rs1.next()) {
			ResultSet rs2 = stmt.executeQuery(
					"SELECT plat.nom as nom,\n"
					+ "plat.prix as prix,\n"
					+ "commande.dateheurecommande as date,\n"
					+ "commande.menuenfant as enfant, repasclient.addition as addition \n"
					+ "FROM repasclient,plat,commande \n"
					+ "WHERE plat.idplat = commande.idplat \n"
					+ "AND commande.idrepasclient = repasclient.idrepasclient\n"
					+ "AND repasclient.idrepasclient ="+rs1.getInt("id"));
			
			while(rs2.next()) {
				donnees[compteur][0] = rs2.getString("nom");
				donnees[compteur][1] = rs2.getString("prix");
				donnees[compteur][2] = rs2.getString("date");
				donnees[compteur][3] = rs2.getString("enfant");
				donnees[compteur][4] = rs2.getString("addition");
				compteur++;
			}
			rs2.close();
			}
			rs1.close();
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return donnees;
	}
	public void payerAddition(Table table) {
		
		
		ResultSet rs;
		try {
			Statement stmt = DBConnection.con.createStatement();
			rs = stmt.executeQuery(
					"SELECT idrepasclient,idtable, datedebut, datefin,addition FROM repasclient where idtable =" + table.getIdtable());
			while (rs.next()) {
				
				if(rs.getDate("datefin")==null) {
					 Statement stmt2;
			         stmt2 = DBConnection.con.createStatement();
					 stmt2.executeUpdate("UPDATE repasclient SET datefin = LOCALTIMESTAMP WHERE idrepasclient =" +rs.getInt("idrepasclient"));
					 stmt2.executeUpdate("UPDATE tables SET statut = 'sale' WHERE idtable =" +table.getIdtable());
					 JOptionPane.showMessageDialog(null, "L'addition a été payé");
					 detailFrame.dispose();
					 rs.close();
					 mainFrame.dispose();
					 new InterfaceServeur(table.getIdemploye());
				}	
		}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
	}

	public void saisirCommande(Table table) {
		
		JComboBox<Plat> listPlat = new JComboBox<Plat>();
		try {
			Statement stmt = DBConnection.con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT idplat, idcategorie, prix, nom FROM plat");
			while (rs.next()) {
			listPlat.addItem(new Plat(rs.getInt("idPlat"),rs.getInt("idCategorie"),rs.getInt("prix"),rs.getString("nom")));
	
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		commandeFrame = new JFrame("Saisir commande");
		commandeFrame.setSize(700, 600);
		commandeFrame.setLayout(new GridLayout(3, 1));
		commandeFrame.getContentPane().setBackground(Color.gray);
		JLabel headerLabel = new JLabel("Saisir une commande", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));
		JPanel controlPanel = new JPanel(new java.awt.GridBagLayout());
		GridBagConstraints labCnst = new GridBagConstraints();
		labCnst.fill = GridBagConstraints.NONE;
	    labCnst.insets = new Insets(3, 3, 3, 3);
	    labCnst.anchor = GridBagConstraints.LINE_START;
		commandeFrame.add(headerLabel);
		commandeFrame.add(controlPanel);
		commandeFrame.setVisible(true);
		JLabel lidTable = new JLabel("Numéro de la table : "+table.getIdtable());
		JLabel lMenuEnfant = new JLabel("Menu Enfant : ");
		JCheckBox cbMenuEnfant = new JCheckBox();
		
		
		JButton btnAjouter = new JButton("Ajouter le plat");
		JButton btnRetour = new JButton("Retour");
		labCnst.gridx  = 0;
	    labCnst.gridy = 0;
		controlPanel.add(lidTable,labCnst);
		labCnst.gridx  = 0;
	    labCnst.gridy = 1;
		controlPanel.add(lMenuEnfant,labCnst);
		labCnst.gridx  = 1;
	    labCnst.gridy = 1;
		controlPanel.add(cbMenuEnfant,labCnst);
		labCnst.gridx  = 0;
	    labCnst.gridy = 2;
		controlPanel.add(listPlat,labCnst);
		labCnst.gridx  = 0;
	    labCnst.gridy = 3;
	    controlPanel.add(btnAjouter,labCnst);
	    labCnst.gridx  = 1;
	    labCnst.gridy = 3;
	    controlPanel.add(btnRetour,labCnst);
	    btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				commandeFrame.dispose();
			}
		});
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			

				try {
					Plat platSelect = (Plat) listPlat.getSelectedItem();
					Statement stmt = DBConnection.con.createStatement();
					Statement stmt2 = DBConnection.con.createStatement();
					
					ResultSet rs = stmt.executeQuery(
							"SELECT idrepasclient,idtable, datedebut, datefin,addition FROM repasclient where idtable =" + table.getIdtable());
					while (rs.next()) {
						
						if(rs.getDate("datefin")==null) {
							
							Commande commande = new Commande(platSelect.getIdplat(),"saisie",cbMenuEnfant.isSelected(),java.time.LocalDate.now(),rs.getInt("idrepasclient"));
							ajoutCommande(commande,platSelect.getPrix());
							rs.close();
						}	
						}
						PreparedStatement stmt1 = DBConnection.con.prepareStatement(
									"INSERT into repasclient(idtable,datedebut,addition) values (?,LOCALTIMESTAMP,?)");
						stmt1.setInt(1, table.getIdtable());
						stmt1.setInt(2, 0);
						stmt1.execute();
						ResultSet rs1 = stmt2.executeQuery("select MAX(idrepasclient) as id FROM repasclient");
						while (rs1.next()) {
						Commande commande = new Commande(platSelect.getIdplat(),"saisie",cbMenuEnfant.isSelected(),java.time.LocalDate.now(),rs1.getInt("id"));
						rs1.close();
						ajoutCommande(commande,platSelect.getPrix());			
						
						
						}
					
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
					
			
		});

		
		commandeFrame.setLocationRelativeTo(null);
		commandeFrame.setVisible(true);
	}
	public void ajoutCommande(Commande commande, int prix) {
		
		try {
		Statement stmt2;
		PreparedStatement stmt = DBConnection.con.prepareStatement(
					"INSERT into commande(idplat,statut,menuenfant,dateheurecommande,idrepasclient) values (?,?,?,LOCALTIMESTAMP,?)");
		stmt.setInt(1, commande.getIdplat());
		stmt.setString(2, commande.getStatut());
		stmt.setBoolean(3, commande.isMenuenfant());
		stmt.setInt(4, commande.getIdrepasclient());
		stmt.execute();
		stmt2 = DBConnection.con.createStatement();
		stmt2.executeUpdate("UPDATE repasclient SET addition = addition + "+prix + "WHERE idrepasclient =" +commande.getIdrepasclient());
		JOptionPane.showMessageDialog(null, "Création effectué !");
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	public void installerClient(Table table) {
		Statement stmt;
		try {
			stmt = DBConnection.con.createStatement();
			stmt.executeUpdate("UPDATE tables SET statut = 'occupe' WHERE idtable =" + table.getIdtable());
			 JOptionPane.showMessageDialog(null, "Les clients ont été installés");
			mainFrame.dispose();
			detailFrame.dispose();
			new InterfaceServeur(table.getIdemploye());
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
	}

	// Retourne l'index du boutton pour la table destiné.
	private int retourIndexButton(ArrayList<JButton> buttonList, JButton button) {
		for (int i = 0; i < buttonList.size(); i++) {
			if (buttonList.get(i).equals(button)) {
				return i;
			}
		}
		return 0;
	}
	
}
