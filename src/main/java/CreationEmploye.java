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

import javax.swing.ComboBoxModel;
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
	private JPanel controlPanel;
	private JLabel firstNameEmploye, lastNameEmplye, login, password, listNameEmploye;
	private JComboBox<String> listEmploye, selectNameEmploye;
	GridLayout experimentLayout = new GridLayout(0, 2);
	
	private String employeFirstName, employeLastName,employeLogin,employePassword;

	

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

		String[] items = { "serveur", "cuisinier", "assistant service", "maitre d hôtel" };
		listEmploye = new JComboBox<String>(items);
		
		
		DBConnection con = new DBConnection();
		ResultSet résultats = null;
		String requete = "SELECT * FROM employe WHERE role!='directeur'";

		
		//String[] employeName = { "Serveur", "Cuisinier", "Assistant service", "Maitre d'hôtel" };
		selectNameEmploye = new JComboBox<String>();


		listNameEmploye = new JLabel("Selectionner le nom d'un employe pour le modifier");
		selectNameEmploye.addItem("");
		
		try {
			Statement stmt = con.con.createStatement();
			résultats = stmt.executeQuery(requete);
			String role = null;
			while (résultats.next()) {
				String nameEmploye = résultats.getString("nom") + " " + résultats.getString("prenom");
				selectNameEmploye.addItem(nameEmploye);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		JButton okButton = new JButton("Créer/Modifier");
		JButton deleteButton = new JButton("Supprimer");

		selectNameEmploye.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	
            	
            	if(!selectNameEmploye.getSelectedItem().toString().equals("")) {
            		String[] name = selectNameEmploye.getSelectedItem().toString().split(" ");
            		tf2.setText(name[1]);
            		tf3.setText(name[0]);
            		
            		String requete = "SELECT * FROM employe WHERE nom='"+name[0]+"' and prenom='"+name[1]+"'";
            		try {
            			ResultSet résultats = null;
            			Statement stmt = con.con.createStatement();
            			résultats = stmt.executeQuery(requete);
            			while (résultats.next()) {
            				String login = résultats.getString("login");
            				String motDePasse = résultats.getString("motdepasse");
            				String role = résultats.getString("role");
            			
            				
            				if(role.equals("serveur")) {
            					listEmploye.setSelectedIndex(0);
            				}else if(role.equals("cuisinier")) {
            					listEmploye.setSelectedIndex(1);
            				}else if(role.equals("assistant service")) {
            					listEmploye.setSelectedIndex(2);
            				}else if(role.equals("maitre d'hôtel")) {
            					listEmploye.setSelectedIndex(3);
            				}
            				tf4.setText(login);
            				tf5.setText(motDePasse);
            				deleteButton.setVisible(true);
            				
            			}
            		}catch (Exception e) {
            			// TODO: handle exception
            		}
            		
            		tf2.setEnabled(false);
            		tf3.setEnabled(false);
            		okButton.setText("Modifier");
            	}else {
            		tf2.setText("");
            		tf3.setText("");
            		tf4.setText("");
            		tf5.setText("");
            		tf2.setEnabled(true);
            		tf3.setEnabled(true);
            		okButton.setText("Créer");
            		deleteButton.setVisible(false);
            	}
            }
        });
		
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DBConnection con = new DBConnection();
				PreparedStatement pst;
				
				if(!selectNameEmploye.getSelectedItem().toString().equals("")){
					//MODIFICATION DE L'EMPLOYE
					String[] name = selectNameEmploye.getSelectedItem().toString().split(" ");
            		tf2.setText(name[1]);
            		tf3.setText(name[0]);
            		String requete = "SELECT * FROM employe WHERE nom='"+name[0]+"' and prenom='"+name[1]+"'";
            		try {
            			ResultSet résultats = null;
            			Statement stmt = con.con.createStatement();
            			résultats = stmt.executeQuery(requete);
        				String id="";
            			while (résultats.next()) {
            				id = résultats.getString("idEmploye");
            			}
  
            			pst = con.mkDataBase().prepareStatement(
    							"UPDATE employe SET login='" +tf4.getText() + "', motdepasse='" +tf5.getText()  + "', role='" +listEmploye.getSelectedItem().toString() + "' WHERE idEmploye='" +id + "'");
            			pst.execute();
    					JOptionPane.showMessageDialog(null,"Modification effectué");
            		}catch (Exception e1) {
						System.out.println(e1.getMessage());
						JOptionPane.showMessageDialog(null,"Erreur dans la modification");
					}	
				}else {
					// CREATION D'UN NOUVEL EMPLOYE
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
						e1.printStackTrace();
					}
				}
				try {
					con.con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

			}
		});
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DBConnection con = new DBConnection();
				PreparedStatement pst;
				String[] name = selectNameEmploye.getSelectedItem().toString().split(" ");
        		tf2.setText(name[1]);
        		tf3.setText(name[0]);
        		String requete = "SELECT * FROM employe WHERE nom='"+name[0]+"' and prenom='"+name[1]+"'";
        		try {
        			ResultSet résultats = null;
        			Statement stmt = con.con.createStatement();
        			résultats = stmt.executeQuery(requete);
    				String id="";
        			while (résultats.next()) {
        				id = résultats.getString("idEmploye");
        			}

        			pst = con.mkDataBase().prepareStatement(
							"DELETE FROM employe WHERE idEmploye='" +id + "'");
					
        			//pst.execute();
					JOptionPane.showMessageDialog(null,"Suppression effectué");
					
					// IL FAUT VIDER LES CHAMPS
					tf2.setText("");
					tf3.setText("");
					tf4.setText("");
					tf5.setText("");
					selectNameEmploye.removeItemAt(selectNameEmploye.getSelectedIndex());
					con.con.close();
        		}catch (Exception e1) {
					System.out.println(e1.getMessage());
					JOptionPane.showMessageDialog(null,"Erreur dans la suppression");
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
		jp.add(listNameEmploye);
		jp.add(selectNameEmploye);
		jp.add(listEmploye);

		jp.setSize(500, 500);
		jp.setLayout(experimentLayout);
		controlPanel.add(jp);
		jp.add(okButton);
		jp.add(deleteButton);
		
		deleteButton.setVisible(false);

		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);

	}
}
