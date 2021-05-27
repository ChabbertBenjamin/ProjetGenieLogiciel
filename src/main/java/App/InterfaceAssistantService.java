package App;

import java.awt.*;

import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class InterfaceAssistantService {

	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JTable cart;

	private DBConnection con;

	public InterfaceAssistantService() {
		this.con = new DBConnection();
		prepareGUI();

	}

	public static void main(String[] args) {
		InterfaceMaitreDhotel swingControlDemo = new InterfaceMaitreDhotel();
		swingControlDemo.showButtonDemo();

	}

	private void prepareGUI() {
		mainFrame = new JFrame("Assistant de service");
		mainFrame.setBounds(100, 100, 700, 400);
		mainFrame.setLayout(new GridLayout(3, 1));

		mainFrame.getContentPane().setBackground(Color.orange);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				try {
					con.con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					System.exit(0);
				}

			}
		});
		headerLabel = new JLabel("", JLabel.CENTER);
		statusLabel = new JLabel("", JLabel.CENTER);

		statusLabel.setSize(350, 300);

		controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(1, 5));

		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		mainFrame.setVisible(true);

	}

	public void showButtonDemo() {

		headerLabel.setText("Assistant de service");
		this.headerLabel.setFont(new Font(null, Font.BOLD, 27));
		headerLabel.setForeground(Color.white);

		JButton seeTableButton = new JButton("Voir les tables");

		seeTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seeTable();
			}
		});
		controlPanel.add(seeTableButton);
		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);

	}

	public String[][] getData() {
		ResultSet rs = null;
		int nbLignes = 0;
		try {
			Statement stmt = con.con.createStatement();
			rs = stmt.executeQuery("SELECT count(*) AS nbLignes FROM tables ");
			rs.next();
			nbLignes = rs.getInt("nbLignes");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[][] donnees = new String[nbLignes][4];
		String requete = "SELECT * FROM tables";
		try {
			Statement stmt = con.con.createStatement();
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

		ResultSet r�sultats = null;
		String requete = "SELECT idTable FROM tables WHERE statut='sale'";
		try {
			Statement stmt = con.con.createStatement();
			r�sultats = stmt.executeQuery(requete);
			while (r�sultats.next()) {
				String id = r�sultats.getString("idTable");
				list.addItem(id);
			}
			r�sultats.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	public void seeTable() {
		mainFrame = new JFrame("Tables");
		mainFrame.setSize(500, 550);

		JPanel jp = new JPanel();
		jp.setSize(400, 400);

		String[] columnNames = { "Num�ro table ", "statut", "nombre de couverts", "�tage" };
		String[][] donnees = getData();
		cart = new JTable(donnees, columnNames);
		cart.setSize(300, 450);
		jp.setLayout(new FlowLayout());

		jp.add(new JScrollPane(cart, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

		JComboBox<String> list = new JComboBox<String>();
		list.addItem("");
		JComboBox<String> listTableToSet = getListTableToSet(list);

		JButton setTable = new JButton("Dresser la table s�lectionn�");
		setTable.setSize(40, 50);

		setTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listTableToSet.getSelectedItem().equals("")) {
					JOptionPane.showMessageDialog(null, "Vous devez s�l�ctionn� une table");
				} else {
					PreparedStatement pst;
					try {
						pst = con.mkDataBase().prepareStatement("UPDATE tables SET statut='propre' WHERE idTable="
								+ listTableToSet.getSelectedItem().toString());
						pst.execute();
						JOptionPane.showMessageDialog(null, "Table dress� !");
						mainFrame.dispose();
						seeTable();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		jp.add(listTableToSet);
		jp.add(setTable);

		mainFrame.add(jp);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		cart.setAutoCreateRowSorter(true);
		cart.setEnabled(false);

	}

}