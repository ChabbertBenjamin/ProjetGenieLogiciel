package fr.ul.miage.restaurant.menu.direteur;

import fr.ul.miage.restaurant.bdd.DBConnection;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

public  abstract class AbstractButtonRecette extends JButton { 
	
	
	private final InterfaceDirecteur parent;
	
	public AbstractButtonRecette(String text, InterfaceDirecteur parent) {
		super(text);
		this.parent = parent;
		addActionListener(event -> {
			try {
				DBConnection.connection();
				Statement statement = DBConnection.con.createStatement();
				ResultSet resultSet = statement.executeQuery(getQuery());
				if (resultSet.next()) {
					String nomPlat = resultSet.getString("nom");
					JOptionPane.showMessageDialog(parent.getControlPanel(),
							MessageFormat.format(getMessage(), nomPlat));
				} else {
					JOptionPane.showMessageDialog(parent.getControlPanel(),
							"Aucune commande n'est encore saisie", "pas de commande",JOptionPane.ERROR_MESSAGE);
				}
				
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		});
	}
	
	protected abstract String getMessage();
	
	
	protected abstract String getQuery();
	
	
}
