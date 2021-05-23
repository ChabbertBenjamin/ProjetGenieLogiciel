
import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VisualiserCommande extends JFrame{

   JTextField idcommande,idtable;
   String[] columnNames = {"Commande",
                        "numTable"
            };
   JTable cart;
   
   
  
   JLabel totalP = new JLabel();
   Object data[][] = new Object[100][3];
   int i = 0;
   double totalprice = 0;
    ArrayList<commandeCart> commandeList = new ArrayList<>();

    VisualiserCommande(){
   
       JPanel jp2 = new JPanel();
       jp2.setSize(700, 400);
       addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            PreparedStatement pst;
            DBConnection con = new DBConnection();
            ResultSet rs;
            try{
                pst = con.mkDataBase().prepareStatement("select idtable from Projet_GL.commande where idcommande = ?");
                pst.setString(1, idcommande.getText());
                rs = pst.executeQuery();

                while (rs.next()){
                    commandeCart f = new commandeCart();
                    f.idcommande = Integer.parseInt(idcommande.getText());
                    f.idtable = Integer.parseInt(idtable.getText());

                    commandeList.add(f);
                    data[i][0] = Integer.parseInt(idcommande.getText());
                    data[i][1] = Integer.parseInt(idtable.getText());
                    i++;
                    idcommande.setText("");
                    idtable.setText("");
                    DefaultTableModel model = (DefaultTableModel) cart.getModel();
                    model.setRowCount(0);
                    cart = new JTable(data, columnNames);
                    removeAll();
                    
                    revalidate();
                    repaint();
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
            catch(Exception ex){
                System.out.println(ex);
            }
         }
      });
       cart = new JTable(data, columnNames);
       cart.setSize(300, 450);
     
       jp2.setLayout(new FlowLayout());
       jp2.add(new JScrollPane(cart, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
       JButton checkOut = new JButton("CheckOut");
       checkOut.setSize(40, 50);
       jp2.add(checkOut);
       checkOut.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             int count = 1;
            for(commandeCart fc : commandeList){
                System.out.println(count + ": Commande : " + fc.idcommande + " numTable : "+ fc.idtable );

            }
            hide();
         }
      });

       this.add(jp2);
       this.setSize(600,550);
	    this.setLocationRelativeTo(null);
       this.setVisible(true);
   }


class commandeCart{
    int idcommande;
    int idtable;
}

}