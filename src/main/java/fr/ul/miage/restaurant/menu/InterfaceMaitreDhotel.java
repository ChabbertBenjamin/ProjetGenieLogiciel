package fr.ul.miage.restaurant.menu;

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

import fr.ul.miage.restaurant.bdd.DBConnection;

public class InterfaceMaitreDhotel {

	private JFrame mainFrame;
	private JPanel controlPanel;

	public InterfaceMaitreDhotel() {
		mainFrame = new JFrame("Maitre d hotel");
		init();
		launch();
		mainFrame.setVisible(true);
	}

	private void init() {
		mainFrame.setBounds(100, 100, 700, 400);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.getContentPane().setBackground(Color.orange);

		JLabel headerLabel = new JLabel("Maitre d hotel", JLabel.CENTER);
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
		JButton fkButton = new JButton("Affecter un serveur");
		fkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				affecterServeur();
			}
		});
		controlPanel.add(fkButton);
	}
	
	public void affecterServeur() {
		final JFrame affectServerFrame = new JFrame("Affecter serveur!");
		affectServerFrame.setSize(700, 600);
		affectServerFrame.setLayout(new GridLayout(3, 1));
		affectServerFrame.getContentPane().setBackground(Color.gray);


		JLabel headerLabel = new JLabel("Affecter serveur", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		affectServerFrame.add(headerLabel);
		affectServerFrame.add(controlPanel);
		affectServerFrame.setVisible(true);
		
		
		JLabel JLabelidTable = new JLabel("Selectionner une table");
		final JComboBox<String> selectidTable = new JComboBox<String>();
		selectidTable.addItem("");
		ResultSet rs = null;
		String requete = "SELECT idtable FROM tables";
		try {
			Statement stmt = DBConnection.con.createStatement();
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
		final JComboBox<String> selectNameEmploye = new JComboBox<String>();
		selectNameEmploye.addItem("");
		

		
		ResultSet rs1 = null;
		String requete1 = "SELECT nom, prenom FROM employe WHERE role='serveur'";
		try {
			Statement stmt = DBConnection.con.createStatement();
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
						Statement stmt = DBConnection.con.createStatement();
						String[] name = selectNameEmploye.getSelectedItem().toString().split(" ");
						String requete = "SELECT idemploye FROM employe WHERE nom='"+name[0]+"' AND prenom='"+name[1]+"'";
						rs = stmt.executeQuery(requete);
            			rs.next();
						pst = DBConnection.con.prepareStatement("UPDATE tables SET idemploye="+rs.getString("idemploye")+" WHERE idtable="+selectidTable.getSelectedItem()+"");
						pst.execute();
						rs.close();
						stmt.close();
						pst.close();
						JOptionPane.showMessageDialog(null,"Affectation effectué");
						affectServerFrame.dispose();
					} catch (Exception ex) {
						System.out.println(ex);
					}
				}else {
					JOptionPane.showMessageDialog(null,"Vous devez sélectionner un serveur et une table");
				}
			}
		});
		
		GridLayout experimentLayout = new GridLayout(0, 2);
		JPanel jp = new JPanel(null);
		jp.add(nameEmployeLabel);
		jp.add(selectNameEmploye);
		jp.add(JLabelidTable);
		jp.add(selectidTable);
		jp.setSize(500, 500);
		jp.setLayout(experimentLayout);
		controlPanel.add(jp);
		jp.add(okButton);
	

		affectServerFrame.setLocationRelativeTo(null);
		affectServerFrame.setVisible(true);
	}
	

}