
import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GererCarteDuJour extends JFrame{
   JTextField idplat,prix;
   String[] columnNames = {"id du plat  ",
                        "  Prix  "
            };
   JTable cart;
   
   
   Object data[][] = new Object[100][3];
   int i = 0;
    ArrayList<cartedujourCart> cartedujourList = new ArrayList<>();


	GererCarteDuJour(){
       JPanel jp2 = new JPanel();
       jp2.setSize(400, 400);

       addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            PreparedStatement pst;
            DBConnection con = new DBConnection();
            ResultSet rs;
            try{
                pst = con.mkDataBase().prepareStatement("select prix from Projet_GL.plat where idplat = ?");
                pst.setString(1, idplat.getText());
                rs = pst.executeQuery();

                while (rs.next()){
                    cartedujourCart f = new cartedujourCart();
                    f.idplat = Integer.parseInt(idplat.getText());
                    f.prix = Integer.parseInt(prix.getText());
                  

                    cartedujourList.add(f);
                    data[i][0] = f.idplat;
                    data[i][1] = f.prix;
             
                    i++;
                    idplat.setText("");
                    prix.setText("");
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
            for(cartedujourCart fc : cartedujourList){
                System.out.println(count + ": id du plat : " + fc.idplat + " Prix : " + fc.prix + "tk");

            }
            hide();
            
         }
      });
       JButton modifier = new JButton("Modifier");
       modifier.setSize(50, 50);
       jp2.add(modifier);
       modifier.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 
         }
         });
       JButton supprimer = new JButton("Supprimer");
       supprimer.setSize(40, 60);
       jp2.add(supprimer);
       supprimer.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 
         }
         });
      
       this.add(jp2);
       this.setSize(600,550);
	    this.setLocationRelativeTo(null);
       this.setVisible(true);
   }


class cartedujourCart{
    int idplat;
    int prix;
}

}