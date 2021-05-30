package fr.ul.miage.restaurant.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.ResultSetMetaData;

import java.sql.SQLException;
import java.sql.Statement;


import java.util.ArrayList;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import fr.ul.miage.restaurant.bdd.DBConnection;
import fr.ul.miage.restaurant.models.Stock;

public class InterfaceCuisinier {

	private JFrame mainFrame;

	private JPanel controlPanel;

	public InterfaceCuisinier() {
		mainFrame = new JFrame("Cuisinier");
		init();
		launch();
		mainFrame.setVisible(true);
	}

	private void init() {
		mainFrame.setBounds(100, 100, 700, 400);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.getContentPane().setBackground(Color.orange);

		JLabel headerLabel = new JLabel("Cuisinier", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));
		headerLabel.setForeground(Color.white);

		controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(1, 5));

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

		JButton afButton = new JButton("Definir plat");
		JButton composeButton = new JButton("Composer un plat");
		JButton billButton = new JButton("Visualiser commandes");
		afButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				definirPlat();
			}
		});

		composeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectionPlat();
			}
		});

		billButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				visualiserCommande();
			}
		});

		controlPanel.add(afButton);
		controlPanel.add(composeButton);
		controlPanel.add(billButton);

	}

	public JComboBox<String> getListPlat() {
		JComboBox<String> list = new JComboBox<String>();
		ResultSet rs = null;
		String requete = "SELECT nom FROM plat";
		try {
			Statement stmt = DBConnection.con.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				String name = rs.getString("nom");
				list.addItem(name);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	public void selectionPlat() {
		final JFrame selectionFrame = new JFrame("Composer un plat");
		selectionFrame.setSize(700, 600);
		selectionFrame.setLayout(new GridLayout(3, 1));
		selectionFrame.getContentPane().setBackground(Color.gray);

		JLabel headerLabel = new JLabel("Composer un plat", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		selectionFrame.add(headerLabel);
		selectionFrame.add(controlPanel);

		JLabel labelNomPlat = new JLabel("Selectionner le nom du plat ");
		final JComboBox<String> listNomPlat = getListPlat();
		final JButton okButton = new JButton("OK");

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				composerPlat(listNomPlat.getSelectedItem().toString());
			}
		});

		JPanel jp = new JPanel(null);
		GridLayout experimentLayout = new GridLayout(0, 2);
		jp.add(labelNomPlat);
		jp.add(listNomPlat);

		jp.setSize(500, 500);
		jp.setLayout(experimentLayout);
		controlPanel.add(jp);
		jp.add(okButton);

		selectionFrame.setLocationRelativeTo(null);
		selectionFrame.setVisible(true);
	}


	public void composerPlat(final String nomPlat) {
		final JFrame composerFrame = new JFrame("Composition " + nomPlat);
		composerFrame.setSize(600, 550);
		composerFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		JPanel panel = new JPanel(new BorderLayout(10, 20)); // panel principal
		panel.setPreferredSize(new Dimension(600, 550));
		composerFrame.add(panel);
		
		// bouton valider à droite
		JButton validateButton = new JButton("Valider");
		JPanel panelWest = new JPanel(new GridLayout(13, 1));
		panelWest.add(validateButton);
		panel.add(panelWest, BorderLayout.EAST);// ajout du panelWest au panel principal

		// JTable au milieu
		Object[] columns = { "Nom", "Quantité" };
		// Créer le modèle de la JTable
		final DefaultTableModel model = new DefaultTableModel(columns, 0);
		int idPlat = getidPlat(nomPlat);
		int idMatierePremiere = -1;
		int quantite = -1;
		String requete = "SELECT * FROM compositionplat WHERE idplat=" + idPlat;
		try {
			ResultSet rs = null;
			Statement stmt = DBConnection.con.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				// Si on rentre c'est qu'il y a déjà une composition pour ce plat
				idMatierePremiere = rs.getInt("idmatierepremiere");
				quantite = rs.getInt("quantitematierepremiere");
				String nomMatierePremiere = "";
				String requeteNomMatierePremiere = "SELECT nom FROM matierepremiere WHERE idmatierepremiere="+ idMatierePremiere;
				try {
					ResultSet rs2 = null;
					Statement stmt2 = DBConnection.con.createStatement();
					rs2 = stmt2.executeQuery(requeteNomMatierePremiere);
					while (rs2.next()) {
						nomMatierePremiere = rs2.getString("nom");
					}
					rs2.close();
					stmt2.close();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				// Ajout dans la JTable
				model.addRow(new Object[] { nomMatierePremiere, quantite });
			}
			rs.close();
			stmt.close();
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		JTable table = new JTable(model){
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		// Empecher la modifications des valeurs directement
		table.isCellEditable(0,0);

		JPanel panelCentre = new JPanel(new GridLayout(1, 1));
		panelCentre.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		panel.add(panelCentre, BorderLayout.CENTER);// ajout du panelCentre au panel principal

		// ELEMENT EN BAS
		JPanel panelSouth = new JPanel(new GridLayout(3, 2));
		JLabel labelNomMatierePremiere = new JLabel("Selectionner la matière première");
		final JComboBox<String> listMatierePremiere = getListMatierePremiere();
		JLabel labelQuantite = new JLabel("Donner une quantité");
		final JTextField textFieldQuantite = new JTextField();
		JButton add = new JButton("Ajouter/Enlever");
		final JButton delete = new JButton("Supprimer matière première");
		panelSouth.add(labelNomMatierePremiere);
		panelSouth.add(listMatierePremiere);
		panelSouth.add(labelQuantite);
		panelSouth.add(textFieldQuantite);
		panelSouth.add(add);
		panelSouth.add(delete);
		panel.add(panelSouth, BorderLayout.SOUTH);// ajout du panelSouth au panel principal

		composerFrame.pack();
		composerFrame.setLocationRelativeTo(null);
		composerFrame.setVisible(true);
		
		// Action des boutons
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textFieldQuantite.getText().equals("")) {
					String nomMatierePremiere = listMatierePremiere.getSelectedItem().toString();
					int quantite = Integer.parseInt(textFieldQuantite.getText());
					boolean test = true;
					int i = 0;
					while (test && i < model.getRowCount()) {
						if (listMatierePremiere.getSelectedItem().toString().equals(model.getValueAt(i, 0))) {
							test = false;
						} else {
							i++;
						}
					}
					if (!test) {
						// Matiere premiere déjà présente
						// Recupère la quantité de la matiere premiere trouvé (si il en trouve une)
						int quantiteTotal = quantite + Integer.parseInt(model.getValueAt(i, 1).toString());
						if (quantiteTotal < 0) {
							delete.doClick();
						} else {
							model.setValueAt(quantiteTotal, i, 1);
						}
					} else {
						// Matiere premiere pas présente
						if (quantite > 0) {
							model.addRow(new Object[] { nomMatierePremiere, quantite });
						}
					}
				}
			}
		});
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean test = true;
				if (model.getRowCount() > 0) {
					int i = 0;
					while (test && i < model.getRowCount()) {
						if (listMatierePremiere.getSelectedItem().toString().equals(model.getValueAt(i, 0))) {
							test = false;
						} else {
							i++;
						}
					}
					if (!test) {
						model.removeRow(i);
					}
				}
			}
		});
		validateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertCompositionPlat(model,nomPlat);
			}
		});
	}

	
	public void insertCompositionPlat(DefaultTableModel model, String nomPlat) {
		// Récupérer l'id du plat
		int idPlat = getidPlat(nomPlat);
		ResultSet rs = null;
		
		// On delete toutes les lignes ayant l'idPlat
		try {
			PreparedStatement pst;
			try {
				pst = DBConnection.con.prepareStatement("DELETE FROM compositionplat WHERE idplat=" + idPlat);
				pst.execute();
				pst.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		// Puis on rajoute les couples (idPlat, idMatierePremiere) de la JTable
		for (int j = 0; j < model.getRowCount(); j++) {
			String nom = model.getValueAt(j, 0).toString();
			int quantite = Integer.parseInt(model.getValueAt(j, 1).toString());
			int idMatierePremiere = -1;
			// Récupérer l'id de la matière première
			String requeteidMatierePremiere = "SELECT idMatierePremiere FROM matierepremiere WHERE nom='" + nom+ "'";
			try {
				Statement stmt = DBConnection.con.createStatement();
				rs = stmt.executeQuery(requeteidMatierePremiere);
				while (rs.next()) {
					idMatierePremiere = rs.getInt("idmatierepremiere");
				}
				rs.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			// On insert
			PreparedStatement pst2;
			try {
				pst2 = DBConnection.con.prepareStatement("INSERT INTO compositionplat (idplat, idmatierepremiere, quantitematierepremiere) values (?,?,?)");
				pst2.setInt(1, idPlat);
				pst2.setInt(2, idMatierePremiere);
				pst2.setInt(3, quantite);
				pst2.execute();
				pst2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		JOptionPane.showMessageDialog(null,"Modification effectué");
	}

	public JComboBox<String> getListMatierePremiere() {
		JComboBox<String> list = new JComboBox<String>();
		ResultSet rs = null;
		String requete = "SELECT nom FROM matierepremiere";
		try {
			Statement stmt = DBConnection.con.createStatement();
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

	public void visualiserCommande() {
		final JFrame composerFrame = new JFrame("Visualisation commande");
		composerFrame.setSize(600, 550);
		composerFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		JPanel panel = new JPanel(new BorderLayout(10, 20)); // panel principal
		panel.setPreferredSize(new Dimension(600, 550));
		composerFrame.add(panel);
		
		// bouton valider à droite
		JButton validateButton = new JButton("Préparer");
		JPanel panelWest = new JPanel(new GridLayout(13, 1));
		panelWest.add(validateButton);
		panel.add(panelWest, BorderLayout.EAST);// ajout du panelWest au panel principal

		// JTable au milieu
		Object[] columns = { "Nom du plat", "date commande", "menu enfant" };
		// Créer le modèle de la JTable
		final DefaultTableModel model = new DefaultTableModel(columns, 0);
		String requete ="SELECT idplat,dateheurecommande,menuenfant FROM commande WHERE statut='saisie'";
		try {
			int idPlat = -1;
			String dateheurecommande="";
			boolean menuenfant=false;
			ResultSet rs = null;
			Statement stmt = DBConnection.con.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				// Si on rentre c'est qu'il y a déjà une composition pour ce plat
				idPlat = rs.getInt("idplat");
				String requeteNomPlat ="SELECT nom FROM plat WHERE idplat="+idPlat;
				try {
					dateheurecommande = rs.getString("dateheurecommande");
					menuenfant = rs.getBoolean("menuenfant");
					String nomPlat ="";
					ResultSet rs2 = null;
					Statement stmt2 = DBConnection.con.createStatement();
					rs2 = stmt2.executeQuery(requeteNomPlat);
					while (rs2.next()) {
						
						nomPlat = rs2.getString("nom");
						model.addRow(new Object[] { nomPlat, dateheurecommande, menuenfant });
					}
				}catch (Exception e) {
					System.out.println(e.getLocalizedMessage());
				}
				
				
				// Ajout dans la JTable
				
			}
			rs.close();
			stmt.close();
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		JTable table = new JTable(model){
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		// Empecher la modifications des valeurs directement
		table.isCellEditable(0,0);

		JPanel panelCentre = new JPanel(new GridLayout(1, 1));
		panelCentre.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		panel.add(panelCentre, BorderLayout.CENTER);// ajout du panelCentre au panel principal
/*
		// ELEMENT EN BAS
		JPanel panelSouth = new JPanel(new GridLayout(3, 2));
		JLabel labelNomMatierePremiere = new JLabel("Selectionner la matière première");
		final JComboBox<String> listMatierePremiere = getListMatierePremiere();
		JLabel labelQuantite = new JLabel("Donner une quantité");
		final JTextField textFieldQuantite = new JTextField();
		JButton add = new JButton("Ajouter/Enlever");
		final JButton delete = new JButton("Supprimer matière première");
		panelSouth.add(labelNomMatierePremiere);
		panelSouth.add(listMatierePremiere);
		panelSouth.add(labelQuantite);
		panelSouth.add(textFieldQuantite);
		panelSouth.add(add);
		panelSouth.add(delete);
		panel.add(panelSouth, BorderLayout.SOUTH);// ajout du panelSouth au panel principal
*/
		composerFrame.pack();
		composerFrame.setLocationRelativeTo(null);
		composerFrame.setVisible(true);
		/*
		// Action des boutons
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		*/
		validateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
	}

	public void definirPlat() {
		final JFrame platFrame = new JFrame("Definir un plat");
		platFrame.setSize(700, 600);
		platFrame.setLayout(new GridLayout(3, 1));

		platFrame.getContentPane().setBackground(Color.gray);

		JLabel headerLabel = new JLabel("Definir un plat", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		platFrame.add(headerLabel);
		platFrame.add(controlPanel);
		// platFrame.setVisible(true);
		/*
		 * JLabel idPlat = new JLabel("Entrer id du plat"); final JTextField
		 * textFieldidPlat = new JTextField(); textFieldidPlat.setSize(100, 40);
		 */
		JLabel idCategorie = new JLabel("Entrer id de la categorie");
		final JTextField textFieldidCategorie = new JTextField();
		textFieldidCategorie.setSize(100, 40);

		JLabel prix = new JLabel("Entrer le prix du plat");
		final JTextField textFieldPrix = new JTextField();
		textFieldPrix.setSize(100, 40);

		JLabel NomPlat = new JLabel("Entrer le nom du plat");
		final JTextField textFieldNomPlat = new JTextField();
		textFieldNomPlat.setSize(100, 40);

		final JButton okButton = new JButton("OK");

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PreparedStatement pst;
				Boolean test = true;

				try {
					// Statement stmt = DBConnection.con.createStatement();
					if (textFieldidCategorie.getText().equals("") || textFieldPrix.getText().equals("")
							|| textFieldNomPlat.getText().equals("")) {
						test = false;
					}

					pst = DBConnection.con.prepareStatement("INSERT into plat (idcategorie, prix,nom) values (?,?,?)");
					// pst.setInt(1, Integer.parseInt(textFieldidPlat.getText()));
					pst.setInt(1, Integer.parseInt(textFieldidCategorie.getText()));
					pst.setInt(2, Integer.parseInt(textFieldPrix.getText()));
					pst.setString(3, textFieldNomPlat.getText());

					if (test) {
						pst.execute();
						JOptionPane.showMessageDialog(null, "Plat défini !");
					} else {
						JOptionPane.showMessageDialog(null,"Impossible de definir le plat (certain champs sont peut-étre vide)");
					}


				} catch (SQLException e1) {
					e1.printStackTrace();
				} finally {
					platFrame.dispose();
					definirPlat();
				}

			}
		});

		JPanel jp = new JPanel(null);
		GridLayout experimentLayout = new GridLayout(0, 2);
		// jp.add(idPlat);
		// jp.add(textFieldidPlat);
		jp.add(idCategorie);
		jp.add(textFieldidCategorie);
		jp.add(prix);
		jp.add(textFieldPrix);
		jp.add(NomPlat);
		jp.add(textFieldNomPlat);

		jp.setSize(500, 500);
		jp.setLayout(experimentLayout);
		controlPanel.add(jp);
		jp.add(okButton);

		platFrame.setLocationRelativeTo(null);
		platFrame.setVisible(true);

	}
	
	public int getidPlat(String nomPlat) {
		int idPlat = -1;
		ResultSet rs = null;
		String requeteidPlat = "SELECT idPlat FROM plat WHERE nom='" + nomPlat + "'";
		try {
			Statement stmt = DBConnection.con.createStatement();
			rs = stmt.executeQuery(requeteidPlat);
			while (rs.next()) {
				idPlat = rs.getInt("idPlat");
			}
			rs.close();
			stmt.close();
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		return idPlat;
	}

}
