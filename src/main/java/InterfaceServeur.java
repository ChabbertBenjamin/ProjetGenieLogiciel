import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import java.awt.Color;

public class InterfaceServeur {

   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;
   JTable cart;
   String[] columnNames = {"idTable  ",
			"  statut  ",	
			"  nbCouverts  ",
			"  etage  ",
			"idemploye"};
   
   Object data[][] = new Object[100][5];
   int i =0;
   

   public InterfaceServeur(){
	
	
      prepareGUI();
		
   }

   public static void main(String[] args){
	   InterfaceServeur  swingControlDemo = new InterfaceServeur();
      swingControlDemo.showButtonDemo();
	  
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


   public void showButtonDemo(){

		headerLabel.setText("Serveur");
		this.headerLabel.setFont(new Font(null, Font.BOLD, 27));
		headerLabel.setForeground(Color.white);
		  try{	
	        	 DBConnection con = new DBConnection();
	             Statement stmt =  con.mkDataBase().createStatement();
	             ResultSet rs = stmt.executeQuery("select idtable, statut, nbcouverts, etage, idemploye from tables");
	             while (rs.next()){
	            	data[i][0] = rs.getInt("idtable"); 
	                data[i][1] = rs.getString("statut");
	                data[i][2] = rs.getInt("nbcouverts"); 
	                data[i][3] = rs.getInt("etage"); 
	                data[i][4] = rs.getInt("idemploye"); 
	                i++;
	             }
	             cart = new JTable(data, columnNames);
	             controlPanel.add(cart);
	       
	   
	             
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


      controlPanel.add(afButton);
	  controlPanel.add(billButton);
	  //controlPanel.add(dlButton);

		  
      mainFrame.setVisible(true);
	  mainFrame.setLocationRelativeTo(null);
	  
   }
   
   
}
