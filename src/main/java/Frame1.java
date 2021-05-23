
import java.awt.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;


public class Frame1 extends JFrame{

    JLabel idLabel;
	JLabel background;
	JLabel headerLabel;
	JLabel devInfo;
	
    JTextField id;
  
    JButton submit;


   public Frame1(){
		setTitle("Interface de connexion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		
		//Background
		this.background = new JLabel(new ImageIcon("C:\\Users\\Asus\\Downloads\\Java-Project-Final (1)\\Java Project Final\\image\\burger.jpg"));

		
		this.init();

		add(background);
		background.setVisible(true);  
        this.pack();
		this.setSize(700,400);
		this.setLocationRelativeTo(null);
    }



    public void init(){
		headerLabel = new JLabel();
		this.headerLabel.setText("Connexion");
		this.headerLabel.setBounds(270,1,200,100);
		this.headerLabel.setFont(new Font("Geomanist", Font.BOLD, 25));
		headerLabel.setForeground(Color.white);
		add(headerLabel);
		
		
		idLabel = new JLabel();
		this.idLabel.setText("Identidiant");
		this.idLabel.setBounds(190,110,100,50);
		this.idLabel.setFont(new Font(null, Font.BOLD, 20));
		idLabel.setForeground(Color.white);
		add(idLabel);
		
      
		
		devInfo = new JLabel();
		this.devInfo.setText("© all Rights Reserved by   -  Aichetou    ||    ");
		this.devInfo.setBounds(130,300,1000,30);
		this.devInfo.setFont(new Font("Geomanist", Font.PLAIN, 15));
		devInfo.setForeground(Color.white);
		add(devInfo);
			
			
		id=new JTextField();
		this.id.setBounds(300,125,200,30);
		add(id);
		
        
		this.id.setVisible(true);
	  
       this.submit=new JButton("Login");
	   this.submit.setBounds(400,230,100,25);
	   add(submit);
	   
       submit.addActionListener(this::submitActionPerformed);


    } 



   public void submitActionPerformed(java.awt.event.ActionEvent evt){

   if(id.getText().equals("serveur")){

     this.hide();
     InterfaceServeur fn=new InterfaceServeur();
     fn.showButtonDemo();

   }else{
	   if(id.getText().equals("directeur")){
		   this.hide();
		   InterfaceDirecteur fn=new InterfaceDirecteur();
		   fn.showButtonDemo();
	   }else{
		   if(id.getText().equals("cuisinier")){
			   this.hide();
			   InterfaceCuisinier fn=new InterfaceCuisinier();
			   fn.showButtonDemo();
		   }else {
			   JOptionPane.showMessageDialog(null, "Invalid identifiant");
   }

   }

    
   }

   }



}
class MyGui{
	public static void main(String[] a){
		Frame1 f = new Frame1();
		f.setVisible(true);
		
	}
}
