package fr.ul.miage.restaurant.menu.sousmenu;
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
import javax.swing.JTextField;

import fr.ul.miage.restaurant.bdd.DBConnection;

public class CreationEmploye {

	private JFrame mainFrame;
	private JPanel controlPanel;
	private JLabel headerLabel, firstNameEmploye, lastNameEmplye, login, password, listNameEmploye;
	private JComboBox<String> listEmploye, selectNameEmploye;
	GridLayout experimentLayout = new GridLayout(0, 2);
	private DBConnection con;

	public CreationEmploye() {
		this.con =  new DBConnection();
		prepareGUI();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Création d'un employé");
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
		headerLabel = new JLabel("Creation d'un employé", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));
		headerLabel.setForeground(Color.white);
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.setVisible(true);
	}
	
	 

	public void showButtonDemo() {
		// Création des champs é remplir
		firstNameEmploye = new JLabel("Entrer prénom employé");
		final JTextField textFieldFirstName = new JTextField();
		textFieldFirstName.setSize(100, 40);

		lastNameEmplye = new JLabel("Entrer nom employé");
		final JTextField textFieldLastName = new JTextField();
		textFieldLastName.setSize(100, 40);

		login = new JLabel("Entrer login employé");
		final JTextField textFieldLogin = new JTextField();
		textFieldLogin.setSize(100, 40);

		password = new JLabel("Entrer mot de passe employé");
		final JTextField textFieldPassword = new JTextField();
		textFieldPassword.setSize(100, 40);

		// JComboBox pour choisir le réle
		String[] items = { "serveur", "cuisinier", "assistant service", "maitre d hôtel" };
		listEmploye = new JComboBox<String>(items);
		// JComboBox pour sélectionner l'employé é modifier
		listNameEmploye = new JLabel("Selectionner le nom d'un employe pour le modifier");
		selectNameEmploye = new JComboBox<String>();
		selectNameEmploye.addItem("");
		
		final JButton okButton = new JButton("Créer/Modifier");
		final JButton deleteButton = new JButton("Supprimer");
		
		ResultSet rs = null;
		String requete = "SELECT * FROM employe WHERE role!='directeur'";
		try {
			Statement stmt = con.con.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				String nameEmploye = rs.getString("nom") + " " + rs.getString("prenom");
				selectNameEmploye.addItem(nameEmploye);
			}
			rs.close();
			stmt.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		// Lorsqu'on change la selection de l'employé
		selectNameEmploye.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	// Si aucun employé n'est sélectionné
            	if(!selectNameEmploye.getSelectedItem().toString().equals("")) {
            		String[] name = selectNameEmploye.getSelectedItem().toString().split(" ");
            		textFieldFirstName.setText(name[1]);
            		textFieldLastName.setText(name[0]);
            		String requete = "SELECT * FROM employe WHERE nom='"+name[0]+"' and prenom='"+name[1]+"'";
            		try {
            			ResultSet rs = null;
            			Statement stmt = con.con.createStatement();
            			rs = stmt.executeQuery(requete);
            			rs.next();
        				String login = rs.getString("login");
        				String motDePasse = rs.getString("motdepasse");
        				String role = rs.getString("role");
        				if(role.equals("serveur")) {
        					listEmploye.setSelectedIndex(0);
        				}else if(role.equals("cuisinier")) {
        					listEmploye.setSelectedIndex(1);
        				}else if(role.equals("assistant service")) {
        					listEmploye.setSelectedIndex(2);
        				}else if(role.equals("maitre d hétel")) {
        					listEmploye.setSelectedIndex(3);
        				}
        				textFieldLogin.setText(login);
        				textFieldPassword.setText(motDePasse);
        				deleteButton.setVisible(true);
        				rs.close();
        				stmt.close();
            		}catch (Exception e) {
            			System.out.println(e.getMessage());
            		}
            		// Rendre impossible la modification du prénom
            		textFieldFirstName.setEnabled(false);
            		textFieldLastName.setEnabled(false);
            		okButton.setText("Modifier");
            	}else {
            		textFieldFirstName.setText("");
            		textFieldLastName.setText("");
            		textFieldLogin.setText("");
            		textFieldPassword.setText("");
            		textFieldFirstName.setEnabled(true);
            		textFieldLastName.setEnabled(true);
            		okButton.setText("Créer");
            		deleteButton.setVisible(false);
            	}
            }
        });
		
		// Lorsqu'on clique sur le bouton Créer ou modifier
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PreparedStatement pst;
				if(!selectNameEmploye.getSelectedItem().toString().equals("")){
					//MODIFICATION DE L'EMPLOYE
					String[] name = selectNameEmploye.getSelectedItem().toString().split(" ");
            		textFieldFirstName.setText(name[1]);
            		textFieldLastName.setText(name[0]);
            		String requete = "SELECT * FROM employe WHERE nom='"+name[0]+"' and prenom='"+name[1]+"'";
            		try {
            			ResultSet rs = null;
            			Statement stmt = con.con.createStatement();
            			rs = stmt.executeQuery(requete);
        				String id="";
            			rs.next();
            			id = rs.getString("idEmploye");
            			pst = con.mkDataBase().prepareStatement(
    							"UPDATE employe SET login='" +textFieldLogin.getText() + "', motdepasse='" +textFieldPassword.getText()  + "', role='" +listEmploye.getSelectedItem().toString() + "' WHERE idEmploye='" +id + "'");
            			pst.execute();
    					JOptionPane.showMessageDialog(null,"Modification effectué");
    					rs.close();
    					stmt.close();
    					pst.close();
            		}catch (Exception e1) {
						System.out.println(e1.getMessage());
						JOptionPane.showMessageDialog(null,"Erreur dans la modification");
					}	
				}else {
					// CREATION D'UN NOUVEL EMPLOYE
					Boolean test = true;
					Boolean alreadyExist = false;
					ResultSet rs = null;
					String requete = "SELECT login FROM employe WHERE login='" + textFieldLogin.getText() + "'";
					try {
						Statement stmt = con.con.createStatement();
						rs = stmt.executeQuery(requete);
						while (rs.next() && !alreadyExist) {
							String login = rs.getString("login");
							// Si le login existe déjé
							if (textFieldLogin.getText().equals(login)) {
								alreadyExist = true;
							}
						} 
						if (textFieldFirstName.getText().equals("") || textFieldLastName.getText().equals("") || textFieldLogin.getText().equals("")
								|| textFieldPassword.getText().equals("") || alreadyExist) {
							test = false;
						}
						pst = con.mkDataBase().prepareStatement(
								"INSERT into employe (nom, prenom, login, motdepasse, role) values (?,?,?,?,?)");
						pst.setString(1, textFieldLastName.getText());
						pst.setString(2, textFieldFirstName.getText());
						pst.setString(3, textFieldLogin.getText());
						pst.setString(4, textFieldPassword.getText());
						pst.setString(5, listEmploye.getSelectedItem().toString());

						if (test) {
							pst.execute();
							JOptionPane.showMessageDialog(null, "Création effectué !");
						} else if(alreadyExist) {
							JOptionPane.showMessageDialog(null,
									"Impossible de créer l'employe (Le login existe déjé)");
						}else {
							JOptionPane.showMessageDialog(null,
									"Impossible de créer l'employe (certain champs sont peut-étre vide)");
						}
						for(int i=selectNameEmploye.getItemCount()-1;i>0;i--){
							selectNameEmploye.removeItemAt(i);
						}
						ResultSet rs1 = null;
						String requete1 = "SELECT * FROM employe WHERE role!='directeur'";
						Statement stmt1 = con.con.createStatement();
						rs1 = stmt1.executeQuery(requete1);
						while (rs1.next()) {
							String nameEmploye = rs1.getString("nom") + " " + rs1.getString("prenom");
							selectNameEmploye.addItem(nameEmploye);
						}
						textFieldFirstName.setText("");
						textFieldLastName.setText("");
						textFieldLogin.setText("");
						textFieldPassword.setText("");
						rs.close();
						stmt.close();
						rs1.close();
						stmt1.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
				

			}
		});
		//Lorsqu'on clique sur le bouton supprimer
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] name = selectNameEmploye.getSelectedItem().toString().split(" ");
        		textFieldFirstName.setText(name[1]);
        		textFieldLastName.setText(name[0]);
        		String requete = "SELECT * FROM employe WHERE nom='"+name[0]+"' and prenom='"+name[1]+"'";
        		try {
        			ResultSet rs = null;
        			Statement stmt = con.con.createStatement();
        			rs = stmt.executeQuery(requete);
    				String id="";
        			while (rs.next()) {
        				id = rs.getString("idEmploye");
        			}
        			PreparedStatement pst;
        			pst = con.mkDataBase().prepareStatement("DELETE FROM employe WHERE idEmploye='" +id + "'");
        			pst.execute();
					JOptionPane.showMessageDialog(null,"Suppression effectué");
					// IL FAUT VIDER LES CHAMPS
					textFieldFirstName.setText("");
					textFieldLastName.setText("");
					textFieldLogin.setText("");
					textFieldPassword.setText("");
					selectNameEmploye.removeItemAt(selectNameEmploye.getSelectedIndex());
					rs.close();
					stmt.close();
        		}catch (Exception e1) {
					System.out.println(e1.getMessage());
					JOptionPane.showMessageDialog(null,"Erreur dans la suppression");
				}	
			}
		});
		// Ajout de tous les éléments
		JPanel jp = new JPanel(null);
		jp.add(firstNameEmploye);
		jp.add(textFieldFirstName);
		jp.add(lastNameEmplye);
		jp.add(textFieldLastName);
		jp.add(login);
		jp.add(textFieldLogin);
		jp.add(password);
		jp.add(textFieldPassword);
		jp.add(listNameEmploye);
		jp.add(selectNameEmploye);
		jp.add(listEmploye);
		jp.setSize(500, 500);
		jp.setLayout(experimentLayout);
		jp.add(okButton);
		jp.add(deleteButton);
		controlPanel.add(jp);
		deleteButton.setVisible(false);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);

	}
}
