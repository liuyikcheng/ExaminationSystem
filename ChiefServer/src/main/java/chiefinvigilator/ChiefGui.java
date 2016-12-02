/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chiefinvigilator;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import qrgen.QRgen;
import chiefinvigilator.InfoData;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Krissy
 */
public class ChiefGui extends javax.swing.JFrame {
//    Staff staff = new Staff();
    DefaultTableModel staffInfoTableModel = new DefaultTableModel();
    DefaultTableModel candidateTableModel = new DefaultTableModel();
    DefaultComboBoxModel  venueBoxModel = new DefaultComboBoxModel();
    DefaultComboBoxModel  statusBoxModel = new DefaultComboBoxModel();
    
    InvLogOutButtonEditor invLogOutButtonEditor = new InvLogOutButtonEditor(new JTextField());
    
    JLabel qrLabel = new JLabel("Scan the QR Code to sign in.");
    Color presetColor;
    
    private String chiefId = "";
    private String chiefPs = "";
    private String chiefBlock = "";
    


    /**
     * Creates new form ChiefGui
     */
    public ChiefGui() {

        initComponents();
        prepareComboBox();
        
        staffInfoTable.getColumnModel().getColumn(4).setCellRenderer(new InvLogOutButtonRenderer());
        staffInfoTable.getColumnModel().getColumn(4).setCellEditor(new InvLogOutButtonEditor(new JTextField()));
//        staffInfoTable(new Object());
        
        candidateTableModel = (DefaultTableModel) candidateTable.getModel();
        candidateTable.setDefaultRenderer(Object.class, new CandidateTableCellRenderer());
        candidateTable.setAutoCreateRowSorter(rootPaneCheckingEnabled);
//        chiefTabbedPane.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        chiefTabbedPane = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        signInButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        staffInfoTable = new javax.swing.JTable();
        idTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        loggedChief = new javax.swing.JLabel();
        loggedBlock = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        qrGenPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        candidateTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        totalCddLabel = new javax.swing.JLabel();
        preCddLabel = new javax.swing.JLabel();
        absCddLabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        candSearch = new javax.swing.JButton();
        regNumTextField = new javax.swing.JTextField();
        venueComboBox = new javax.swing.JComboBox<>();
        statusComboBox = new javax.swing.JComboBox<>();
        attendanceComboBox = new javax.swing.JComboBox<>();
        tableNumTextField = new javax.swing.JTextField();
        statusPanel = new javax.swing.JPanel();
        connectivityTextField = new javax.swing.JLabel();
        connectButton = new javax.swing.JButton();
        submitButton = new javax.swing.JButton();
        downloadButton = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        signInButton.setText("Sign In");

        staffInfoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Staff ID", "Staff Name", "Venue", "Status", "Log Out"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(staffInfoTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1050, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
        );

        jLabel1.setText("ID:");

        jLabel2.setText("Password:");

        loggedChief.setText("Chief :");

