import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.lang.Float;
import java.lang.Integer;

import static java.lang.System.*;

public class addItem {
    private JPanel Main;
    private JTextField txt_Name;
    private JTextField txt_Type;
    private JTextField int_Price;
    private JTextField int_Quantity;
    private JButton addItemButton;
    private JTable table1;
    private JTextArea txt_Desc;
    private JScrollPane table_1;
    private float itemprice;
    private int itemquantity;

    public static void main(String[] args) {
        JFrame frame = new JFrame("addItem");
        frame.setContentPane(new addItem().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static final String URL = "jdbc:postgresql://localhost/postgres";
    public static final String USER = "postgres";
    public static final String PASSWORD = "";
    public static final String DRIVER_CLASS = "org.postgresql.Driver";
    Connection connection;
    PreparedStatement pst;


    public void connect()
     {
         try{
             Class.forName(DRIVER_CLASS);
         } catch(ClassNotFoundException e){
             e.printStackTrace();
             System.exit(1);
         }
         try{
             connection = DriverManager.getConnection(URL,USER,PASSWORD );
             out.println("Connection success!");
//             Statement statement = connection.createStatement();
//             ResultSet results = statement.executeQuery("SELECT * FROM inventory");
//             table_1.setModel(DbUtils.resultSetToTableModel(results));
         }
         catch(SQLException e) {
             e.printStackTrace();
         }
     }

    public void table_load()
    {
//        String itemid = "";
//        String itemname = "";
//        String itemtype = "";
//        String itemprice = "";
//        String itemquantity = "";
//        String itemdescription = "";
        try{
            pst = connection.prepareStatement(connection.nativeSQL("select * from inventory"));
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
//            while(rs.next())
//            {
//                itemid = rs.getString("itemid");
//                itemname = rs.getString("itemname");
//                itemtype = rs.getString("itemtype");
//                itemprice = rs.getString("itemprice");
//                itemquantity = rs.getString("itemquantity");
//                itemdescription = rs.getString("itemdescription");
//
//                out.println(itemname+"\t"+itemtype+"\t"+itemprice+"\t"+itemquantity+"\t"+itemdescription);
//            }
//            rs.close();
//            pst.close();
//            connection.close();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }


    public addItem() {
        connect();
        table_load();
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String itemname,itemtype,itemdescription;

                itemname = txt_Name.getText();
                itemtype = txt_Type.getText();
                itemprice = Float.parseFloat(int_Price.getText());
                itemquantity = Integer.parseInt(int_Quantity.getText());
                itemdescription = txt_Desc.getText();

                    try{
                        String SQLquery = "INSERT INTO public.inventory" + "(itemname,itemtype,itemprice,itemquantity,itemdescription) VALUES" + "(?,?,?,?,?)";
                        pst = connection.prepareStatement(SQLquery);
 //                       pst.setString(0,itemid);
                        pst.setString(1,itemname);
                        pst.setString(2,itemtype);
                        pst.setFloat(3,itemprice);
                        pst.setInt(4,itemquantity);
                        pst.setString(5,itemdescription);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Record added successfully");
                        txt_Name.setText("");
                        txt_Type.setText("");
                        int_Price.setText("");
                        int_Quantity.setText("");
                        txt_Desc.setText("");
                        txt_Name.requestFocus();
                    }
                    catch(SQLException e1)
                    {
                        e1.printStackTrace();
                    }
            }
        });

    }
}

