/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examdatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Krissy
 */
public class DataWriter {
    
    public static final String ERR_INVALID_PROG_GROUP = "Invalid Programme Group";
    
    public DataWriter(){
        
    }
    
    public void insertCandidate(String ic, String name, String regNum, 
                                String programmeName, String programmeGroup,
                                String examId) throws Exception {
        String sql = "INSERT OR REPLACE INTO "
                    + "CandidateInfo(IC, Name, RegNum, Programme_id, ExamID) "
                    + "VALUES(?,?,?,(SELECT Programme_id FROM Programme WHERE Programme_Group =? AND Name =?), ?) ";
            
            Connection conn = new ConnectDB().connect();
            
            PreparedStatement ps = conn.prepareStatement(sql);
            
            if(isNumeric(programmeGroup)){
                ps.setString(1,ic);
                ps.setString(2,name);
                ps.setString(3,regNum);
                ps.setString(5,programmeName);
                ps.setInt(4,Integer.parseInt(programmeGroup));
                ps.setString(6,examId);
            }
            else
                throw new Exception(ERR_INVALID_PROG_GROUP);
            
            ps.executeUpdate();
            ps.close();
        
            conn.close();
            
    }
    
    public void insertPaper(String paperCodeField, String paperNameField, String lecturerField, 
                                String tutorField, String facultyField) throws SQLException {
        
        Connection conn = new ConnectDB().connect();
            String sql = "INSERT OR REPLACE INTO "
                    + "PaperInfo(PaperCode, PaperDecription) "
                    + "VALUES(?,?) ";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1,paperCodeField);
            ps.setString(2,paperNameField);
//            ps.setInt(3,Integer.parseInt(programmeGroup));
//            ps.setInt(4,Integer.parseInt(programmeGroup));
            
            ps.executeUpdate();
            ps.close();
            conn.close();
            
    }
    
    public boolean isNumeric(String str)  {  
      try  
      {  
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }

}
