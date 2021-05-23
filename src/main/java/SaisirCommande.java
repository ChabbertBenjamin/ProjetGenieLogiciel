import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class SaisirCommande {

   private JFrame mainFrame;
   private JLabel headerLabel;
   private JPanel controlPanel;
   private JLabel idcommande,idplat,statut,dateheurecommande,idtable;
   GridLayout experimentLayout = new GridLayout(0,2);
    ResultSet rs;

    SaisirCommande(){


    prepareGUI();
   }

   public static void main(String[] args){
      GestionEmploye  swingControlDemo = new GestionEmploye();
      swingControlDemo.showButtonDemo();
   }

   private void prepareGUI(){
      mainFrame = new JFrame("Saisir commande");
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

		headerLabel.setText("Saisir une commande");
		headerLabel.setFont(new Font(null, Font.BOLD, 27));

        idcommande = new JLabel("Entrer id commande");
        JTextField tf2=new JTextField();
        tf2.setSize(100,40);

        idplat = new JLabel("Entrer id plat");
        JTextField tf3=new JTextField();
        tf3.setSize(100,40);

        statut = new JLabel("Entrer statut");
        JTextField tf4=new JTextField();
        tf4.setSize(100,40);
        
        dateheurecommande = new JLabel("Entrer date et heure de la commande");
        JTextField tf5=new JTextField();
        tf5.setSize(100,40);
        
        idtable = new JLabel("Entrer id de la table");
        JTextField tf6=new JTextField();
        tf6.setSize(100,40);
        
        JButton okButton = new JButton("OK");


      okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            PreparedStatement pst;
            DBConnection con = new DBConnection();
            try{
                pst = con.mkDataBase().prepareStatement("insert into Projet_GL.commande(idcommande,idplat,statut,dateheurecommande,idtable) values (?,?,?,?,?)");
                pst.setInt(1, Integer.parseInt(tf2.getText()));
                pst.setInt(2, Integer.parseInt(tf3.getText()));
                pst.setString(3, tf4.getText());
                pst.setString(4, tf5.getText());
                pst.setInt(5, Integer.parseInt(tf6.getText()));
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
      jp.add(idcommande);
      jp.add(tf2);
      jp.add(idplat);
      jp.add(tf3);
      jp.add(statut);
      jp.add(tf4);
      jp.add(dateheurecommande);
      jp.add(tf5);
      jp.add(idtable);
      jp.add(tf6);
      
      jp.setSize(500,500);
      jp.setLayout(experimentLayout);
      controlPanel.add(jp);
      jp.add(okButton);
		

	  mainFrame.setLocationRelativeTo(null);
      mainFrame.setVisible(true);
   }
}
