package fr.ul.miage.restaurant.menu;

import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import fr.ul.miage.restaurant.bdd.DBConnection;

public class Frame1 extends JFrame {

	JLabel idLabel;
	JLabel passwordLabel;
	JLabel background;
	JLabel headerLabel;
	JLabel devInfo;

	JTextField id;
	JTextField password;

	JButton submit;
	

	public Frame1() {
		setTitle("Interface de connexion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		// Background
		//this.background = new JLabel(new ImageIcon("C:\\Users\\Asus\\Downloads\\Java-Project-Final (1)\\Java Project Final\\image\\burger.jpg"));

		this.init();

		add(background);
		background.setVisible(true);
		this.pack();
		this.setSize(700, 400);
		this.setLocationRelativeTo(null);
	}

	public void init() {
		headerLabel = new JLabel();
		this.headerLabel.setText("Connexion");
		this.headerLabel.setBounds(270, 1, 200, 100);
		this.headerLabel.setFont(new Font("Geomanist", Font.BOLD, 25));
		headerLabel.setForeground(Color.white);
		add(headerLabel);

		idLabel = new JLabel();
		this.idLabel.setText("Identidiant");
		this.idLabel.setBounds(190, 110, 100, 50);
		this.idLabel.setFont(new Font(null, Font.BOLD, 20));
		idLabel.setForeground(Color.white);
		add(idLabel);

		passwordLabel = new JLabel();
		this.passwordLabel.setText("Mot de passe");
		this.passwordLabel.setBounds(190, 155, 150, 50);
		this.passwordLabel.setFont(new Font(null, Font.BOLD, 20));
		passwordLabel.setForeground(Color.white);
		add(passwordLabel);

		devInfo = new JLabel();
		this.devInfo.setText("© all Rights Reserved");
		this.devInfo.setBounds(130, 300, 1000, 30);
		this.devInfo.setFont(new Font("Geomanist", Font.PLAIN, 15));
		devInfo.setForeground(Color.white);
		add(devInfo);

		id = new JTextField();
		this.id.setBounds(350, 125, 200, 30);
		add(id);
		
		password = new JPasswordField();
		password.setBounds(350, 165, 200, 30);
		add(password);

		this.id.setVisible(true);

		this.submit = new JButton("Login");
		this.submit.setBounds(400, 230, 100, 25);
		add(submit);

		submit.addActionListener(this::submitActionPerformed);

	}

	public void submitActionPerformed(java.awt.event.ActionEvent evt) {
		DBConnection con = new DBConnection();
		ResultSet resultats = null;
		String requete = "SELECT login, motdepasse,role FROM employe WHERE login='" + id.getText() + "'";
		Boolean connect = false;
		try {
			Statement stmt = con.con.createStatement();
			resultats = stmt.executeQuery(requete);

			String role = null;
			while (resultats.next() && !connect) {
				String login = resultats.getString("login");
				String password = resultats.getString("motdepasse");
				role = resultats.getString("role");
				// Si l'identifiant et le mot de passe correcsponde à un compte
				if (id.getText().equals(login) && this.password.getText().equals(password)) {
					connect = true;
				}
			}
			
			// Si les informations sont corrects, on lance la bonne interface
			if (connect) {
				this.hide();
				if (role.equals("serveur")) {
					InterfaceServeur fn = new InterfaceServeur();
					fn.showButtonDemo();

				} else if (role.equals("directeur")) {
					InterfaceDirecteur fn = new InterfaceDirecteur();
					fn.showButtonDemo();

				} else if (role.equals("cuisinier")) {
					InterfaceCuisinier fn = new InterfaceCuisinier();
					fn.showButtonDemo();
				} else if (role.equals("maitre d hôtel")) {
					InterfaceMaitreDhotel fn = new InterfaceMaitreDhotel();
					fn.showButtonDemo();
				} else if (role.equals("assistant service")) {
					InterfaceAssistantService fn = new InterfaceAssistantService();
					fn.showButtonDemo();
				}
			}else {
				JOptionPane.showMessageDialog(null, "Invalid identifiant");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}

