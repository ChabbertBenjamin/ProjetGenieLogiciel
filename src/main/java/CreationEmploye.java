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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreationEmploye {

	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JLabel firstNameEmploye, lastNameEmplye, login, password;
	private JComboBox<String> listEmploye;
	GridLayout experimentLayout = new GridLayout(0, 2);

	public CreationEmploye() {

		prepareGUI();

	}

	private void prepareGUI() {
		mainFrame = new JFrame("Création d'un employé");
		mainFrame.setSize(700, 600);
		mainFrame.setLayout(new GridLayout(3, 1));

		mainFrame.getContentPane().setBackground(Color.gray);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				mainFrame.setVisible(false);
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

		headerLabel.setText("Creation d'un employé");
		this.headerLabel.setFont(new Font(null, Font.BOLD, 27));
		headerLabel.setForeground(Color.white);

		firstNameEmploye = new JLabel("Entrer prénom employé");
		final JTextField tf2 = new JTextField();
		tf2.setSize(100, 40);

		lastNameEmplye = new JLabel("Entrer nom employé");
		final JTextField tf3 = new JTextField();
		tf3.setSize(100, 40);

		login = new JLabel("Entrer login employé");
		final JTextField tf4 = new JTextField();
		tf4.setSize(100, 40);

		password = new JLabel("Entrer mot de passe employé");
		final JTextField tf5 = new JTextField();
		tf5.setSize(100, 40);

		String[] items = { "Serveur", "Cuisinier", "Assistant service", "Maitre d'hôtel" };
		listEmploye = new JComboBox(items);

		JButton okButton = new JButton("OK");

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DBConnection con = new DBConnection();
				PreparedStatement pst;
				Boolean test = true;
				Boolean alreadyExist = false;
				ResultSet résultats = null;
				String requete = "SELECT login FROM employe WHERE login='" + tf4.getText() + "'";
				
				
				try {
					Statement stmt = con.con.createStatement();
					résultats = stmt.executeQuery(requete);
					while (résultats.next() && !alreadyExist) {
						String login = résultats.getString("login");
						// Si le login existe déjà
						if (tf4.getText().equals(login)) {
							alreadyExist = true;
						}
					} 
					if (tf2.getText().equals("") || tf3.getText().equals("") || tf4.getText().equals("")
							|| tf5.getText().equals("") || alreadyExist) {
						test = false;
					}
					pst = con.mkDataBase().prepareStatement(
							"INSERT into employe (nom, prenom, login, motdepasse, role) values (?,?,?,?,?)");
					pst.setString(1, tf3.getText());
					pst.setString(2, tf2.getText());
					pst.setString(3, tf4.getText());
					pst.setString(4, tf5.getText());
					pst.setString(5, listEmploye.getSelectedItem().toString());

					if (test) {
						pst.execute();
						JOptionPane.showMessageDialog(null, "Création effectué !");
					} else if(alreadyExist) {
						JOptionPane.showMessageDialog(null,
								"Impossible de créer l'employe (Le login existe déjà)");
					}else {
						JOptionPane.showMessageDialog(null,
								"Impossible de créer l'employe (certain champs sont peut-être vide)");
					}

					tf2.setText("");
					tf3.setText("");
					tf4.setText("");
					tf5.setText("");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
 
		JPanel jp = new JPanel(null);
		jp.add(firstNameEmploye);
		jp.add(tf2);
		jp.add(lastNameEmplye);
		jp.add(tf3);
		jp.add(login);
		jp.add(tf4);
		jp.add(password);
		jp.add(tf5);
		jp.add(listEmploye);

		jp.setSize(500, 500);
		jp.setLayout(experimentLayout);
		// controlPanel.add(listEmploye);
		controlPanel.add(jp);
		jp.add(okButton);

		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);

	}
}
