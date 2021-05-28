package fr.ul.miage.restaurant.menu.sousmenu;

import java.awt.Color;
import java.awt.FlowLayout;
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

import fr.ul.miage.restaurant.models.Table;

public class DetailTable {
	private JFrame mainFrame;
	   private JLabel headerLabel;
	   private JPanel controlPanel;	
	   private JLabel  lidTable,lstatut,lnbcouverts,letage,lidEmploye;
	   private JButton btnRetour,btnAjout;
	   GridLayout experimentLayout = new GridLayout(0,2);
	public DetailTable(){


	    prepareGUI();
	   }
	 private void prepareGUI(){
	      mainFrame = new JFrame("Détails de la table");
	      mainFrame.setSize(700,600);
	      mainFrame.setLayout(new GridLayout(3, 1));
		  
		  mainFrame.getContentPane().setBackground(Color.gray);
		  
	      mainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
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
	 public void showButtonDemo(Table table){

			headerLabel.setText("Détails de la table");
			headerLabel.setFont(new Font(null, Font.BOLD, 27));
	        lidTable = new JLabel("Numéro : " + table.getIdtable());
	        lstatut = new JLabel("Statut : "+table.getStatut());
	        lnbcouverts = new JLabel("Nombre de couverts : "+table.getNbcouverts());
	        letage = new JLabel("Etage : " +table.getEtage());	       	        
	        lidEmploye = new JLabel("Employé(e) affecté(e) : "+table.getIdemploye());
	        btnRetour = new JButton("Retour");
	        btnAjout = new JButton("Saisir une commande");
	        
	        
	        JPanel jp = new JPanel(null);
	        jp.add(lidTable);
	        jp.add(lstatut);
	        jp.add(lnbcouverts);
	        jp.add(letage);
	        jp.add(lidEmploye);
	        jp.add(btnRetour);
	        jp.add(btnAjout);
	        
	        jp.setSize(500,500);
	        jp.setLayout(experimentLayout);
	        controlPanel.add(jp);
	        btnRetour.addActionListener(new ActionListener() {
      	         public void actionPerformed(ActionEvent e)
      	         {
      	        	
      	        	 mainFrame.setVisible(false);
      	        	
      	        	 }

      	});
	        btnAjout.addActionListener(new ActionListener() {
      	         public void actionPerformed(ActionEvent e)
      	         {
      	        	 SaisirCommande sc=new SaisirCommande();
    	             sc.showButtonDemo();
      	  
      	        	
      	        	 }

      	});
	 }
}
