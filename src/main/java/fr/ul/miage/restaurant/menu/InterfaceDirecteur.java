package fr.ul.miage.restaurant.menu;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.ul.miage.restaurant.menu.sousmenu.GererCarteDuJour;
import fr.ul.miage.restaurant.menu.sousmenu.GestionEmploye;
import fr.ul.miage.restaurant.menu.sousmenu.Stock;

public class InterfaceDirecteur {

	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;

	public InterfaceDirecteur() {

		prepareGUI();

	}


	private void prepareGUI() {
		mainFrame = new JFrame("Directeur");
		mainFrame.setBounds(100, 100, 700, 400);
		mainFrame.setLayout(new GridLayout(3, 1));

		mainFrame.getContentPane().setBackground(Color.orange);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
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

		headerLabel.setText("Directeur");
		this.headerLabel.setFont(new Font(null, Font.BOLD, 27));
		headerLabel.setForeground(Color.white);

		JButton fkButton = new JButton("Stocks");
		JButton billButton = new JButton("Gerer carte du jour");
		JButton afButton = new JButton("Gerer employé");
		JButton dlButton = new JButton("Analyse des ventes ");
		
		fkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Stock();
			}
		});

		billButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GererCarteDuJour gb = new GererCarteDuJour();
			}

		});

		afButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GestionEmploye ef = new GestionEmploye();
				ef.showButtonDemo();
			}
		});

		controlPanel.add(fkButton);
		controlPanel.add(afButton);
		controlPanel.add(billButton);
		controlPanel.add(dlButton);

		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);

	}

}
