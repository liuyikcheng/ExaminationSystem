/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

import globalvariable.CheckInType;
import globalvariable.InfoType;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import querylist.CddPaper;
import querylist.ExamDataList;
import querylist.CandidateAttendance;

/**
 *
 * @author Krissy
 */
public class MessageListener extends Thread{
    ServerSocket server;
    int port;
    ChiefData chief;
    Socket client;
    
    public MessageListener(int port){
        try {
            server = new ServerSocket(port);
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run(){
        
        try {
            client = server.accept();
            System.out.println("\n connected to "+ client.getRemoteSocketAddress());
            while(client.isClosed() != true){
                
                String message = receiveMessage();
                System.out.println("Received: " + message);
                response(message);
            }
        } catch (IOException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String receiveMessage() throws IOException{
        
        String message = null;
        InputStream  ir = client.getInputStream();
        DataInputStream  br = new DataInputStream(ir);
        message = br.readUTF();
        return message;
    }
    
    private void sendMessage(String message) throws IOException{
        
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        out.writeUTF(message);
        System.out.println("Message sent: " + message);
    }
    
    private void response(String message){
        
        try {
            JSONObject json = new JSONObject(message);
            System.out.println(json.getString(InfoType.TYPE));
            
            switch(json.getString(InfoType.TYPE)){
                
                case CheckInType.CHIEF_LOGIN: String id = json.getString(InfoType.ID_NO);
                                    String ps = json.getString(InfoType.PASSWORD);                 //Need to be change
                                    String block = json.getString(InfoType.BLOCK);
                                    String randomMsg = json.getString(InfoType.RANDOM_MSG); //Need to be chagne
                                    
                                    chief = new ChiefData();
                                    
//                                    System.out.println(chief.verifyStaff(id, ps, randomMsg) + chief.getStatus(id, block));  
                                    
                                    // if id is valid staff and status is CHIEF
                                    if((chief.verifyStaff(id, ps, randomMsg))&&(chief.getStatus(id, block).equals("CHIEF"))){ 
                                        //send the desired semester database
                                        sendMessage(booleanToJson(true,CheckInType.CHIEF_LOGIN).toString());
                                        chief.setChiefSignInTime(id);
                                        sendMessage(dbToJson(id, block));    
                                    }
                                    else{
                                        sendMessage(booleanToJson(false,CheckInType.CHIEF_LOGIN).toString());
                                    
                                    }
                                    break;
                                    
                case CheckInType.STAFF_LOGIN:    
                                    if(new ChiefData().verifyStaff(json.getString(InfoType.ID_NO), json.getString(InfoType.PASSWORD), json.getString(InfoType.RANDOM_MSG))){
                                        sendMessage(loginReply(json, true).toString());
                                        chief.setChiefSignInTime(json.getString(InfoType.ID_NO));
                                    }
                                    else
                                        sendMessage(loginReply(json, false).toString());
                                    break;
                                    
                case CheckInType.CDDPAPERS:   
                                    sendMessage(cddPaperListToJson(json.getString(InfoType.VALUE)));
                                    break;
                                    
                case CheckInType.GEN_RANDOM_MSG:
                                    sendMessage(challengMsgToJson(json, generateRandomString()).toString());
                                    break;
                                    
                case CheckInType.ATTDLIST:
                                    updateDB();
            }
        } catch (Exception ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void updateDB(){
        
    }
    
    public void updateCddAttdList(ArrayList<CandidateAttendance> cddAttdList) throws SQLException{
        Connection conn = new ConnectDB().connect();
        
        for(int i=0; i<cddAttdList.size(); i++){
            String sql = "UPDATE CandidateAttendance "
                    + "SET Attendance = ?, TableNum = ? "
                    + "Where CA_id = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cddAttdList.get(i).getAttendance());
            ps.setInt(2, cddAttdList.get(i).getTableNo());
            ps.setInt(3, cddAttdList.get(i).getCa_id());

            ps.executeUpdate();
            ps.close();
           
       }
        conn.close();
   }
    
    /**
    * @brief    To convert a boolean into JSON object
    * @param    b   contains a boolean
    * @return   boolean in JSONObject format
    * @throws   JSONException 
    */
    private JSONObject booleanToJson(boolean b, String type) throws JSONException{
        JSONObject bool = new JSONObject();
        bool.put(InfoType.TYPE, type);
        bool.put(InfoType.RESULT, b);
        
        return bool;
    }
    
    private JSONObject loginReply(JSONObject jsonReceived, boolean bool){
        JSONObject json = new JSONObject();
        
        try {
            System.out.println(jsonReceived.toString());
            json.put(InfoType.RESULT, bool);
            
            if(InfoType.TYPE.equals(CheckInType.STAFF_LOGIN))
                json.put(InfoType.THREAD_ID, jsonReceived.getInt(InfoType.THREAD_ID));

            json.put(InfoType.TYPE, jsonReceived.getString(InfoType.TYPE));
            json.put(InfoType.ID_NO, jsonReceived.getString(InfoType.ID_NO));
            
                    } catch (Exception ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
    
    private String dbToJson(String id, String block){
        String jsonString = null;
        ObjectMapper mapper = new ObjectMapper();
        JSONObject json = new JSONObject();
        try {
            ExamDataList examDataList = new ExamDataList(
                    chief.getCddAttdList(block, chief.getSessionId()),
                    chief.getCddInfoList(block),
                    chief.getChAndReList(block),
                    chief.getInvigilatorList(block),
                    chief.getPaperList(block, chief.getSessionId()),
                    chief.getPaperInfoList(block),
                    chief.getProgrammeList(),
                    chief.getStaffInfoList(block,chief.getSessionId()),
                    chief.getVenueList(block)
            );
            //System.out.println(chief.getCddAttdList().get(0).getCa_id());
            jsonString = mapper.writeValueAsString(examDataList);
            JSONObject jsonData = new JSONObject(jsonString);
            json.put(InfoType.TYPE, CheckInType.EXAM_INFO_LIST);
            json.put(InfoType.VALUE, jsonData);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        System.out.println(json.toString());
        return json.toString();
    }
    
    /**
     *  JSON: { "Result":true               or            "Result":false
     *          "Type":"CddPapers"                        "Type": "CddPapers"
     *          "PaperList":[{
     *              "PaperCode"
     *              "PaperDesc"
     *              "Date"
     *              "Session"
     *              "Venue"}...]
     *          }
     * 
     * @param regNum
     * @return
     * @throws IOException 
     */
    private String cddPaperListToJson(String regNum) throws IOException{
        
        ObjectMapper mapper = new ObjectMapper();
        JSONObject json = new JSONObject();
        ArrayList<CddPaper> cddPaperList;
        
        try {
            String jsonString = null;
            cddPaperList = chief.getCddPaperList(regNum);
            jsonString = mapper.writeValueAsString(cddPaperList);
            JSONObject jsonData = new JSONObject(jsonString);
            json.put(InfoType.RESULT, true);
            json.put(InfoType.VALUE, jsonData);
        } catch (SQLException ex) {
            json.put(InfoType.RESULT, false);
        }
        
        json.put(InfoType.TYPE, CheckInType.CDDPAPERS);
        return json.toString();
    }
    
    /**
     * 
     */
    protected JSONObject challengMsgToJson(JSONObject jsonMessage, String challengMsg){
        JSONObject json = new JSONObject(jsonMessage.toString());
        System.out.println(json.toString());
        json.put(InfoType.VALUE, challengMsg);
        System.out.println(jsonMessage.toString());
        return json;
    }
    
    /**
     * @brief   To generate a random message with certain character string
     * @return 
     */
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
    
}
