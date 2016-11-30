/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chiefinvigilator;

import globalvariable.CheckInType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import qrgen.QRgen;

/**
 *
 * @author Krissy
 */
public class ChiefControl {
    private final static String ELIGIBLE = "ELIGIBLE";
    private final static String BARRED = "BARRED";
    private final static String EXEMPTED = "EXEMPTED";
    
    
    ChiefGui chiefGui;
    ServerComm serverComm;
    ClientComm currentClientComm;
    ChiefServer chief;
    QRgen qrgen;
    
    String mainServerHostName = "localhost";
    Integer mainServerPortNum = 5006;
    
    
    Timer timer = new Timer(4000, new ChiefControl.TimerActionListener());
    
    HashMap invMap = new HashMap();
    Integer invNum;
//    ChiefControl(){}
    
    
    ChiefControl(ChiefGui chiefGui) throws Exception{
        this.invNum = 0;
        this.chiefGui = chiefGui;
        this.chiefGui.setVisible(true);
        serverComm = new ServerComm(this);
        chief = new ChiefServer();
        
        generateQRInterface();
        
        chiefGui.addConnectListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    ChiefControl.this.serverComm.connectToServer(ChiefControl.this.mainServerHostName, ChiefControl.this.mainServerPortNum);
//                    ChiefControl.this.chiefGui.popUpChiefSignInJOptionPane();
//                    chiefSignIn(chiefGui.getChiefId(),chiefGui.getChiefPs(),chiefGui.getChiefBlock());
//                    ChiefControl.this.displayConnectivity("Connected");
                } catch (Exception ex) {
                    System.out.println("Warning: "+ex.toString());
//                    ChiefControl.this.displayConnectivity("Not connected");
                }
                
