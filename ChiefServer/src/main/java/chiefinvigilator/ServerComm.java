/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chiefinvigilator;

import globalvariable.CheckInType;
import globalvariable.InfoType;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.codehaus.jackson.map.ObjectMapper;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;
import org.json.JSONException;
import org.json.JSONObject;
import queue.ClientToServer;
import queue.ThreadMessage;
import serverquerylist.ExamDataList;

/**
 *
 * @author Krissy
 */
public class ServerComm extends Thread implements Runnable{

    Socket socket;
    boolean signIn = false;
    ChiefData chief;
    ChiefControl chiefControl;
    Queue serverQueue = new LinkedList();
    HashMap clientQueueList = new HashMap();
    
    Thread queueThread = new Thread(new Runnable() {
         public void run()
         {
            while(true){
            System.out.println("Check");
            invSignIn();
            }
         }
        });
    
    public ServerComm(){
        chief = new ChiefData();
        serverQueue = new LinkedList();
        clientQueueList = new HashMap();
    }
    
    public boolean getSignIn(){
        return this.signIn;
    }
    
    public void connectToServer(String hostName, int port) throws IOException{
        socket = new Socket(hostName, port);
//            this.start();
       
    }
    
    public boolean isSocketAliveUitlitybyCrunchify(String hostName, int port) {
		boolean isAlive = false;
 
		// Creates a socket address from a hostname and a port number
		SocketAddress socketAddress = new InetSocketAddress(hostName, port);
		Socket socket = new Socket();
 
		// Timeout required - it's in milliseconds
		int timeout = 2000;
 
		try {
			socket.connect(socketAddress, timeout);
			socket.close();
			isAlive = true;
 
		} catch (SocketTimeoutException exception) {
			System.out.println("SocketTimeoutException " + hostName + ":" + port + ". " + exception.getMessage());
		} catch (IOException exception) {
			System.out.println(
					"IOException - Unable to connect to " + hostName + ":" + port + ". " + exception.getMessage());
		}
		return isAlive;
	}
    
