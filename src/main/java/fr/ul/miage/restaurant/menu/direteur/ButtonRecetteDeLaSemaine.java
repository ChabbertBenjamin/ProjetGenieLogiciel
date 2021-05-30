package fr.ul.miage.restaurant.menu.direteur;

import fr.ul.miage.restaurant.bdd.DBConnection;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

public class ButtonRecetteDeLaSemaine extends JButton {
	String query = "select count(*), p.nom from commande com inner join plat p on com.idplat =p.idplat\n"
			+ " where DATE(com.dateheurecommande) = CURRENT_DATE"
			+ " group by p.idplat order by count(*) desc limit 1";
	private final InterfaceDirecteur parent;
	
	public ButtonRecetteDeLaSemaine(InterfaceDirecteur parent) {
		super("Recette de la semaine");
		this.parent = parent;
		addActionListener(event -> {
			try {
				DBConnection.connection();
				Statement statement = DBConnection.con.createStatement();
				ResultSet resultSet = statement.executeQuery(query);
				if (resultSet.next()) {
					String nomDuPlatDuJour = resultSet.getString("nom");
					JOptionPane.showMessageDialog(parent.getControlPanel(),
							MessageFormat.format("Le plat du jour est : {0}", nomDuPlatDuJour));
				}
				
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		});
	}
	
	
}
