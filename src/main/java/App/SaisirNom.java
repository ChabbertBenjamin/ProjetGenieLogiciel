package App;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class SaisirNom {

   private JFrame mainFrame;
   private JLabel headerLabel;
   private JPanel controlPanel;
   private JLabel nom;
   GridLayout experimentLayout = new GridLayout(0,2);
    ResultSet rs;

    SaisirNom(){


    prepareGUI();
   }

   public static void main(String[] args){
	   SaisirNom  swingControlDemo = new SaisirNom();
      swingControlDemo.showButtonDemo();
   }

   private void prepareGUI(){
      mainFrame = new JFrame("Saisir votre nom");
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


   public void showButtonDemo(){

		headerLabel.setText("Saisir votre nom");
		headerLabel.setFont(new Font(null, Font.BOLD, 27));

        nom = new JLabel("Entrer nom");
        final JTextField tf2=new JTextField();
        tf2.setSize(100,40);
        
        JButton okButton = new JButton("OK");


    okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            PreparedStatement pst;
            DBConnection con = new DBConnection();
            try{
                pst = con.mkDataBase().prepareStatement("insert into Projet_GL.employe(nom,prenom,login,role) values (?,?,?)");
                pst.setString(1, tf2.getText());
                
                pst.execute();

                JOptionPane.showMessageDialog(null, "Done Inserting " + tf2.getText());
                mainFrame.setVisible(false);

            }catch(Exception ex){
                System.out.println(ex);
                System.out.println("EEEE");
                JOptionPane.showMessageDialog(null, "Inserting Error : " + tf2.getText());
            }finally{

            }
         }
      });



      JPanel jp = new JPanel(null);
      jp.add(nom);
      jp.add(tf2);
     
      
      jp.setSize(500,500);
      jp.setLayout(experimentLayout);
      controlPanel.add(jp);
      jp.add(okButton);
		

	  mainFrame.setLocationRelativeTo(null);
      mainFrame.setVisible(true);
   }
}