    @Override
    public void run(){
        this.queueThread.start();
        try {
            System.out.println("\nconnected to "+ socket.getRemoteSocketAddress());
            
            while(this.socket.isClosed() != true){
            
            String message = receiveMessage();
            System.out.println("ServerComm Message Received: " + message);
            response(message);
            
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ServerComm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void invSignIn(){
        try {
            while(this.serverQueue.isEmpty()){
                sleep(1000);
//                System.out.print(this.serverQueue.size());
            };
            System.out.println("check123");
            ThreadMessage tm = (ThreadMessage) this.serverQueue.poll();
            System.out.println("Staff sign in : " + tm.toJsonString());
            sendMessage(tm.toJsonString());
            
        } catch (IOException ex) {
            Logger.getLogger(ServerComm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerComm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void signInToServer(String id, String password, String value, String type) throws Exception{
        sendMessage(identityInToJson(id,password,value,type));
    }
    
    public String identityInToJson(String id, String password, String value, String type) throws JSONException, Exception{
        JSONObject json = new JSONObject();
        Hmac hmac = new Hmac();
        String randomMessage = hmac.generateRandomString();
        
        
        json.put(InfoType.TYPE,type);
        json.put(InfoType.ID_NO,id);
        json.put(InfoType.RANDOM_MSG,randomMessage);
        json.put(InfoType.HASHCODE,hmac.encode(password, randomMessage));
        
        if(type.equals(CheckInType.CHIEF_LOGIN))
            json.put(InfoType.BLOCK,value);
//        json.put(InfoType.THREAD_ID,"fff");
        
        return json.toString();
    }
    
    
    
    private String receiveMessage() throws IOException{
//        System.out.println(socket.getLocalPort());
        InputStream  ir = socket.getInputStream();
        DataInputStream  br = new DataInputStream(ir);

        return br.readUTF();
    }
    
    private void sendMessage(String message) throws IOException{
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(message);
            
        System.out.println("ServerComm Message sent: " + message);
    }
    
    public void messageVerify(String message){
        boolean bool = false;
        JSONObject json = new JSONObject(message);
        
        this.signIn = (boolean) json.get("Result");
        
    }
    
    public void response(String message){
        try {
            JSONObject json = new JSONObject(message);
            System.out.println("ServerComm: " +json.getString(InfoType.TYPE));
            
            switch(json.getString(InfoType.TYPE)){
                case CheckInType.CHIEF_LOGIN: 
                                    if(json.getBoolean(InfoType.RESULT))
                                        this.signIn = true;
                                    else
                                        ChiefGui.showSignInErrorMsg();
                                    break;
                                    
                case CheckInType.STAFF_LOGIN_FROM_CHIEF_SERVER:
                                    if(json.getBoolean(InfoType.RESULT)){
                                        String id = json.getString(InfoType.ID_NO);
                                        Staff staff = new Staff(id);
                                        staff.setInvInfo(id);
                                    }
                                    else
                                        ChiefGui.showSignInErrorMsg();
                                    break;
                                    
                case CheckInType.EXAM_SESSION_DATA:    
                                    System.out.println(json.getJSONObject(InfoType.VALUE).toString());
                                    updateDB(json.getJSONObject(InfoType.VALUE).toString());
                                    break;
                                    
                case CheckInType.STAFF_LOGIN:    
//                                    if(json.getBoolean(InfoType.RESULT))
//                                        setInvigilatorSignInTime(json.getString(InfoType.ID_NO));
//                                            
                                    putQueue(json.getLong(InfoType.THREAD_ID), message);
                                    break;
                
                case CheckInType.CDDPAPERS:   
                                    putQueue(json.getLong(InfoType.THREAD_ID), message);
                                    break;
                                    
                case CheckInType.GEN_RANDOM_MSG:
                                    putQueue(json.getLong(InfoType.THREAD_ID), message);
                                    break;
                                    
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void setInviSignInTime(String staffId) throws SQLException{
        
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        
        Calendar cal = Calendar.getInstance();

        Connection conn = new ConnectDB().connect();
        
        String sql = "UPDATE InvigilatorAndAssistant "
                + "SET SignInTime = ? , Attendance = ? "
                + "WHERE StaffID = ?";
        
        PreparedStatement ps = conn.prepareStatement(sql);
        
        ps.setString(1, dateFormat.format(cal.getTime()));
        ps.setString(2, "PRESENT");
        ps.setString(3, staffId);
        
        ps.executeUpdate();
        ps.close();
    }
    
    public void invSignIn(String id, String ps){
        
    }
    
    public void getSendQueue(ThreadMessage tm){
        this.serverQueue.add(tm);
//        System.out.println("ServerComm Size = "+this.serverQueue.size());
    }
    
    public void createReceiveQueue(long threadId){
        this.clientQueueList.put(threadId, new LinkedList());
    }
    
    public void putQueue(long threadId, String message){
        
        LinkedList list = (LinkedList)(this.clientQueueList.get(threadId));
        list.add(new ThreadMessage(threadId, message));
        this.clientQueueList.replace(threadId, list);
        
    }
    
    /**
     * @brief   Continuously
     * @param threadId
     * @return 
     */
    public ThreadMessage getReceiveQueue(long threadId){
        LinkedList list = (LinkedList)(this.clientQueueList.get(threadId));
        while(list.isEmpty()){
            list = (LinkedList)(this.clientQueueList.get(threadId));
        }
        return (ThreadMessage)list.poll();
    }
    
    public void updateDB(String data) throws IOException, SQLException{
        
        ObjectMapper mapper = new ObjectMapper();
        ChiefData chief = new ChiefData();
        
        ExamDataList examDataList = mapper.readValue(data, ExamDataList.class);
        chief.updateCddAttdList(examDataList.getCddAttd());
        chief.updateCddInfoList(examDataList.getCddInfo());
        chief.updateChAndRe(examDataList.getChAndRe());
        chief.updateInvigilator(examDataList.getInv());
        chief.updatePaper(examDataList.getPaper());
        chief.updatePaperInfo(examDataList.getPaperInfo());
        chief.updateProgramme(examDataList.getProgramme());
        chief.updateStaffInfo(examDataList.getStaffInfo());
        chief.updateVenue(examDataList.getVenue());
    }
    

}
