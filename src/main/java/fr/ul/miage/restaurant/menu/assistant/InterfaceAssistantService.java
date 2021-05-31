package fr.ul.miage.restaurant.menu.assistant;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import fr.ul.miage.restaurant.bdd.DBConnection;

public class InterfaceAssistantService {

	private JFrame mainFrame;
	private JPanel controlPanel;
	private JTable cart;

	public InterfaceAssistantService() {
		mainFrame = new JFrame("Assistant de service");
		init();
		launch();
		mainFrame.setVisible(true);
	}

	private void init() {
		mainFrame.setBounds(100, 100, 700, 400);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.getContentPane().setBackground(Color.orange);

		JLabel headerLabel = new JLabel("Assistant de service", JLabel.CENTER);
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
		JButton seeTableButton = new JButton("Voir les tables");
		seeTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seeTable();
			}
		});
		controlPanel.add(seeTableButton);
	}

	public String[][] getData() {
		ResultSet rs = null;
		int nbLignes = 0;
		try {
			Statement stmt = DBConnection.con.createStatement();
			rs = stmt.executeQuery("SELECT count(*) AS nbLignes FROM tables ");
			rs.next();
			nbLignes = rs.getInt("nbLignes");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[][] donnees = new String[nbLignes][4];
		String requete = "SELECT * FROM tables";
		try {
			Statement stmt = DBConnection.con.createStatement();
			rs = stmt.executeQuery(requete);
			int compteur = 0;
			while (rs.next()) {
				donnees[compteur][0] = rs.getString("idtable");
				donnees[compteur][1] = rs.getString("statut");
				donnees[compteur][2] = rs.getString("nbcouverts");
				donnees[compteur][3] = rs.getString("etage");
				compteur++;
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return donnees;
	}

	public JComboBox<String> getListTableToSet(JComboBox<String> list) {

		ResultSet rs = null;
		String requete = "SELECT idTable FROM tables WHERE statut='sale'";
		try {
			Statement stmt = DBConnection.con.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				String id = rs.getString("idTable");
				list.addItem(id);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	public void seeTable() {

		final JFrame tableFrame = new JFrame("Tables");
		tableFrame.setSize(500, 550);

		JPanel jp = new JPanel();
		jp.setSize(400, 400);

		String[] columnNames = { "Numéro table ", "statut", "nombre de couverts", "étage" };
		String[][] donnees = getData();
		cart = new JTable(donnees, columnNames);
		cart.setSize(300, 450);

		cart.setAutoCreateRowSorter(true);
		cart.setEnabled(true);
		cart.setSelectionModel(new OnlyOnRowToBeSelected());
		cart.setRowSelectionAllowed(true);
		jp.setLayout(new FlowLayout());

		jp.add(new JScrollPane(cart, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

		JComboBox<String> list = new JComboBox<String>();
		list.addItem("");
		final JComboBox<String> listTableToSet = getListTableToSet(list);
		JButton desservirTableSelectionnee = new JButton("Desservir la table sélectionné");
		JButton setTable = new JButton("Dresser la table sélectionné");
		
		setTable.setSize(40, 50);

		setTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listTableToSet.getSelectedItem().equals("")) {
					JOptionPane.showMessageDialog(null, "Vous devez séléctionné une table");
				} else {
					PreparedStatement pst;
					try {
						pst = DBConnection.con.prepareStatement("UPDATE tables SET statut='propre' WHERE idTable="
								+ listTableToSet.getSelectedItem().toString());
						pst.execute();
						JOptionPane.showMessageDialog(null, "Table dressé !");
						tableFrame.dispose();
						seeTable();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		desservirTableSelectionnee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = cart.getSelectedRow();
				if (selectedRow == -1 || !"occupe".equals(cart.getModel().getValueAt(selectedRow,1))) {
					JOptionPane.showMessageDialog(null, "Vous devez séléctionné une ligne de table occupée");
				} else {
					PreparedStatement pst;
					try {
						Object selectedTableId = cart.getModel().getValueAt(selectedRow, 0);
						pst = DBConnection.con.prepareStatement("UPDATE tables SET statut='sale' WHERE idTable="
								+ selectedTableId);
						pst.execute();
						JOptionPane.showMessageDialog(null, "Table desservie !");
						tableFrame.dispose();
						seeTable();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		jp.add(listTableToSet);
		jp.add(setTable);
		jp.add(desservirTableSelectionnee);
		tableFrame.add(jp);
		tableFrame.setLocationRelativeTo(null);
		tableFrame.setVisible(true);
	}

}