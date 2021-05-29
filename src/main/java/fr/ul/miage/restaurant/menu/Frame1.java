package fr.ul.miage.restaurant.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
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

	private static final long serialVersionUID = 1L;

	public Frame1() {
		// Connection à la base de données
		DBConnection.connection();
		this.setTitle("Interface de connexion");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//new ImageIcon("C:\\Users\\Asus\\Downloads\\Java-Project-Final (1)\\Java Project Final\\image\\burger.jpg")
		JLabel background = new JLabel();
		this.init();
		add(background);
		this.setSize(700, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void init() {
		// Titre
		JLabel headerLabel = new JLabel("Connexion");
		headerLabel.setBounds(270, 1, 200, 100);
		headerLabel.setFont(new Font("Geomanist", Font.BOLD, 25));
		headerLabel.setForeground(Color.black);
		add(headerLabel);

		// Label identifiant
		JLabel idLabel = new JLabel("Identifiant");
		idLabel.setBounds(190, 110, 100, 50);
		idLabel.setFont(new Font(null, Font.BOLD, 20));
		idLabel.setForeground(Color.black);
		add(idLabel);
		
		// TextField identifiant
		final JTextField identifiantTextField = new JTextField();
		identifiantTextField.setBounds(350, 125, 200, 30);
		add(identifiantTextField);

		// Label mot de passe
		JLabel passwordLabel = new JLabel("Mot de passe");
		passwordLabel.setBounds(190, 155, 150, 50);
		passwordLabel.setFont(new Font(null, Font.BOLD, 20));
		passwordLabel.setForeground(Color.black);
		add(passwordLabel);
		
		// PasswordField mot de passe
		final JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(350, 165, 200, 30);
		add(passwordField);

		// Label Info
		JLabel devInfo = new JLabel("© all Rights Reserved");
		devInfo.setBounds(130, 300, 1000, 30);
		devInfo.setFont(new Font("Geomanist", Font.PLAIN, 15));
		devInfo.setForeground(Color.black);
		add(devInfo);

		// Bouton login
		JButton login = new JButton("Login");
		login.setBounds(400, 230, 100, 25);
		add(login);

		// Lorsque l'on appuie sur le bouton login
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet rs = null;
				String requeteIdentifiant = "SELECT motdepasse, role, idemploye FROM employe WHERE login='" + identifiantTextField.getText() + "'";
				Boolean connect = false;
				int idEmploye = -1;
				try {
					Statement stmt = DBConnection.con.createStatement();
					rs = stmt.executeQuery(requeteIdentifiant);
					String role = null;
					while (rs.next() && !connect) {
						String password = rs.getString("motdepasse");
						role = rs.getString("role");
						idEmploye = rs.getInt("idemploye");
						// Si l'identifiant et le mot de passe correspondent à un compte
						if (passwordField.getText().equals(password)) {
							connect = true;
						}
					}

					// Si les informations sont corrects, on lance la bonne interface
					if (connect) {
						if (role.equals("serveur")) {
							new InterfaceServeur(idEmploye);
						} else if (role.equals("directeur")) {
							new InterfaceDirecteur();
						} else if (role.equals("cuisinier")) {
							new InterfaceCuisinier();
						} else if (role.equals("maitre d hôtel")) {
							new InterfaceMaitreDhotel();
						} else if (role.equals("assistant service")) {
							new InterfaceAssistantService();
						}
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Invalid identifiant");
					}

				} catch (SQLException erreur) {
					System.out.println(erreur.getMessage());
				}
			}
		});
		
		// On ferme la connection à la BDD lorsqu'on ferme la fenêtre
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				try {
					DBConnection.con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					System.exit(0);
				}

			}
		});
	}
}
