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
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.ul.miage.restaurant.bdd.DBConnection;


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

		JButton billButton = new JButton("Visualiser commandes");
		JButton afButton = new JButton("Definir plat");

		billButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				visualiserCommande();
			}
		});

		afButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				definirPlat();
			}
		});

		controlPanel.add(afButton);
		controlPanel.add(billButton);

		

	}
	
	public void visualiserCommande() {
		
	}
	
	public void definirPlat() {
		JFrame platFrame = new JFrame("Definir un plat");
		platFrame.setSize(700, 600);
		platFrame.setLayout(new GridLayout(3, 1));

		platFrame.getContentPane().setBackground(Color.gray);


		JLabel headerLabel = new JLabel("Definir un plat", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		platFrame.add(headerLabel);
		platFrame.add(controlPanel);
		platFrame.setVisible(true);
		
		JLabel idPlat = new JLabel("Entrer id du plat");
		final JTextField textFieldidPlat = new JTextField();
		textFieldidPlat.setSize(100, 40);

		JLabel idCategorie = new JLabel("Entrer id de la categorie");
		final JTextField textFieldidCategorie = new JTextField();
		textFieldidCategorie.setSize(100, 40);

		JLabel prix = new JLabel("Entrer le prix du plat");
		final JTextField textFieldPrix = new JTextField();
		textFieldPrix.setSize(100, 40);

		JLabel idMatierePremiere = new JLabel("Entrer id matiere premiere");
		final JTextField textFieldidMatierePremiere = new JTextField();
		textFieldidMatierePremiere.setSize(100, 40);

		JButton okButton = new JButton("OK");
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JPanel jp = new JPanel(null);
		GridLayout experimentLayout = new GridLayout(0, 2);
		jp.add(idPlat);
		jp.add(textFieldidPlat);
		jp.add(idCategorie);
		jp.add(textFieldidCategorie);
		jp.add(prix);
		jp.add(textFieldPrix);
		jp.add(idMatierePremiere);
		jp.add(textFieldidMatierePremiere);

		jp.setSize(500, 500);
		jp.setLayout(experimentLayout);
		controlPanel.add(jp);
		jp.add(okButton);

		platFrame.setLocationRelativeTo(null);
		platFrame.setVisible(true);
		
		
	}

}