                if(ChiefControl.this.serverIsConnected()){
                    ChiefControl.this.displayConnectivity("Connected");
                    ChiefControl.this.serverComm.start();
                }
                else{
                    ChiefControl.this.displayConnectivity("Not connected");
                }
            }
        });
        
        chiefGui.addSignInButtonListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                try {
                    
                    if(ChiefControl.this.serverIsConnected()){
                        String id = ChiefControl.this.chiefGui.getIdField();
                        String ps = ChiefControl.this.chiefGui.getPsField();
                       
                        if(ChiefControl.this.serverComm.invIsAssigned(id))
                            ChiefControl.this.serverComm.signInToServer(id, ps, "", CheckInType.STAFF_LOGIN_FROM_CHIEF_SERVER);
                        else
                            ChiefControl.this.chiefGui.popUpErrorPane("Wrong ID/PASSWORD");
                    }
                    else
                        ChiefControl.this.chiefGui.popUpErrorPane("Server is not connected");
                        
                } catch (Exception ex) {
                    Logger.getLogger(ChiefControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        chiefGui.addChiefTabbedListener(new ChangeListener(){
            public void stateChanged(ChangeEvent evt) {
                try {
                    activateQRTab(chiefGui.getTabbedNumber());
                } catch (IOException ex) {
                    Logger.getLogger(ChiefControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        chiefGui.addCandSearchListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e){
                candSearch();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //To change body of generated methods, choose Tools | Templates.
            }
        });
        
    }
    
    public boolean serverIsConnected(){
        return ChiefControl.this.serverComm.isSocketAliveUitlitybyCrunchify(ChiefControl.this.mainServerHostName, ChiefControl.this.mainServerPortNum);
    }
    
    /**
     * @brief   To record staff that sign in using the chief server
     */
    public void staffSignInRecord(Staff staff){
        chiefGui.addStaffToStaffInfoTable(staff);
    }
    
    public void addInvSignOutActionListener(ActionListener al){
        chiefGui.addInvSignOutActionListener(al);
    }
    
    /**
     * @brief   To sign in
     * @param id
     * @param password
     * @param block
     * @throws Exception 
     */
    public void chiefSignIn(String id, String password, String block) throws Exception{
        serverComm.signInToServer(id,password,block,CheckInType.CHIEF_LOGIN);
    }
    
    public void displayConnectivity(String status){
        this.chiefGui.setStatusForConnectivityTextField(status);
    }
    
    /**
     * @brief   To activate the Tab which generate the QR code
     * @param tabNum
     * @throws IOException 
     */
    public void activateQRTab(Integer tabNum) throws IOException{

        if(tabNum == 1){
            try {
                createNewClientComm();
//                timer.start();
            } catch (Exception ex) {
                Logger.getLogger(ChiefControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
//            timer.stop();
        }
    }
    
    
    public void generateQRInterface(){
        this.qrgen = new QRgen();
        this.qrgen.setPreferredSize(new Dimension(500,500));
      
        chiefGui.addQRPanel(qrgen);
    }
    
    public void createNewClientComm() throws IOException, Exception{
        ServerSocket socket = new ServerSocket();
            
        String randomString = this.generateRandomString();
        chief.setPort();
        socket = this.chief.getServerSocket();
        System.out.println(randomString);
        System.out.println("new clientComm created");
        this.currentClientComm = new ClientComm(socket, this.serverComm, this.chief, this.qrgen, this);
        (this.currentClientComm).start();
        regenerateCurrentQRInterface();
        
    }
    
    public void regenerateCurrentQRInterface(){
        try {
            this.currentClientComm.requestForRandomMessage();
        } catch (Exception ex) {
            Logger.getLogger(ChiefControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public void candSearch(){
        InfoData data = new InfoData();
        
        data.setStatus(chiefGui.getStatusComboBox());
        data.setAttendance(chiefGui.getAttendanceComboBox());
        data.setRegNum(chiefGui.getRegNumCandidiate());
        data.setTableNum(chiefGui.getTableNumber());
        data.setVenueName(chiefGui.getVenueComboBox());
        
        ArrayList<InfoData> cddList = new ArrayList<>();
        System.out.print(chiefGui.getAttendanceComboBox());
        try {
            cddList = data.getDataFromTable();
        } catch (CustomException ex) {
            System.out.println(ex.getMessage());
        }
        
        chiefGui.setCandidateTableModelRow(0);
        
        for(int i = 0; i<cddList.size(); i++){
            
                if (cddList.get(i).getStatus().equals(ELIGIBLE)){
                    chiefGui.addCandidateTableModelRow(new Object[]{cddList.get(i).getVenueName(), cddList.get(i).getRegNum(), cddList.get(i).getAttendance(), cddList.get(i).getTableNum()});

                }
                else if (cddList.get(i).getStatus().equals(EXEMPTED)){
                    chiefGui.addCandidateTableModelRow(new Object[]{cddList.get(i).getVenueName(), cddList.get(i).getRegNum(), cddList.get(i).getStatus(), cddList.get(i).getTableNum()});

                }
                else if (cddList.get(i).getStatus().equals(BARRED)){
                    chiefGui.addCandidateTableModelRow(new Object[]{cddList.get(i).getVenueName(), cddList.get(i).getRegNum(), cddList.get(i).getStatus(), cddList.get(i).getTableNum()});
 
                }
            }
        
        try {
            chiefGui.setSummaryPanel(data.getCountTotalCdd(), data.getCountAttdCdd("PRESENT"),
                                        data.getCountAttdCdd("ABSENT"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void addStaffInfoToGuiTable(Staff staff){
        chiefGui.addStaffToStaffInfoTable(staff);
    }
    
    
    protected String generateRandomString() {
        String seed = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+Long.toString(System.nanoTime());
        StringBuilder str = new StringBuilder();
        Random rnd = new Random();
        while (str.length() < 18) {
            int index = (int) (rnd.nextFloat() * seed.length());
            str.append(seed.charAt(index));
        }
        String saltStr = str.toString();
        return saltStr;
    }

    class TimerActionListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
          try {
              regenerateCurrentQRInterface();
          } catch (Exception ex) {
              System.out.println("Error TimerActionListener->actionPerformed");
          }
            
      }
    }
    
}
