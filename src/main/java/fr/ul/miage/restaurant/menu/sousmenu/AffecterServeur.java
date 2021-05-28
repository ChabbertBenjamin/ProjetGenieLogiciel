package fr.ul.miage.restaurant.menu.sousmenu;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import fr.ul.miage.restaurant.bdd.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AffecterServeur {

	private JFrame mainFrame;
	private JLabel headerLabel;
	private JPanel controlPanel;
	private JLabel JLabelidTable, idemploye;
	GridLayout experimentLayout = new GridLayout(0, 2);
	ResultSet rs;

	private DBConnection con;

	public AffecterServeur() {
		this.con = new DBConnection();
		prepareGUI();
	}


	private void prepareGUI() {
		mainFrame = new JFrame("Affecter serveur!");
		mainFrame.setSize(700, 600);
		mainFrame.setLayout(new GridLayout(3, 1));

		mainFrame.getContentPane().setBackground(Color.gray);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				try {
					con.con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		headerLabel = new JLabel("", JLabel.CENTER);

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.setVisible(true);

	}

	public void showButtonDemo() {

		headerLabel.setText("Affecter serveur");
		headerLabel.setFont(new Font(null, Font.BOLD, 27));

		JLabelidTable = new JLabel("Selectionner une table");
		JComboBox<String> selectidTable = new JComboBox<String>();
		selectidTable.addItem("");
		ResultSet rs = null;
		String requete = "SELECT idtable FROM tables";
		try {
			Statement stmt = con.con.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				String idTable = rs.getString("idtable");
				selectidTable.addItem(idTable);
			}
			rs.close();
			stmt.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		// JComboBox pour sélectionner l'employé é modifier
		JLabel nameEmployeLabel = new JLabel("Selectionner nom serveur");
		JComboBox<String> selectNameEmploye = new JComboBox<String>();
		selectNameEmploye.addItem("");
		

		
		ResultSet rs1 = null;
		String requete1 = "SELECT nom, prenom FROM employe WHERE role='serveur'";
		try {
			Statement stmt = con.con.createStatement();
			rs1 = stmt.executeQuery(requete1);
			while (rs1.next()) {
				String nameEmploye = rs1.getString("nom") + " " + rs1.getString("prenom");
				selectNameEmploye.addItem(nameEmploye);
			}
			rs1.close();
			stmt.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
				

		JButton okButton = new JButton("OK");
		

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!selectNameEmploye.getSelectedItem().equals("") && !selectidTable.getSelectedItem().equals("")) {
					ResultSet rs = null;
					PreparedStatement pst;
					try {
						Statement stmt = con.con.createStatement();
						String[] name = selectNameEmploye.getSelectedItem().toString().split(" ");
						String requete = "SELECT idemploye FROM employe WHERE nom='"+name[0]+"' AND prenom='"+name[1]+"'";
						rs = stmt.executeQuery(requete);
            			rs.next();
						pst = con.mkDataBase().prepareStatement("UPDATE tables SET idemploye="+rs.getString("idemploye")+" WHERE idtable="+selectidTable.getSelectedItem()+"");
						pst.execute();
						rs.close();
						stmt.close();
						pst.close();
						JOptionPane.showMessageDialog(null,"Affectation effectué");
						mainFrame.dispose();
					} catch (Exception ex) {
						System.out.println(ex);
					}
				}else {
					JOptionPane.showMessageDialog(null,"Vous devez sélectionner un serveur et une table");
				}
			}
		});
		

		JPanel jp = new JPanel(null);
		jp.add(nameEmployeLabel);
		jp.add(selectNameEmploye);
		jp.add(JLabelidTable);
		jp.add(selectidTable);
		
		
		

		jp.setSize(500, 500);
		jp.setLayout(experimentLayout);
		controlPanel.add(jp);
		jp.add(okButton);
	

		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
}
