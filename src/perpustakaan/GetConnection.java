package perpustakaan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class GetConnection {
    
    Connection con;
    Statement stat;
    
    public void dbConnect()
    {        
        try {
            con = DriverManager.getConnection("jdbc:mysql://192.168.56.102/library","dharma","123");
            stat = con.createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Connection Error "+ex.getMessage());
        }
    }
    
}
