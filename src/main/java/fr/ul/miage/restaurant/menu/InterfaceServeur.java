package fr.ul.miage.restaurant.menu;

import java.awt.Color;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import fr.ul.miage.restaurant.bdd.DBConnection;
import fr.ul.miage.restaurant.models.Table;

public class InterfaceServeur {

	private JFrame mainFrame;
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
		controlPanel = new JPanel();
		controlPanel.setLayout(new GridBagLayout());
		controlPanel.setSize(500, 500);
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
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = .25;
		c.insets = new Insets(3, 0, 3, 0);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;

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
				controlPanel.add(buttonTable, c);
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
		final JFrame tableFrame = new JFrame("Détails de la table");
		tableFrame.setSize(700, 600);
		tableFrame.setLayout(new GridLayout(3, 1));
		tableFrame.getContentPane().setBackground(Color.gray);

		JLabel headerLabel = new JLabel("Détails de la table", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		tableFrame.add(headerLabel);
		tableFrame.add(controlPanel);

		GridLayout experimentLayout = new GridLayout(0, 2);
		JLabel lidTable = new JLabel("Numéro : " + table.getIdtable());
		JLabel lstatut = new JLabel("Statut : " + table.getStatut());
		JLabel lnbcouverts = new JLabel("Nombre de couverts : " + table.getNbcouverts());
		JLabel letage = new JLabel("Etage : " + table.getEtage());
		JLabel lidEmploye = new JLabel("Employé(e) affecté(e) : " + table.getIdemploye());
		JButton btnRetour = new JButton("Retour");
		JButton btnAjout = new JButton("Saisir une commande");
		JButton btnReserve = new JButton("Reserver la table");
		JPanel jp = new JPanel(null);
		jp.add(lidTable);
		jp.add(lstatut);
		jp.add(lnbcouverts);
		jp.add(letage);
		jp.add(lidEmploye);
		jp.add(btnRetour);
		jp.add(btnAjout);
		jp.add(btnReserve);
		if (table.getStatut().equals("occupe")) {
			btnAjout.setVisible(true);
			btnReserve.setVisible(false);
		} else {
			btnAjout.setVisible(false);
			btnReserve.setVisible(true);
		}
		jp.setSize(500, 500);
		jp.setLayout(experimentLayout);
		controlPanel.add(jp);
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableFrame.dispose();
			}
		});
		btnAjout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saisirCommande();
			}
		});
		tableFrame.setLocationRelativeTo(null);
		tableFrame.setVisible(true);
	}

	
	public void saisirCommande() {
		final JFrame commandeFrame = new JFrame("Saisir commande");
		commandeFrame.setSize(700, 600);
		commandeFrame.setLayout(new GridLayout(3, 1));

		commandeFrame.getContentPane().setBackground(Color.gray);

		JLabel headerLabel = new JLabel("Saisir une commande", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		commandeFrame.add(headerLabel);
		commandeFrame.add(controlPanel);
		commandeFrame.setVisible(true);
		GridLayout experimentLayout = new GridLayout(0, 2);
		
		JLabel idplat = new JLabel("Entrer id plat");
		final JTextField textFieldidPlat = new JTextField();
		textFieldidPlat.setSize(100, 40);

		JLabel statut = new JLabel("Entrer statut");
		final JTextField textFieldStatut = new JTextField();
		textFieldStatut.setSize(100, 40);
		
		JLabel idrepasclient = new JLabel("Entrer id repas client");
		final JTextField textFieldidrepasclient = new JTextField();
		textFieldidrepasclient.setSize(100, 40);
	
		
		JButton okButton = new JButton("OK");

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PreparedStatement pst;
				Boolean test = true;
				ResultSet rs = null;
				try {
					Statement stmt = DBConnection.con.createStatement();
					if (textFieldidPlat.getText().equals("") || textFieldStatut.getText().equals("")
							|| textFieldidrepasclient.getText().equals("") ) {
						
						test = false;
					}
				
					pst = DBConnection.con.prepareStatement("INSERT into commande (idplat, statut,idrepasclient) values (?,?,?)");
					pst.setInt(1, Integer.parseInt(textFieldidPlat.getText()));
					pst.setString(2, textFieldStatut.getText());
					pst.setInt(3, Integer.parseInt(textFieldidrepasclient.getText()));
					
					
					if (test) {
						pst.execute();
						JOptionPane.showMessageDialog(null, "Commande saisi !");
					}else {
						JOptionPane.showMessageDialog(null,
								"Impossible de saisir la commande (certain champs sont peut-étre vide)");
					}
					rs.close();
				
				}catch (SQLException e1) {
					e1.printStackTrace();
				}finally {
					commandeFrame.dispose();
					saisirCommande();
				}
					
				}
		});

		JPanel jp = new JPanel(null);
		jp.add(idplat);
		jp.add(textFieldidPlat);
		jp.add(statut);
		jp.add(textFieldStatut);
		jp.add(idrepasclient);
		jp.add(textFieldidrepasclient);
		
		jp.setSize(500, 500);
		jp.setLayout(experimentLayout);
		controlPanel.add(jp);
		jp.add(okButton);
		commandeFrame.setLocationRelativeTo(null);
		commandeFrame.setVisible(true);
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
