package fr.ul.miage.restaurant.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.ul.miage.restaurant.bdd.DBConnection;
import fr.ul.miage.restaurant.menu.sousmenu.DetailTable;
import fr.ul.miage.restaurant.menu.sousmenu.ModifierCommande;
import fr.ul.miage.restaurant.menu.sousmenu.SaisirCommande;
import fr.ul.miage.restaurant.models.Table;

public class InterfaceServeur {

   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;
   int i =0;
   int j=0;
   
   
   public InterfaceServeur(){
	
	
      prepareGUI();
		
   }

   private void prepareGUI(){
      mainFrame = new JFrame("Serveur");
      mainFrame.setBounds(100,100,700,400);
      mainFrame.setLayout(new GridLayout(3,1));
      
      

	 mainFrame.getContentPane().setBackground(Color.orange);
	
	 
	  
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
		
		
         }
      });
      headerLabel = new JLabel("", JLabel.CENTER);
      statusLabel = new JLabel("",JLabel.CENTER);

      statusLabel.setSize(350,300);

      controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(1,5));
	  
	 
      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);
	
   }


   public void showButtonDemo(int idemploye){
 
		headerLabel.setText("Serveur");
		this.headerLabel.setFont(new Font(null, Font.BOLD, 27));
		headerLabel.setForeground(Color.white);
		
       
		 
        try{	
        	 DBConnection con = new DBConnection();
             Statement stmt =  con.mkDataBase().createStatement();
             ArrayList<Table> listeTable = new ArrayList<Table>();
             ArrayList<JButton> listButton = new ArrayList();
             //On récupere les tables correspondant à l'employé
             ResultSet rs = stmt.executeQuery("select idtable, statut, nbcouverts, etage, idemploye from tables WHERE idemploye ="+idemploye);
             while (rs.next()){
            	listeTable.add(new Table(rs.getInt("idtable"),rs.getString("statut"),rs.getInt("nbcouverts"),rs.getInt("etage"),rs.getInt("idemploye")));
            	JButton buttonTable = new JButton("Table numéro : "+rs.getInt("idtable"));
            	if(rs.getString("statut").equals("propre")) {
            	buttonTable.setBackground(Color.GREEN);
            	}
            	if(rs.getString("statut").equals("occupe")) {
                	buttonTable.setBackground(Color.YELLOW);
            	}
            	if(rs.getString("statut").equals("sale")) {
                	buttonTable.setBackground(Color.RED);
            	}
            	if(rs.getString("statut").equals("reserve")) {
                	buttonTable.setBackground(Color.ORANGE);
            	}
                listButton.add(buttonTable);	
            	controlPanel.add(buttonTable);
            	i++;
            	buttonTable.addActionListener(new ActionListener() {
       	         public void actionPerformed(ActionEvent e)
       	         {
       	        	
       	  
       	        	 DetailTable mc=new DetailTable();
    	             mc.showButtonDemo(listeTable.get(retourIndexButton(listButton,buttonTable)));
       	        	 }

       	});
            	  
             }
             for(int j=0; j < listButton.size();j++){
            	 
            	 controlPanel.add(listButton.get(j));
            	
             }
             
           
       
   
             
        }
         catch(Exception ex){
             System.out.println(ex);
         }
   
        
         
		
		
		JButton billButton = new JButton("Saisir la commande");
		
		JButton afButton = new JButton("Modifier une commande");
		
        billButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e)
{
            

        	 SaisirCommande gb=new SaisirCommande();
        	 gb.showButtonDemo();}

});


        
      afButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e)
{
            

            ModifierCommande ef=new ModifierCommande();
            //ef.showButtonDemo();
         }
});


      //controlPanel.add(afButton);
	  //controlPanel.add(billButton);
	  //controlPanel.add(dlButton);

		  
      mainFrame.setVisible(true);
	  mainFrame.setLocationRelativeTo(null);
	  
   }
//Retourne l'index du boutton pour la table destiné.
private int retourIndexButton(ArrayList<JButton> buttonList,JButton button) {
	for(int i =0; i<buttonList.size();i++) {
		if(buttonList.get(i).equals(button)) {
			return i;
		}	
	}	
	return 0;
}
 
   
}