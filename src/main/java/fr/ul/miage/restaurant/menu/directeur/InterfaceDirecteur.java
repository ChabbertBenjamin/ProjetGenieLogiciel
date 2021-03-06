package fr.ul.miage.restaurant.menu.directeur;


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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import fr.ul.miage.restaurant.bdd.DBConnection;
import fr.ul.miage.restaurant.models.Stock;


public class InterfaceDirecteur {

	private JFrame mainFrame;
	private JPanel controlPanel;
	
	PaneauRecettes paneauRecettes;
	
	
	

	public InterfaceDirecteur() {
		mainFrame = new JFrame("Directeur");
		init();
		launch();
		mainFrame.setVisible(true);
	}

	private void init() {
		mainFrame.setBounds(100, 100, 700, 400);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.getContentPane().setBackground(Color.orange);

		JLabel headerLabel = new JLabel("Directeur", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));
		headerLabel.setForeground(Color.white);

		controlPanel = new JPanel();
		paneauRecettes = new PaneauRecettes();
		
		controlPanel.setLayout(new GridLayout(1, 5));

		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(paneauRecettes);
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

		JButton fkButton = new JButton("Stocks");
		JButton billButton = new JButton("Gerer carte du jour");
		JButton afButton = new JButton("Gerer employé");
		JButton dlButton = new JButton("Analyse des ventes");
		JButton recetteDuJour = new ButtonRecetteDuJour(this);
		JButton recetteDeLaSemaine = new ButtonRecetteDeLaSemaine(this);
		JButton recetteDuMois = new ButtonRecetteDuMois(this);


		fkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				voirStock();
			}
		});

		billButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gererCarteDuJour();
			}
		});

		afButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creationEmploye();
			}
		});

		controlPanel.add(fkButton);
		controlPanel.add(afButton);
		controlPanel.add(billButton);
		controlPanel.add(dlButton);
		
		
		paneauRecettes.add(recetteDuJour);
		paneauRecettes.add(recetteDeLaSemaine);
		paneauRecettes.add(recetteDuMois);


	}
	
	public void gererCarteDuJour() {
		
	}
	
	public JComboBox<String> remplirNomListEmploye(){
		JComboBox<String> selectNameEmploye = new JComboBox<String>();
		selectNameEmploye.addItem("");
		ResultSet rs = null;
		String requete = "SELECT * FROM employe WHERE role!='directeur'";
		try {
			Statement stmt = DBConnection.con.createStatement();
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
		return selectNameEmploye;
	}
	
	public void creationEmploye() {
		final JFrame createEmployeFrame = new JFrame("Création d'un employé");
		createEmployeFrame.setSize(700, 600);
		createEmployeFrame.setLayout(new GridLayout(3, 1));
		createEmployeFrame.getContentPane().setBackground(Color.gray);

		JLabel headerLabel = new JLabel("Creation d'un employé", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));
		headerLabel.setForeground(Color.white);
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		createEmployeFrame.add(headerLabel);
		createEmployeFrame.add(controlPanel);
		
		// Création des champs à remplir
		JLabel firstNameEmploye = new JLabel("Entrer prénom employé");
		final JTextField textFieldFirstName = new JTextField();

		JLabel lastNameEmplye = new JLabel("Entrer nom employé");
		final JTextField textFieldLastName = new JTextField();

		JLabel login = new JLabel("Entrer login employé");
		final JTextField textFieldLogin = new JTextField();

		JLabel password = new JLabel("Entrer mot de passe employé");
		final JTextField textFieldPassword = new JTextField();

		// JComboBox pour choisir le réle
		String[] items = { "serveur", "cuisinier", "assistant service", "maitre d hôtel" };
		final JComboBox<String> listRole = new JComboBox<String>(items);
		// JComboBox pour sélectionner l'employé é modifier
		JLabel listNameEmploye = new JLabel("Selectionner le nom d'un employe pour le modifier");
		final JComboBox<String> selectNameEmploye = remplirNomListEmploye();

		final JButton okButton = new JButton("Créer/Modifier");
		final JButton deleteButton = new JButton("Supprimer");
		
		// Lorsqu'on change la selection de l'employé
		selectNameEmploye.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	// Si aucun employé n'est sélectionné
            	if(!selectNameEmploye.getSelectedItem().toString().equals("")) {
            		String[] name = selectNameEmploye.getSelectedItem().toString().split(" ");
            		textFieldFirstName.setText(name[1]);
            		textFieldLastName.setText(name[0]);
            		String requete = "SELECT login, motdepasse, role FROM employe WHERE nom='"+name[0]+"' and prenom='"+name[1]+"'";
            		try {
            			ResultSet rs = null;
            			Statement stmt = DBConnection.con.createStatement();
            			rs = stmt.executeQuery(requete);
            			rs.next();
        				String login = rs.getString("login");
        				String motDePasse = rs.getString("motdepasse");
        				String role = rs.getString("role");
        				if(role.equals("serveur")) {
        					listRole.setSelectedIndex(0);
        				}else if(role.equals("cuisinier")) {
        					listRole.setSelectedIndex(1);
        				}else if(role.equals("assistant service")) {
        					listRole.setSelectedIndex(2);
        				}else if(role.equals("maitre d hôtel")) {
        					listRole.setSelectedIndex(3);
        				}
        				textFieldLogin.setText(login);
        				textFieldPassword.setText(motDePasse);
        				deleteButton.setVisible(true);
                		// Rendre impossible la modification du prénom
                		textFieldFirstName.setEnabled(false);
                		textFieldLastName.setEnabled(false);
                		okButton.setText("Modifier");
        				rs.close();
        				stmt.close();
            		}catch (Exception e) {
            			System.out.println(e.getMessage());
            		}
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
            			Statement stmt = DBConnection.con.createStatement();
            			rs = stmt.executeQuery(requete);
        				String id="";
            			rs.next();
            			id = rs.getString("idEmploye");
            			pst = DBConnection.con.prepareStatement(
    							"UPDATE employe SET login='" +textFieldLogin.getText() + "', motdepasse='" +textFieldPassword.getText()  + "', role='" +listRole.getSelectedItem().toString() + "' WHERE idEmploye='" +id + "'");
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
						Statement stmt = DBConnection.con.createStatement();
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
						pst = DBConnection.con.prepareStatement("INSERT into employe (nom, prenom, login, motdepasse, role) values (?,?,?,?,?)");
						pst.setString(1, textFieldLastName.getText());
						pst.setString(2, textFieldFirstName.getText());
						pst.setString(3, textFieldLogin.getText());
						pst.setString(4, textFieldPassword.getText());
						pst.setString(5, listRole.getSelectedItem().toString());

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
						rs.close();
						stmt.close();
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}finally {
						createEmployeFrame.dispose();
						creationEmploye();
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
        			Statement stmt = DBConnection.con.createStatement();
        			rs = stmt.executeQuery(requete);
    				String id="";
        			while (rs.next()) {
        				id = rs.getString("idEmploye");
        			}
        			PreparedStatement pst;
        			pst = DBConnection.con.prepareStatement("DELETE FROM employe WHERE idEmploye='" +id + "'");
        			pst.execute();
					JOptionPane.showMessageDialog(null,"Suppression effectué");
					rs.close();
					stmt.close();
        		}catch (Exception e1) {
					System.out.println(e1.getMessage());
					JOptionPane.showMessageDialog(null,"Erreur dans la suppression");
				}finally {
					createEmployeFrame.dispose();
					creationEmploye();
				}
			}
		});
		// Ajout de tous les éléments
		GridLayout experimentLayout = new GridLayout(0, 2);
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
		jp.add(listRole);
		jp.setSize(500, 500);
		jp.setLayout(experimentLayout);
		jp.add(okButton);
		jp.add(deleteButton);
		controlPanel.add(jp);
		deleteButton.setVisible(false);
		createEmployeFrame.setLocationRelativeTo(null);
		createEmployeFrame.setVisible(true);
	}
	
	public void voirStock() {
		final JFrame stockFrame = new JFrame("Stock");
		stockFrame.setSize(600, 550);

		JPanel jp2 = new JPanel();
		jp2.setSize(400, 400);

		ArrayList<Stock> stock = getData();
		Object [] col = {"Nom","Quantité"};
		Object[][] data= new Object[stock.size()][2];
        for(int i=0;i<stock.size();i++){
       	 data[i][0] = stock.get(i).getNom();
         data[i][1] = stock.get(i).getQuantite();
        }
        JTable cart = new JTable(data, col);

		cart.setSize(300, 450);
		jp2.setLayout(new FlowLayout());

		jp2.add(new JScrollPane(cart, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

		JButton buy = new JButton("Acheter des matiéres premiéres");
		buy.setSize(40, 50);
		jp2.add(buy);

		buy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stockFrame.dispose();
				BuyStock();
			}
		});

		stockFrame.add(jp2);
		stockFrame.setLocationRelativeTo(null);
		stockFrame.setVisible(true);
		cart.setEnabled(false);
	}
	public ArrayList<Stock> getData() {
		ResultSet rs = null;
		ArrayList<Stock> stock = new ArrayList<Stock>() ;
		String requete = "SELECT * FROM matierepremiere";
		try {
			Statement stmt = DBConnection.con.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				stock.add(new Stock(rs.getString("nom"),rs.getInt("quantite")));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return stock;
	}
	
	
	public void BuyStock() {
		final JFrame buyFrame = new JFrame("Acheter");
		buyFrame.setSize(600, 400);
		buyFrame.setLayout(new GridLayout(3, 1));
		buyFrame.getContentPane().setBackground(Color.gray);

		JLabel headerLabel = new JLabel("Acheter matiére premiére", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));
		headerLabel.setForeground(Color.white);
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		buyFrame.setLocationRelativeTo(null);
		buyFrame.add(headerLabel);
		buyFrame.add(controlPanel);
		buyFrame.setVisible(true);

		// JComboBox pour sélectionner l'employé é modifier
		JComboBox<String> list = new JComboBox<String>();
		final JComboBox<String> listMatierePremiere = getListMatierePremiere(list);

		JLabel quantite = new JLabel(" Entrer quantité ");
		final JTextField textFieldQuantite = new JTextField();
		textFieldQuantite.setSize(100, 40);

		JButton buy = new JButton("Acheter");
		buy.setSize(40, 50);

		buy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String nom = (String) listMatierePremiere.getSelectedItem();
				if (!textFieldQuantite.getText().equals("")) {
					try {
						String requete = "SELECT quantite FROM matierepremiere WHERE nom='" + nom + "'";
						ResultSet rs = null;
						Statement stmt = DBConnection.con.createStatement();
						rs = stmt.executeQuery(requete);
						int quantiteDB = 0;
						while (rs.next()) {
							quantiteDB = rs.getInt("quantite");
						}
						int intQuantite = Integer.parseInt(textFieldQuantite.getText()) + quantiteDB;
						if (Integer.parseInt(textFieldQuantite.getText()) > 0) {
							PreparedStatement pst;
							pst = DBConnection.con.prepareStatement(
									"UPDATE matierepremiere SET quantite=" + intQuantite + " WHERE nom='" + nom + "'");
							pst.execute();
							JOptionPane.showMessageDialog(null, "Achat effectué");
						} else {

							JOptionPane.showMessageDialog(null, "Impossible d'acheter une quantité negative");
						}
					} catch (Exception e1) {
						System.out.println(e1.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Insérer une quantité é acheter");
				}
			}
		});

		JButton newMatierePremiere = new JButton("Gérer matiére premiére");
		newMatierePremiere.setSize(40, 50);
		newMatierePremiere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buyFrame.dispose();
				newMatierePremiere();
			}
		});

		JButton back = new JButton("Retour à la visualisation");
		back.setSize(40, 50);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buyFrame.dispose();
				voirStock();
			}
		});
		GridLayout experimentLayout = new GridLayout(0, 3);
		JPanel jp = new JPanel(null);
		jp.add(listMatierePremiere);
		jp.add(quantite);
		jp.add(textFieldQuantite);
		jp.add(buy);
		jp.add(newMatierePremiere);
		jp.setSize(500, 500);
		jp.setLayout(experimentLayout);
		buyFrame.add(back);
		controlPanel.add(jp);
		
		buyFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				voirStock();
			}
		});

	}
	
	public void newMatierePremiere() {
		final JFrame manageFrame = new JFrame("gérer");
		manageFrame.setSize(600, 400);
		manageFrame.setLayout(new GridLayout(3, 1));
		manageFrame.getContentPane().setBackground(Color.gray);

		JLabel headerLabel = new JLabel("Créer/Modifier matiére premiére", JLabel.CENTER);
		headerLabel.setFont(new Font(null, Font.BOLD, 27));
		headerLabel.setForeground(Color.white);
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		manageFrame.setLocationRelativeTo(null);
		manageFrame.add(headerLabel);
		manageFrame.add(controlPanel);
		manageFrame.setVisible(true);

		JLabel nom = new JLabel(" Entrer nom ");
		final JTextField nomMatierePremiere = new JTextField();
		nomMatierePremiere.setSize(100, 40);
		
		JComboBox<String> list = new JComboBox<String>();
		list.addItem("");
		final JComboBox<String> listMatierePremiere = getListMatierePremiere(list);

		final JButton modif = new JButton("Créer");
		modif.setSize(40, 50);
		modif.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listMatierePremiere.getSelectedItem().toString().equals("")) {
					// Requete de création
					PreparedStatement pst;
					try {
						pst = DBConnection.con.prepareStatement("INSERT INTO matierepremiere (nom, quantite) values (?,0)");
						pst.setString(1, nomMatierePremiere.getText());
						pst.execute();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Création effectué");
				} else {
					// Requete de modification
					PreparedStatement pst;
					try {
						pst = DBConnection.con.prepareStatement("UPDATE matierepremiere SET nom='"+nomMatierePremiere.getText()+"' WHERE nom='"+listMatierePremiere.getSelectedItem().toString()+"'");
						pst.execute();
						JOptionPane.showMessageDialog(null, "Modification effectué");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				manageFrame.dispose();
				BuyStock();
			}
		});

		// Lorsqu'on change la selection de la matiére premiére
		listMatierePremiere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Si aucune matiére premiére n'est sélectionné
				if (listMatierePremiere.getSelectedItem().toString().equals("")) {
					nomMatierePremiere.setText("");
					modif.setText("Créer");
				} else {
					nomMatierePremiere.setText((String) listMatierePremiere.getSelectedItem());
					modif.setText("Modifier");
				}
			}
		});
		GridLayout experimentLayout = new GridLayout(0, 3);
		JPanel jp = new JPanel(null);
		jp.add(listMatierePremiere);
		jp.add(nom);
		jp.add(nomMatierePremiere);
		jp.add(modif);
		jp.setSize(500, 500);
		jp.setLayout(experimentLayout);
		controlPanel.add(jp);
		
		manageFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				BuyStock();
			}
		});
	}
	
	public JComboBox<String> getListMatierePremiere(JComboBox<String> list) {
		ResultSet rs = null;
		String requete = "SELECT nom FROM matierepremiere";
		try {
			Statement stmt = DBConnection.con.createStatement();
			rs = stmt.executeQuery(requete);
			while (rs.next()) {
				String name = rs.getString("nom");
				list.addItem(name);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return list;
	}
	
	public JPanel getControlPanel() {
		return controlPanel;
	}
}
