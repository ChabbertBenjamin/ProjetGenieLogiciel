package fr.ul.miage.restaurant.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	private JFrame mainFrame, detailFrame, commandeFrame;
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
			final ArrayList<Table> listeTable = new ArrayList<Table>();
			final ArrayList<JButton> listButton = new ArrayList<JButton>();
			// On récupere les tables correspondant à l'employé
			ResultSet rs = stmt.executeQuery(
					"SELECT idtable, statut, nbcouverts, etage, idemploye from tables WHERE idemploye =" + idEmploye);
			while (rs.next()) {
				listeTable.add(new Table(rs.getInt("idtable"), rs.getString("statut"), rs.getInt("nbcouverts"),
						rs.getInt("etage"), rs.getInt("idemploye")));
				final JButton buttonTable = new JButton("Table numéro : " + rs.getInt("idtable"));
				// On associe a chaque bouton une couleur en fonction du statut de la table
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
				labCnst.gridy = j;
				labCnst.gridx = i;
				// On ajoute chaque bouton a notre panel
				controlPanel.add(buttonTable, labCnst);
				i++;
				if (i == 5) {
					j++;
					i = 0;
				}
				buttonTable.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						detailTable(listeTable.get(retourIndexButton(listButton, buttonTable)));
					}
				});

			}
			rs.close();
			stmt.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	// Fonction permettant d'afficher les détails d'une table selectionner
	public void detailTable(final Table table) {
		// Création des composants
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
		// Ajout des composants
		labCnst.gridx = 0;
		labCnst.gridy = 0;
		controlPanel.add(lidTable, labCnst);
		labCnst.gridx = 0;
		labCnst.gridy = 1;
		controlPanel.add(lstatut, labCnst);
		labCnst.gridx = 0;
		labCnst.gridy = 2;
		controlPanel.add(lnbcouverts, labCnst);
		labCnst.gridx = 0;
		labCnst.gridy = 3;
		controlPanel.add(letage, labCnst);
		labCnst.gridx = 0;
		labCnst.gridy = 4;
		controlPanel.add(lidEmploye, labCnst);
		labCnst.gridx = 0;
		labCnst.gridy = 5;
		controlPanel.add(btnRetour, labCnst);
		labCnst.gridx = 2;
		labCnst.gridy = 5;
		controlPanel.add(btnAjout, labCnst);
		labCnst.gridx = 3;
		labCnst.gridy = 5;
		controlPanel.add(btnVoirRepas, labCnst);
		labCnst.gridx = 2;
		labCnst.gridy = 5;
		controlPanel.add(btnInstallerClient, labCnst);
		labCnst.gridx = 4;
		labCnst.gridy = 5;
		controlPanel.add(btnPayerAddition, labCnst);
		labCnst.gridx = 2;
		labCnst.gridy = 5;
		controlPanel.add(lNettoyer, labCnst);
		// On rends visible les boutons adequat au statut de la table
		btnVoirRepas.setVisible(false);
		lNettoyer.setVisible(false);
		btnPayerAddition.setVisible(false);
		if (table.getStatut().equals("occupe")) {
			btnAjout.setVisible(true);
			btnVoirRepas.setVisible(true);
			btnPayerAddition.setVisible(true);
			btnInstallerClient.setVisible(false);
		} else if (table.getStatut().equals("sale")) {
			btnAjout.setVisible(false);
			btnInstallerClient.setVisible(false);
			lNettoyer.setVisible(true);
		} else {
			btnAjout.setVisible(false);
			btnInstallerClient.setVisible(true);
		}
		detailFrame.add(headerLabel);
		detailFrame.add(controlPanel);
		// Fonctions attribuées au boutons
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

	// Fonction qui permet d'afficher les commandes pour un repas d'un client
	public void voirRepas(Table table) {
		// Création des composants
		final JFrame voirRepasFrame = new JFrame("Repas en cours");
		voirRepasFrame.setSize(700, 600);
		voirRepasFrame.setLayout(new GridLayout(0, 1));
		voirRepasFrame.getContentPane().setBackground(Color.gray);
		JLabel headerLabel = new JLabel("Repas en cours", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));
		JPanel controlPanel = new JPanel();
		JPanel btnPanel = new JPanel();
		JButton btnRetour = new JButton("Retour");
		btnPanel.add(btnRetour);
		// Ajout des composants
		voirRepasFrame.add(headerLabel);
		voirRepasFrame.add(controlPanel);
		voirRepasFrame.add(btnPanel);

		// Création du tableau contenant les commandes
		String[] columnNames = { "Nom ", "Prix", "Date", "Menu enfant", "Addition" };
		String[][] donnees = getDataRepas(table);
		JTable cart = new JTable(donnees, columnNames);
		// Ajout des composants
		cart.setSize(300, 450);
		cart.setAutoCreateRowSorter(true);
		cart.setEnabled(false);
		controlPanel.add(new JScrollPane(cart, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		voirRepasFrame.add(btnRetour);
		voirRepasFrame.setLocationRelativeTo(null);
		voirRepasFrame.setVisible(true);
		// Bouton retour
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				voirRepasFrame.dispose();
			}
		});

	}

	// Fonction utiliser dans voirRepas() qui nous retourne la liste des commandes
	public String[][] getDataRepas(Table table) {

		int compteur = 0;
		int nbCommandes = 0;
		int idrepasclient = 0;
		String[][] donnees = new String[nbCommandes][5];
		try {
			Statement stmt = DBConnection.con.createStatement();
			// On récupére l'ID du repasclient
			ResultSet rs1 = stmt.executeQuery(
					"select MAX(idrepasclient) as id FROM repasclient where idtable = " + table.getIdtable());
			while (rs1.next()) {
				idrepasclient = rs1.getInt("id");
			}
			rs1.close();
			// On récupére le nombre de ligne de commande lié à ce repas client
			ResultSet rs2 = stmt.executeQuery("SELECT COUNT(commande.idcommande) as nbcommande \n"
					+ "FROM commande,repasclient \n" + "WHERE commande.idrepasclient =" + idrepasclient
					+ "AND commande.idrepasclient = repasclient.idrepasclient AND repasclient.datefin is NULL");
			while (rs2.next()) {
				nbCommandes = rs2.getInt("nbcommande");
				donnees = new String[nbCommandes][5];
			}
			rs2.close();
			// On insére les commandes dans le tableau de string
			ResultSet rs3 = stmt.executeQuery("SELECT plat.nom as nom,\n" + "plat.prix as prix,\n"
					+ "commande.dateheurecommande as date,\n"
					+ "commande.menuenfant as enfant, repasclient.addition as addition \n"
					+ "FROM repasclient,plat,commande \n" + "WHERE plat.idplat = commande.idplat \n"
					+ "AND repasclient.datefin is NULL\n" + "AND commande.idrepasclient = repasclient.idrepasclient\n"
					+ "AND repasclient.idrepasclient =" + idrepasclient);

			while (rs3.next()) {
				donnees[compteur][0] = rs3.getString("nom");
				donnees[compteur][1] = rs3.getString("prix");
				donnees[compteur][2] = rs3.getString("date");
				donnees[compteur][3] = rs3.getString("enfant");
				donnees[compteur][4] = rs3.getString("addition");
				compteur++;
			}
			rs3.close();
			stmt.close();

		} catch (Exception ex) {
			System.out.println(ex);
		}
		// On retourne le tableau
		return donnees;
	}

	// Fonction qui permet de terminer le repas d'un client
	public void payerAddition(Table table) {

		try {
			Statement stmt = DBConnection.con.createStatement();
			// On regarde s'il y a un repasclient en cours
			ResultSet rs1 = stmt.executeQuery("Select count(idrepasclient) as nbligne from repasclient where \n"
					+ "datefin is null \n" + "AND idtable =" + table.getIdtable());

			while (rs1.next()) {
				// Si non, on affiche un message
				if (rs1.getInt("nbligne") == 0) {
					JOptionPane.showMessageDialog(null, "Aucune commande n'a été faite");
				}
			}
			rs1.close();
			// Si oui,
			// On récupére l'idrepasclient et la datefin pour la table sélectionné
			ResultSet rs2 = stmt.executeQuery(
					"SELECT idrepasclient, datefin FROM repasclient where idtable =" + table.getIdtable());

			while (rs2.next()) {
				// Si la datefin est null donc que le repas n'est pas terminé
				if (rs2.getDate("datefin") == null) {
					// On ajoute une date de fin
					stmt.executeUpdate("UPDATE repasclient SET datefin = LOCALTIMESTAMP WHERE idrepasclient ="
							+ rs2.getInt("idrepasclient"));
					// On passe le statut de la table a 'sale'
					stmt.executeUpdate("UPDATE tables SET statut = 'sale' WHERE idtable =" + table.getIdtable());
					JOptionPane.showMessageDialog(null, "L'addition a été payé");
					detailFrame.dispose();
					mainFrame.dispose();
					// On relance l'interface serveur pour voir les modifications
					new InterfaceServeur(table.getIdemploye());
				}
			}
			rs2.close();
			stmt.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}

	}

	// Fonction qui permet de saisir sa commande
	public void saisirCommande(final Table table) {

		final JComboBox<Plat> listPlat = new JComboBox<Plat>();
		// On récupére la liste des plats
		try {
			Statement stmt = DBConnection.con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idplat, idcategorie, prix, nom FROM plat");
			while (rs.next()) {
				// On ajoute chaque plat dans notre ComboBox
				listPlat.addItem(new Plat(rs.getInt("idPlat"), rs.getInt("idCategorie"), rs.getInt("prix"),
						rs.getString("nom")));

			}
			rs.close();
			stmt.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
		// Création des composants
		commandeFrame = new JFrame("Saisir commande");
		commandeFrame.setSize(700, 600);
		commandeFrame.setLayout(new GridLayout(3, 1));
		commandeFrame.getContentPane().setBackground(Color.gray);
		JLabel headerLabel = new JLabel("Saisir une commande", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));
		JPanel controlPanel = new JPanel(new java.awt.GridBagLayout());
		GridBagConstraints labCnst = new GridBagConstraints();
		JLabel lidTable = new JLabel("Numéro de la table : " + table.getIdtable());
		JLabel lMenuEnfant = new JLabel("Menu Enfant : ");
		final JCheckBox cbMenuEnfant = new JCheckBox();
		JButton btnAjouter = new JButton("Ajouter le plat");
		JButton btnRetour = new JButton("Retour");
		labCnst.fill = GridBagConstraints.NONE;
		labCnst.insets = new Insets(3, 3, 3, 3);
		labCnst.anchor = GridBagConstraints.LINE_START;
		// Ajout des composants
		commandeFrame.add(headerLabel);
		commandeFrame.add(controlPanel);
		commandeFrame.setVisible(true);
		labCnst.gridx = 0;
		labCnst.gridy = 0;
		controlPanel.add(lidTable, labCnst);
		labCnst.gridx = 0;
		labCnst.gridy = 1;
		controlPanel.add(lMenuEnfant, labCnst);
		labCnst.gridx = 1;
		labCnst.gridy = 1;
		controlPanel.add(cbMenuEnfant, labCnst);
		labCnst.gridx = 0;
		labCnst.gridy = 2;
		controlPanel.add(listPlat, labCnst);
		labCnst.gridx = 0;
		labCnst.gridy = 3;
		controlPanel.add(btnAjouter, labCnst);
		labCnst.gridx = 1;
		labCnst.gridy = 3;
		controlPanel.add(btnRetour, labCnst);
		// Bouton Retour
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				commandeFrame.dispose();
			}
		});
		// Bouton Ajouter la commande
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					// On récupére le plat selectionner dans la ComboBox
					Plat platSelect = (Plat) listPlat.getSelectedItem();
					Statement stmt = DBConnection.con.createStatement();
					Statement stmt2 = DBConnection.con.createStatement();
					// On récupére les repasclient lié a la table
					ResultSet rs = stmt.executeQuery(
							"SELECT idrepasclient, datefin FROM repasclient where idtable =" + table.getIdtable());
					while (rs.next()) {
						// Si le repas n'est pas fini donc que datefin est null
						if (rs.getDate("datefin") == null) {
							// On crée une commande avec les informations fournis
							Commande commande = new Commande(platSelect.getIdplat(), "saisie",
									cbMenuEnfant.isSelected(), java.time.LocalDate.now(), rs.getInt("idrepasclient"));
							// On ajoute la commande à la base
							ajoutCommande(commande, platSelect.getPrix());
							rs.close();
							stmt.close();
						}
					}
					// Si il n'y a pas de repas en cours
					// On insère un nouveau repasclient sans date de fin
					PreparedStatement stmt1 = DBConnection.con.prepareStatement(
							"INSERT into repasclient(idtable,datedebut,addition) values (?,LOCALTIMESTAMP,?)");
					stmt1.setInt(1, table.getIdtable());
					stmt1.setInt(2, 0);
					stmt1.execute();
					stmt1.close();
					// On sélectionne le nouveau repas client créé
					ResultSet rs1 = stmt2.executeQuery("select MAX(idrepasclient) as id FROM repasclient");
					while (rs1.next()) {
						// On crée une commande avec les informations fournis
						Commande commande = new Commande(platSelect.getIdplat(), "saisie", cbMenuEnfant.isSelected(),
								java.time.LocalDate.now(), rs1.getInt("id"));
						rs1.close();
						// On ajoute la commande à la base
						ajoutCommande(commande, platSelect.getPrix());

						stmt2.close();
					}

				} catch (Exception ex) {
					System.out.println(ex);
				}
			}

		});

		commandeFrame.setLocationRelativeTo(null);
		commandeFrame.setVisible(true);
	}

	// Fonction qui permets d'insérer une commande dans la base
	public void ajoutCommande(Commande commande, int prix) {

		try {
			// On insére l'objet commande dans la base
			PreparedStatement stmt = DBConnection.con.prepareStatement(
					"INSERT into commande(idplat,statut,menuenfant,dateheurecommande,idrepasclient) values (?,?,?,LOCALTIMESTAMP,?)");
			stmt.setInt(1, commande.getIdplat());
			stmt.setString(2, commande.getStatut());
			stmt.setBoolean(3, commande.isMenuenfant());
			stmt.setInt(4, commande.getIdrepasclient());
			stmt.execute();
			// On update l'addition du repasclient avec le prix de la commande
			Statement stmt2 = DBConnection.con.createStatement();
			stmt2.executeUpdate("UPDATE repasclient SET addition = addition + " + prix + "WHERE idrepasclient ="
					+ commande.getIdrepasclient());
			JOptionPane.showMessageDialog(null, "Création effectué !");
			stmt.close();
			stmt2.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	// Fonction qui permet d'installer un client à une table
	public void installerClient(Table table) {
		try {
			Statement stmt = DBConnection.con.createStatement();
			// On update le statut de la table en 'occupe'
			stmt.executeUpdate("UPDATE tables SET statut = 'occupe' WHERE idtable =" + table.getIdtable());
			JOptionPane.showMessageDialog(null, "Les clients ont été installés");
			mainFrame.dispose();
			detailFrame.dispose();
			// On relance l'interface serveur pour voir les modifications
			stmt.close();
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
