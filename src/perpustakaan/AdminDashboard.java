package perpustakaan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AdminDashboard extends javax.swing.JFrame {

    Connection con;
    Statement stat;
    String type_book;
    int index;
    
    public AdminDashboard() {
        initComponents();
        GetConnection db = new GetConnection();
        db.dbConnect();
        con = db.con;
        stat = db.stat;
        books.setVisible(false);
        users.setVisible(false);
        addbooks.setVisible(false);
        updatebooks.setVisible(false);
        borrowedbook.setVisible(false);
    }
    
    public void addBooksLogic() throws SQLException {
        String query = "INSERT INTO books(title, author, release_year, type) values(?,?,?,?)";
        
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, title.getText());
        ps.setString(2, author.getText());
        ps.setString(3, year.getText());
        
        if(elec.isSelected()){
            type_book = "electronic";
        } else if(nonelec.isSelected()){
            type_book = "non-electronic";
        }
        
        ps.setString(4, type_book);

        ps.executeUpdate();
        
        JOptionPane.showMessageDialog(null, "New Book Has Inserted!");
    }
    
    public void updateLogic() throws SQLException{
        String query = "UPDATE books SET title = ?, author = ?, release_year = ?, type = ? WHERE id = ?";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, title_update.getText());
        ps.setString(2, author_update.getText());
        ps.setString(3, year_update.getText());
        
        if(elec_update.isSelected()){
            type_book = "electronic";
        } else if(nonelec_update.isSelected()){
            type_book = "non-electronic";
        }
        
        ps.setString(4, type_book);
        ps.setInt(5, listBook().get(index).getId());

        ps.executeUpdate();

        JOptionPane.showMessageDialog(null, "Data Has Beed Updated");
    }
    
    public void deleteLogic() throws SQLException{
        String query = "DELETE FROM books WHERE id = ?";
        
        PreparedStatement ps = con.prepareStatement(query);
        
        ps.setInt(1, listBook().get(index).getId());
        
        ps.executeUpdate();

        JOptionPane.showMessageDialog(null, "Data Has Been Deleted");
    }
    
    public ArrayList<Books> listBook()
    {
        ArrayList<Books> productList  = new ArrayList<>();
        String query = "SELECT * FROM books";
            
        try {
            
            stat = con.createStatement();
            ResultSet rs = stat.executeQuery(query);
            Books buku;
            
            while(rs.next())
            {
                buku = new Books(rs.getInt("id"),rs.getString("title"),rs.getString("author"),rs.getString("release_year"),rs.getString("type"));
                productList.add(buku);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return productList;
    }
    
    public void showBooks()
    {
        ArrayList<Books> list = listBook();
        DefaultTableModel model = (DefaultTableModel)table_book.getModel();
        
        model.setRowCount(0);
        Object[] row = new Object[5];
        for(int i = 0; i < list.size(); i++)
        {
            row[0] = i+1;
            row[1] = list.get(i).getTitle();
            row[2] = list.get(i).getAuthor();
            row[3] = list.get(i).getYear();
            row[4] = list.get(i).getType();
            
            model.addRow(row);
        }
    }
    
    public ArrayList<Users> listUser()
    {
        ArrayList<Users> productList  = new ArrayList<>();
        String query = "SELECT * FROM users WHERE role='member'";
            
        try {
            
            stat = con.createStatement();
            ResultSet rs = stat.executeQuery(query);
            Users user;
            
            while(rs.next())
            {
                user = new Users(rs.getInt("id"),rs.getString("username"),rs.getString("name"),rs.getString("nim"));
                productList.add(user);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return productList;     
    }
    
    public void showUsers()
    {
        ArrayList<Users> list = listUser();
        DefaultTableModel model = (DefaultTableModel)table_user.getModel();
        
        model.setRowCount(0);
        Object[] row = new Object[4];
        for(int i = 0; i < list.size(); i++)
        {
            row[0] = i+1;
            row[1] = list.get(i).getUsername();
            row[2] = list.get(i).getName();
            row[3] = list.get(i).getNim();
            
            model.addRow(row);
        }
    }
    
    public ArrayList<Transaction> listTransaction()
    {
        ArrayList<Transaction> productList  = new ArrayList<>();
        String query = "SELECT * FROM transaction";
            
        try {
            
            stat = con.createStatement();
            ResultSet rs = stat.executeQuery(query);
            Transaction trans;
            
            while(rs.next())
            {
                trans = new Transaction(rs.getInt("id"),rs.getString("username"),rs.getString("title"),rs.getString("author"),rs.getString("type"),rs.getString("start_date"),rs.getString("return_date"));
                productList.add(trans);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ListBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return productList;     
    }
    
    public void showTransaction()
    {
        ArrayList<Transaction> list = listTransaction();
        DefaultTableModel model = (DefaultTableModel)tableBorrow.getModel();
        
        model.setRowCount(0);
        Object[] row = new Object[6];
        for(int i = 0; i < list.size(); i++)
        {
            row[0] = i+1;
            row[1] = list.get(i).getUsername();
            row[2] = list.get(i).getTitle();
            row[3] = list.get(i).getAuthor();
            row[4] = list.get(i).getSdate();
            row[5] = list.get(i).getRdate();
            
            model.addRow(row);
        }
    }
    
    public void showItem() throws SQLException{
        
//        String query = "SELECT * FROM books";
//        ResultSet rs = stat.executeQuery(query);
//        
//        if(rs.getString("type").equals("electronic")){
//            elec.isSelected();
//        } else if(rs.getString("type").equals("non-electronic")){
//            nonelec.isSelected();
//        }
        
        title_update.setText(listBook().get(index).getTitle());
        author_update.setText(listBook().get(index).getAuthor());
        year_update.setText(listBook().get(index).getYear());
//        type_update.setSelected((ButtonModel) type, true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        type = new javax.swing.ButtonGroup();
        type_update = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        listBooks = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        listUsers = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        addBooks = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        exit = new javax.swing.JButton();
        listBorrow = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        updatebooks = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        title_update = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        author_update = new javax.swing.JTextField();
        update = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        year_update = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        elec_update = new javax.swing.JRadioButton();
        nonelec_update = new javax.swing.JRadioButton();
        back_update = new javax.swing.JButton();
        delete_update = new javax.swing.JButton();
        books = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_book = new javax.swing.JTable();
        users = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_user = new javax.swing.JTable();
        addbooks = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        submit = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        elec = new javax.swing.JRadioButton();
        nonelec = new javax.swing.JRadioButton();
        jPanel13 = new javax.swing.JPanel();
        title = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        author = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        year = new javax.swing.JTextField();
        borrowedbook = new javax.swing.JPanel();
        tableborrowed = new javax.swing.JScrollPane();
        tableBorrow = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(17, 17, 17));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Copyright 2019 STMIK Primakara");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(26, 191, 155));
        jPanel1.setForeground(new java.awt.Color(250, 250, 250));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(250, 250, 250));
        jLabel2.setText("PRILI");

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(250, 250, 250));
        jLabel4.setText("Primakara Library");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/image/logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(jLabel4))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel9))
        );

        jPanel3.setBackground(new java.awt.Color(26, 166, 119));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perpustakaan/image/user-male-circle.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 124, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel3)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 126, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel3)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        listBooks.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        listBooks.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        listBooks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listBooksMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("List Books");

        javax.swing.GroupLayout listBooksLayout = new javax.swing.GroupLayout(listBooks);
        listBooks.setLayout(listBooksLayout);
        listBooksLayout.setHorizontalGroup(
            listBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listBooksLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        listBooksLayout.setVerticalGroup(
            listBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listBooksLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        listUsers.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        listUsers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        listUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listUsersMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("List User");

        javax.swing.GroupLayout listUsersLayout = new javax.swing.GroupLayout(listUsers);
        listUsers.setLayout(listUsersLayout);
        listUsersLayout.setHorizontalGroup(
            listUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listUsersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        listUsersLayout.setVerticalGroup(
            listUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listUsersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        addBooks.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addBooks.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addBooks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addBooksMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Add Books");

        javax.swing.GroupLayout addBooksLayout = new javax.swing.GroupLayout(addBooks);
        addBooks.setLayout(addBooksLayout);
        addBooksLayout.setHorizontalGroup(
            addBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addBooksLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        addBooksLayout.setVerticalGroup(
            addBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addBooksLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        exit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        exit.setText("LogOut");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        listBorrow.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        listBorrow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        listBorrow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listBorrowMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("List Borrowed Book");

        javax.swing.GroupLayout listBorrowLayout = new javax.swing.GroupLayout(listBorrow);
        listBorrow.setLayout(listBorrowLayout);
        listBorrowLayout.setHorizontalGroup(
            listBorrowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listBorrowLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        listBorrowLayout.setVerticalGroup(
            listBorrowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listBorrowLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listBooks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(listUsers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(addBooks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exit)
                .addGap(53, 53, 53))
            .addComponent(listBorrow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(listBooks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(addBooks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(listUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(listBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(exit)
                .addContainerGap())
        );

        updatebooks.setBackground(new java.awt.Color(255, 255, 255));
        updatebooks.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("EDIT BOOK");

        jPanel17.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(72, 72, 72), 1, true));

        title_update.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        title_update.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 10, 2, 10));
        title_update.setMargin(new java.awt.Insets(10, 5, 10, 5));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(title_update, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(title_update, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
        );

        jPanel18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(72, 72, 72), 1, true));

        author_update.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        author_update.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 10, 2, 10));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(author_update, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(author_update, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
        );

        update.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        jPanel19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(72, 72, 72), 1, true));

        year_update.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        year_update.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 10, 2, 10));
        year_update.setMargin(new java.awt.Insets(10, 5, 10, 5));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(year_update, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addComponent(year_update, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel21.setText("Title");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel22.setText("Author");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setText("Release Year");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel24.setText("Type");

        elec_update.setBackground(new java.awt.Color(255, 255, 255));
        type_update.add(elec_update);
        elec_update.setText("Electronic");

        nonelec_update.setBackground(new java.awt.Color(255, 255, 255));
        type_update.add(nonelec_update);
        nonelec_update.setText("Non-Electronic");

        back_update.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        back_update.setText("Back");
        back_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back_updateActionPerformed(evt);
            }
        });

        delete_update.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        delete_update.setText("Delete");
        delete_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_updateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout updatebooksLayout = new javax.swing.GroupLayout(updatebooks);
        updatebooks.setLayout(updatebooksLayout);
        updatebooksLayout.setHorizontalGroup(
            updatebooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updatebooksLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(updatebooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, updatebooksLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(updatebooksLayout.createSequentialGroup()
                        .addComponent(delete_update)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addGroup(updatebooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(updatebooksLayout.createSequentialGroup()
                                .addComponent(back_update)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))))
            .addGroup(updatebooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(updatebooksLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(updatebooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel21)
                        .addComponent(jLabel22)
                        .addComponent(jLabel23)
                        .addComponent(jLabel24))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(elec_update)
                    .addGap(18, 18, 18)
                    .addComponent(nonelec_update)
                    .addContainerGap()))
        );
        updatebooksLayout.setVerticalGroup(
            updatebooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updatebooksLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(69, 69, 69)
                .addGroup(updatebooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(back_update, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete_update, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
            .addGroup(updatebooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(updatebooksLayout.createSequentialGroup()
                    .addGap(62, 62, 62)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(updatebooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(elec_update)
                        .addComponent(nonelec_update))
                    .addContainerGap(78, Short.MAX_VALUE)))
        );

        books.setBackground(new java.awt.Color(255, 255, 255));

        table_book.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table_book.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nomor", "Title", "Author", "Release Year", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_book.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_bookMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_book);
        if (table_book.getColumnModel().getColumnCount() > 0) {
            table_book.getColumnModel().getColumn(0).setMinWidth(50);
            table_book.getColumnModel().getColumn(0).setPreferredWidth(50);
            table_book.getColumnModel().getColumn(0).setMaxWidth(50);
            table_book.getColumnModel().getColumn(1).setMinWidth(230);
            table_book.getColumnModel().getColumn(1).setPreferredWidth(230);
            table_book.getColumnModel().getColumn(1).setMaxWidth(230);
            table_book.getColumnModel().getColumn(3).setMinWidth(100);
            table_book.getColumnModel().getColumn(3).setPreferredWidth(100);
            table_book.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        javax.swing.GroupLayout booksLayout = new javax.swing.GroupLayout(books);
        books.setLayout(booksLayout);
        booksLayout.setHorizontalGroup(
            booksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        booksLayout.setVerticalGroup(
            booksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
        );

        users.setBackground(new java.awt.Color(255, 255, 255));

        table_user.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table_user.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nomor", "Username", "Nama", "NIM"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(table_user);
        if (table_user.getColumnModel().getColumnCount() > 0) {
            table_user.getColumnModel().getColumn(0).setMinWidth(50);
            table_user.getColumnModel().getColumn(0).setPreferredWidth(50);
            table_user.getColumnModel().getColumn(0).setMaxWidth(50);
            table_user.getColumnModel().getColumn(1).setMinWidth(150);
            table_user.getColumnModel().getColumn(1).setPreferredWidth(150);
            table_user.getColumnModel().getColumn(1).setMaxWidth(150);
            table_user.getColumnModel().getColumn(3).setMinWidth(150);
            table_user.getColumnModel().getColumn(3).setPreferredWidth(150);
            table_user.getColumnModel().getColumn(3).setMaxWidth(150);
        }

        javax.swing.GroupLayout usersLayout = new javax.swing.GroupLayout(users);
        users.setLayout(usersLayout);
        usersLayout.setHorizontalGroup(
            usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        usersLayout.setVerticalGroup(
            usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
        );

        addbooks.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(48, 48, 48));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("ADD BOOKS");

        submit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        submit.setText("Submit");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("Title");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("Author");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setText("Release Year");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel20.setText("Type");

        elec.setBackground(new java.awt.Color(255, 255, 255));
        type.add(elec);
        elec.setText("Electronic");

        nonelec.setBackground(new java.awt.Color(255, 255, 255));
        type.add(nonelec);
        nonelec.setText("Non-Electronic");

        jPanel13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(72, 72, 72), 1, true));

        title.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        title.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 10, 2, 10));
        title.setMargin(new java.awt.Insets(10, 5, 10, 5));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(title, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
        );

        jPanel14.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(72, 72, 72), 1, true));

        author.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        author.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 10, 2, 10));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(author, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(author, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
        );

        jPanel15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(72, 72, 72), 1, true));

        year.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        year.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 10, 2, 10));
        year.setMargin(new java.awt.Insets(10, 5, 10, 5));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(year, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout addbooksLayout = new javax.swing.GroupLayout(addbooks);
        addbooks.setLayout(addbooksLayout);
        addbooksLayout.setHorizontalGroup(
            addbooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addbooksLayout.createSequentialGroup()
                .addContainerGap(165, Short.MAX_VALUE)
                .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(164, 164, 164))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addbooksLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addbooksLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(addbooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(addbooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(addbooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(addbooksLayout.createSequentialGroup()
                        .addComponent(elec)
                        .addGap(18, 18, 18)
                        .addComponent(nonelec)))
                .addGap(38, 38, 38))
        );
        addbooksLayout.setVerticalGroup(
            addbooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addbooksLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel16)
                .addGap(30, 30, 30)
                .addGroup(addbooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(addbooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(addbooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(addbooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(elec)
                    .addComponent(nonelec))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        borrowedbook.setBackground(new java.awt.Color(255, 255, 255));

        tableBorrow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Username", "Title", "Author", "Borrowed", "Return"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableborrowed.setViewportView(tableBorrow);

        javax.swing.GroupLayout borrowedbookLayout = new javax.swing.GroupLayout(borrowedbook);
        borrowedbook.setLayout(borrowedbookLayout);
        borrowedbookLayout.setHorizontalGroup(
            borrowedbookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tableborrowed, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
        );
        borrowedbookLayout.setVerticalGroup(
            borrowedbookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(borrowedbookLayout.createSequentialGroup()
                .addComponent(tableborrowed, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(addbooks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 200, Short.MAX_VALUE)
                    .addComponent(books, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 200, Short.MAX_VALUE)
                    .addComponent(users, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(325, 325, 325)
                    .addComponent(updatebooks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(137, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 201, Short.MAX_VALUE)
                    .addComponent(borrowedbook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(addbooks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 36, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 509, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(63, Short.MAX_VALUE)
                    .addComponent(books, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(52, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(63, Short.MAX_VALUE)
                    .addComponent(users, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(38, 38, 38)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(121, 121, 121)
                    .addComponent(updatebooks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(131, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(63, 63, 63)
                    .addComponent(borrowedbook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(40, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void table_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_bookMouseClicked
        this.index = table_book.getSelectedRow();
        books.setVisible(false);
        updatebooks.setVisible(true);
        try {
            showItem();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_table_bookMouseClicked

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        try{
            addBooksLogic();
            addbooks.setVisible(false);
            books.setVisible(true);
            showBooks();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_submitActionPerformed

    private void listUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listUsersMouseClicked
        books.setVisible(false);
        addbooks.setVisible(false);
        borrowedbook.setVisible(false);
        users.setVisible(true);
        showUsers();
    }//GEN-LAST:event_listUsersMouseClicked

    private void listBooksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listBooksMouseClicked
        users.setVisible(false);
        addbooks.setVisible(false);
        borrowedbook.setVisible(false);
        books.setVisible(true);
        showBooks();
    }//GEN-LAST:event_listBooksMouseClicked

    private void addBooksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBooksMouseClicked
        books.setVisible(false);
        users.setVisible(false);
        borrowedbook.setVisible(false);
        addbooks.setVisible(true);
    }//GEN-LAST:event_addBooksMouseClicked

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        this.setVisible(false);
        new Login().setVisible(true);
    }//GEN-LAST:event_exitActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        try{
            updateLogic();
            updatebooks.setVisible(false);
            books.setVisible(true);
            showBooks();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_updateActionPerformed

    private void back_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_back_updateActionPerformed
        updatebooks.setVisible(false);
        books.setVisible(true);
        showBooks();
    }//GEN-LAST:event_back_updateActionPerformed

    private void delete_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_updateActionPerformed
        int alert = JOptionPane.showConfirmDialog(null, "Are You Sure?", "Delete Book",  JOptionPane.YES_NO_OPTION);
        if(alert == JOptionPane.YES_OPTION){
            try{
                deleteLogic();
                updatebooks.setVisible(false);
                books.setVisible(true);
                showBooks();
            } catch (SQLException ex) {
                Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(alert == JOptionPane.NO_OPTION){
            
        }
    }//GEN-LAST:event_delete_updateActionPerformed

    private void listBorrowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listBorrowMouseClicked
        books.setVisible(false);
        addbooks.setVisible(false);
        users.setVisible(false);
        borrowedbook.setVisible(true);
        showTransaction();
    }//GEN-LAST:event_listBorrowMouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addBooks;
    private javax.swing.JPanel addbooks;
    private javax.swing.JTextField author;
    private javax.swing.JTextField author_update;
    private javax.swing.JButton back_update;
    private javax.swing.JPanel books;
    private javax.swing.JPanel borrowedbook;
    private javax.swing.JButton delete_update;
    private javax.swing.JRadioButton elec;
    private javax.swing.JRadioButton elec_update;
    private javax.swing.JButton exit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel listBooks;
    private javax.swing.JPanel listBorrow;
    private javax.swing.JPanel listUsers;
    private javax.swing.JRadioButton nonelec;
    private javax.swing.JRadioButton nonelec_update;
    private javax.swing.JButton submit;
    private javax.swing.JTable tableBorrow;
    private javax.swing.JTable table_book;
    private javax.swing.JTable table_user;
    private javax.swing.JScrollPane tableborrowed;
    private javax.swing.JTextField title;
    private javax.swing.JTextField title_update;
    private javax.swing.ButtonGroup type;
    private javax.swing.ButtonGroup type_update;
    private javax.swing.JButton update;
    private javax.swing.JPanel updatebooks;
    private javax.swing.JPanel users;
    private javax.swing.JTextField year;
    private javax.swing.JTextField year_update;
    // End of variables declaration//GEN-END:variables
}