        loggedBlock.setText("Block:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(signInButton)
                .addGap(94, 94, 94)
                .addComponent(loggedChief)
                .addGap(116, 116, 116)
                .addComponent(loggedBlock)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {idTextField, passwordField});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(signInButton)
                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(loggedChief, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(loggedBlock)))
                .addGap(26, 26, 26)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {idTextField, passwordField});

        jScrollPane1.setViewportView(jPanel1);

        chiefTabbedPane.addTab("tab1", jScrollPane1);

        qrGenPanel.setLayout(new java.awt.GridLayout(1, 0));
        jScrollPane3.setViewportView(qrGenPanel);

        chiefTabbedPane.addTab("tab2", jScrollPane3);

        candidateTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Venue", "Student Registration Number", "Status", "Table Number"
            }
        ));
        jScrollPane6.setViewportView(candidateTable);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Summary"));

        totalCddLabel.setText("Total Candidates:");

        preCddLabel.setText("Present Candidates: ");

        absCddLabel.setText("Absent Candidates:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(totalCddLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(preCddLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(absCddLabel))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalCddLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(preCddLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(absCddLabel)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Search"));

        jLabel3.setText("Venue:");

        jLabel4.setText("Reg. Number:");

        jLabel5.setText("Status:");

        jLabel6.setText("Attendance:");

        jLabel7.setText("Table Number:");

        candSearch.setText("Search");

        venueComboBox.setModel(venueBoxModel);

        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "ELIGIBLE", "EXEMPTED", "BARRED" }));

        attendanceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "ABSENT", "PRESENT" }));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(regNumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(venueComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(attendanceComboBox, 0, 78, Short.MAX_VALUE)
                    .addComponent(statusComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(59, 59, 59)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(tableNumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(candSearch))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(venueComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tableNumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel6)
                        .addComponent(regNumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(attendanceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(candSearch))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1048, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
        );

        jScrollPane5.setViewportView(jPanel4);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jScrollPane4.setViewportView(jPanel3);

        chiefTabbedPane.addTab("tab3", jScrollPane4);

        submitButton.setText("Submit");

        downloadButton.setText("Download");

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(connectivityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(connectButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 648, Short.MAX_VALUE)
                .addComponent(downloadButton)
                .addGap(41, 41, 41)
                .addComponent(submitButton)
                .addGap(38, 38, 38))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectivityTextField)
                    .addComponent(connectButton)
                    .addComponent(submitButton)
                    .addComponent(downloadButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chiefTabbedPane)
            .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(chiefTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void addSignInButtonListener(ActionListener al){
        this.signInButton.addActionListener(al);
    }
    
    public void addChiefTabbedListener(ChangeListener cl){
        this.chiefTabbedPane.addChangeListener(cl);
    }

    public void addCandSearchListener(MouseListener al){
        this.candSearch.addMouseListener(al);
    }
    
    public void addConnectListener(ActionListener al){
        this.connectButton.addActionListener(al);
    }
    
    public void addDownloadListener(ActionListener al){
        this.downloadButton.addActionListener(al);
    }
    
    public void addQRPanel(QRgen s){
        JLabel qrLabel = new JLabel("Scan to sign in.");
        
        qrLabel.setFont(new Font("Serif", Font.BOLD, 12));
        
        qrGenPanel.removeAll();
//        qrGenPanel.setLayout(new BoxLayout(qrGenPanel, BoxLayout.Y_AXIS));
        qrGenPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 0;
	qrGenPanel.add(qrLabel, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 0.5;
	c.gridx = 1;
	c.gridy = 0;
        qrGenPanel.add(s, c);
        qrGenPanel.revalidate(); 
        qrGenPanel.repaint();
    }
    
    /**
     * @brief   Add a staff object to the table
     * @param staff 
     */
    public void addStaffToStaffInfoTable(Staff staff){
        staffInfoTableModel = (DefaultTableModel) staffInfoTable.getModel();
        staffInfoTableModel.addRow(new Object[]{staff.getID(),staff.getName(),
                                                staff.getVenue(),staff.getStatus()});
                

    }
    
    public void removeStaffInfoFromRow(Staff staff) {
        for (int i = staffInfoTableModel.getRowCount() - 1; i >= 0; --i) {

                if (staffInfoTableModel.getValueAt(i, 0).equals(staff.getID())) {
                    // what if value is not unique?
                    staffInfoTableModel.removeRow(i);
                }
        }
    }
    
    public static void activateChiefTabbedPane(){
        chiefTabbedPane.setEnabled(true);
    }
    
    public void prepareComboBox(){
        ArrayList<String> list = new InfoData().getList("Venue", "Name");
       
       //get the distinct programme list from database
       venueBoxModel.addElement("");
       for(int i = 0; i<list.size(); i++){
           venueBoxModel.addElement(list.get(i));
       }
       
       statusBoxModel.addElement("");
       for(int i = 0; i<list.size(); i++){
//           statusBoxModel.addElement(list.get(i));
       }
//       AutoCompleteDecorator.decorate(statusComboBox);
    }
    
    /**
     * @brief   set the candidate table row number
     * @param num 
     */
    public void setCandidateTableModelRow(Integer num){
        candidateTableModel.setRowCount(num);
    }
    
    /**
     * @brief   add new object to candidate table
     * @param object 
     */
    public void addCandidateTableModelRow(Object[] object){
        candidateTableModel.addRow(object);
    }
    
    public void setTableRowColor(Color color, Integer row){
        this.presetColor = color;
        candidateTableModel.fireTableRowsUpdated(row, row);
    }
    
    public Color getPresetColor(){
        return this.presetColor;
    }
    
    public void setStatusForConnectivityTextField(String text){
        this.connectivityTextField.setText(text);
    }
    
    public void setConnectButtonIcon(ImageIcon img){
        this.connectButton.setIcon(img);
    }
    
    public void setLoggedChief(String text){
        this.loggedChief.setText(text);
    }
    
    public void setLoggedBlock(String text){
        this.loggedBlock.setText(text);
    }
    
    /**
     * To change the cell of the table
     */
    class CandidateTableCellRenderer extends DefaultTableCellRenderer {
        
        
        public CandidateTableCellRenderer(){
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            TableModel model = table.getModel();
            int modelRow = table.getRowSorter().convertRowIndexToModel(row);
            String statusColumnValue = (String) model.getValueAt(modelRow, 2);

            
            if (statusColumnValue.equals("EXEMPTED")){
                setBackground(Color.YELLOW);
            }
            else if (statusColumnValue.equals("BARRED")){
                setBackground(Color.PINK);
            }
            else {
                setBackground(Color.WHITE);
            }
            return this;
        }
    }
    
    /**
     * @brief "Log Out" custom table cell renderer for candidateTable
     */
    class InvLogOutButtonRenderer extends JButton implements TableCellRenderer{

        public InvLogOutButtonRenderer() {
            setOpaque(true);
        }
        
        public Component getTableCellRendererComponent(
                                final JTable table, Object value,
                                boolean isSelected, boolean hasFocus,
                                int row, int column) {
                    
            setText("LogOut");
//            this.setLayout(new FlowLayout());
//            JButton logoutButton = new JButton("Log Out");
//            logoutButton.setSize(new Dimension(80,20));
//                    this.add(logoutButton);
//                    this.add(new JTextArea("Check"));
//                    System.out.print("afad");
                    return this;
            }
    }
    
    class InvLogOutButtonEditor extends DefaultCellEditor{
    
        JButton logoutButton;
        Boolean clicked;
        Integer content;

        public InvLogOutButtonEditor(JTextField textField) {
            super(textField);
            logoutButton = new JButton();
            logoutButton.setOpaque(true);
            
            logoutButton.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("button clicked");
                    System.out.println("content:" + content);
                    
                    fireEditingStopped();
                }
            });
            
        }
        
        public void addActionListenerLogOutButton(ActionListener al){
            this.logoutButton.addActionListener(al);
        }
        
        @Override
	public Component getTableCellEditorComponent(JTable table, Object obj,
			boolean selected, int row, int col) {
                        
            content = row;
            logoutButton.setText("Log Out");
            clicked=true;
            return logoutButton;
	}
        
        @Override
        public Object getCellEditorValue() {

		 if(clicked)
			{
			//SHOW US SOME MESSAGE
//				JOptionPane.showMessageDialog(" Clicked");
			}
		//SET IT TO FALSE NOW THAT ITS CLICKED
		clicked=false;
	  return new String("button click");
	}

        @Override
	public boolean stopCellEditing() {

	       //SET CLICKED TO FALSE FIRST
			clicked=false;
		return super.stopCellEditing();
	}
	 
	 @Override
	protected void fireEditingStopped() {
		// TODO Auto-generated method stub
		super.fireEditingStopped();
	}
        

    }   
    
    public void addInvSignOutActionListener(ActionListener al){
        invLogOutButtonEditor.addActionListenerLogOutButton(al);
    }
    
    public void addSubmitActionListener(ActionListener al){
        submitButton.addActionListener(al);
    }
    
    /**
     * @brief   set or update the number in the summary panel
     * @param totalCdd
     * @param presentCdd
     * @param absentCdd 
     */
    public void setSummaryPanel(Integer totalCdd, Integer presentCdd, Integer absentCdd){
        totalCddLabel.setText("Total Candidate: "+ totalCdd);
        preCddLabel.setText("Present Candidate: "+ presentCdd);
        absCddLabel.setText("Absent Candidate: "+ absentCdd);
    }
    
    public void popUpChiefSignInJOptionPane(){
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextField chiefIdField = new JTextField();
        JTextField chiefPsField = new JTextField();
        JTextField chiefBlockField = new JTextField();
        
        panel.add(new JLabel("Chief ID: "));
        panel.add(chiefIdField);
        panel.add(new JLabel("Password: "));
        panel.add(chiefPsField);
        panel.add(new JLabel("Block: "));
        panel.add(chiefBlockField);
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Test",
            JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            this.chiefId = chiefIdField.getText();
            this.chiefPs = chiefPsField.getText();
            this.chiefBlock = chiefBlockField.getText();
        }
    }
    
    public void popUpErrorPane(String message){
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    protected String getChiefId(){
        return this.chiefId;
    }
    
    protected String getChiefPs(){
        return this.chiefPs;
    }
    
    protected String getChiefBlock(){
        return this.chiefBlock;
    }
    
    protected void setTabbedPanelEnable(Boolean enable){
        this.chiefTabbedPane.setEnabled(enable);
    }
    
    public Integer getTabbedNumber(){
        return chiefTabbedPane.getSelectedIndex();
    }
    
    public String getIdField(){
        return this.idTextField.getText();
    }
    
    public String getPsField(){
        return new String(passwordField.getPassword());
    }
    
    public String getVenueComboBox(){
        return venueComboBox.getSelectedItem().toString();
    }
    
    public String getRegNumCandidiate(){
        return regNumTextField.getText();
    }
    
    public String getStatusComboBox(){
        return statusComboBox.getSelectedItem().toString();
    }
    
    public String getAttendanceComboBox(){
        return attendanceComboBox.getSelectedItem().toString();
    }
    
    public String getTableNumber(){
        return tableNumTextField.getText();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel absCddLabel;
    private javax.swing.JComboBox<String> attendanceComboBox;
    private javax.swing.JButton candSearch;
    private static javax.swing.JTable candidateTable;
    public static javax.swing.JTabbedPane chiefTabbedPane;
    private javax.swing.JButton connectButton;
    private javax.swing.JLabel connectivityTextField;
    private javax.swing.JButton downloadButton;
    private javax.swing.JTextField idTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel loggedBlock;
    private javax.swing.JLabel loggedChief;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel preCddLabel;
    private javax.swing.JPanel qrGenPanel;
    private javax.swing.JTextField regNumTextField;
    private javax.swing.JButton signInButton;
    private static javax.swing.JTable staffInfoTable;
    private javax.swing.JComboBox<String> statusComboBox;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JButton submitButton;
    private javax.swing.JTextField tableNumTextField;
    private javax.swing.JLabel totalCddLabel;
    private javax.swing.JComboBox<String> venueComboBox;
    // End of variables declaration//GEN-END:variables
}
