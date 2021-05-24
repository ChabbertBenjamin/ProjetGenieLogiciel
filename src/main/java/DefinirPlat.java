import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class DefinirPlat {

   private JFrame mainFrame;
   private JLabel headerLabel;
   private JPanel controlPanel;
   private JLabel idplat, idcategorie, prix, idmatierepremiere;
   GridLayout experimentLayout = new GridLayout(0,2);
   ResultSet rs;

    DefinirPlat(){


    prepareGUI();
   }

   public static void main(String[] args){
      DefinirPlat  swingControlDemo = new DefinirPlat();
      swingControlDemo.showButtonDemo();
   }

   private void prepareGUI(){
      mainFrame = new JFrame("Definir un plat");
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

		headerLabel.setText("Definir un plat");
		headerLabel.setFont(new Font(null, Font.BOLD, 27));

        idplat = new JLabel("Entrer id du plat");
        final JTextField tf2=new JTextField();
        tf2.setSize(100,40);

        idcategorie = new JLabel("Entrer id de la categorie");
        final JTextField tf3=new JTextField();
        tf3.setSize(100,40);

        prix = new JLabel("Entrer le prix du plat");
        final JTextField tf4=new JTextField();
        tf4.setSize(100,40);
        
        idmatierepremiere = new JLabel("Entrer id matiere premiere");
        final JTextField tf5=new JTextField();
        tf5.setSize(100,40);
        
        JButton okButton = new JButton("OK");


      okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            PreparedStatement pst;
            DBConnection con = new DBConnection();
            try{
                pst = con.mkDataBase().prepareStatement("insert into Projet_GL.plat(idplat, idcategorie, prix, idmatierepremiere) values (?,?,?,?)");
                pst.setInt(1, Integer.parseInt(tf2.getText()));
                pst.setInt(2, Integer.parseInt(tf3.getText()));
                pst.setInt(3, Integer.parseInt(tf4.getText()));
                pst.setInt(4, Integer.parseInt(tf5.getText()));
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
      jp.add(idplat);
      jp.add(tf2);
      jp.add(idcategorie);
      jp.add(tf3);
      jp.add(prix);
      jp.add(tf4);
      jp.add(idmatierepremiere);
      jp.add(tf5);
      
      jp.setSize(500,500);
      jp.setLayout(experimentLayout);
      controlPanel.add(jp);
      jp.add(okButton);
		

	  mainFrame.setLocationRelativeTo(null);
      mainFrame.setVisible(true);
   }
}

