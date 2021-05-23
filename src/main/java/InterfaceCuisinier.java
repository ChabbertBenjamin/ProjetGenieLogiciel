import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.Color;

public class InterfaceCuisinier {

   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;
   

   public InterfaceCuisinier(){
	
	
      prepareGUI();
		
   }

   public static void main(String[] args){
	   InterfaceCuisinier swingControlDemo = new InterfaceCuisinier();
      swingControlDemo.showButtonDemo();
	  
   }

   private void prepareGUI(){
      mainFrame = new JFrame("Cuisinier");
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

		headerLabel.setText("Cuisinier");
		this.headerLabel.setFont(new Font(null, Font.BOLD, 27));
		headerLabel.setForeground(Color.white);
		
		
		JButton billButton = new JButton("Visualiser commandes");
		JButton afButton = new JButton("Definir plat");
		

		
        billButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e)
{
            

        	 VisualiserCommande gb=new VisualiserCommande();}

});


        
      afButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e)
{
            

            DefinirPlat ef=new DefinirPlat();
            ef.showButtonDemo();
         }
});


      controlPanel.add(afButton);
	  controlPanel.add(billButton);


		  
      mainFrame.setVisible(true);
	  mainFrame.setLocationRelativeTo(null);
	  
   }
   
   
}
