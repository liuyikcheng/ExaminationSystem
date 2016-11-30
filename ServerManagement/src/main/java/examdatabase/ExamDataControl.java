/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examdatabase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Krissy
 */
public class ExamDataControl {
    
    ExamDataGUI examDataGUI;
    
    String months[] = {
      "Jan", "Feb", "Mar", "Apr",
      "May", "Jun", "Jul", "Aug",
      "Sep", "Oct", "Nov", "Dec"};
    
    public ExamDataControl(ExamDataGUI examDataGUI){
        this.examDataGUI = examDataGUI;
        addGuiListener(this.examDataGUI);
    }
    
    public void addGuiListener(ExamDataGUI examDataGUI){
        
        //add Search Button Action Listener in Tab 1
        examDataGUI.addSearchButtonListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GetData getData = new GetData(examDataGUI.getIcFieldText(),examDataGUI.getNameFieldText(), examDataGUI.getIdFieldText(),
                                        examDataGUI.getStatusBoxText(),examDataGUI.getAttendanceBoxText(), examDataGUI.getTableNumberText(),
                                        examDataGUI.getProgrammeBoxText(),examDataGUI.getFacultyBoxText(),
                                        examDataGUI.getDateFieldText(), examDataGUI.getSessionBoxText(),
                                        examDataGUI.getPaperCodeBoxText(), "",
                                        examDataGUI.getVenueText(),""
                                        );
                examDataGUI.setWarningMessage("");
                ArrayList<GetData> list = null;

                examDataGUI.setExamTableRow(0);
                try {
                    list = getData.getDataFromTable();
                    int i = 0;
                    for(i = 0; i<list.size(); i++){
                        examDataGUI.addExamTableRow(new Object[]{list.get(i).getName(), list.get(i).getIc(), list.get(i).getRegNum(), list.get(i).getProgName(), list.get(i).getFaculty(), list.get(i).getPaperCode(), list.get(i).getVenueName(), list.get(i).getDate(), list.get(i).getSession(), list.get(i).getStatus(), list.get(i).getAttendance(), list.get(i).getTableNum()});
                    }

                } catch (Exception ex) {
                    String message = ex.getMessage();
                    examDataGUI.setWarningMessage(message);
                }
            }

            });
        
        //add Search Button Action Listener in Tab 2
        examDataGUI.addSearchButtonTab2Listener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GetData getData = new GetData(examDataGUI.getIcField1Text(), examDataGUI.getNameField1Text(), examDataGUI.getIdField1Text(),
                                        "","","",
                                        (String)examDataGUI.getProgrammeBox2Text(), (String)examDataGUI.getFacultyBox2Text(),
                                        "", "",
                                        (String)examDataGUI.getPaperCodeBox2Text(), "",
                                        "",""
                                        );
        
        examDataGUI.setStatusMessage("");
        ArrayList<GetData> list = null;
        
        examDataGUI.setMarkTableRowCount(0);
        
        try {
            list = getData.getDataCheckMark();
            
            int i = 0;
            for(i = 0; i<list.size(); i++){
                examDataGUI.addMarkTable(new Object[]{list.get(i).getName(), list.get(i).getRegNum(), list.get(i).getIc(), list.get(i).getProgName(), list.get(i).getFaculty(), list.get(i).getPaperCode(), list.get(i).getPractical(), list.get(i).getCoursework()});
            }
        
        } catch (Exception ex) {
            String message = ex.getMessage();
            examDataGUI.setStatusMessage(message);
        }
            
            
        }});
        
        //add Save Button Action Listener in Tab 2
        examDataGUI.addSaveButtonListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int confirm = JOptionPane.showConfirmDialog(examDataGUI
                        , "Confirm to save?"
                        ,"Save"
                        ,JOptionPane.OK_CANCEL_OPTION);
                GregorianCalendar calendar = new GregorianCalendar();

                if(confirm == JOptionPane.OK_OPTION){

                for(int i = 0; i < examDataGUI.getMarkTableRowCount(); i++){

                    new UpdateMark( (String)examDataGUI.getMarkTableCell(i,1),
                                    (String)examDataGUI.getMarkTableCell(i,5),
                                    (Integer)examDataGUI.getMarkTableCell(i,6),
                                    (Integer)examDataGUI.getMarkTableCell(i,7)
                                    ).setMark();



                }
                examDataGUI.setStatusMessage("Recent updated on "
                                        + calendar.get(Calendar.HOUR)+":"
                                        + calendar.get(Calendar.MINUTE)+":"
                                        + calendar.get(Calendar.SECOND)+"  "
                                        + calendar.get(Calendar.DATE) + " "
                                        + months[calendar.get(Calendar.MONTH)]+ " "
                                        + calendar.get(Calendar.YEAR)
                                        + ""
                                        );
                }
            
            
            
         }});
        
        //add Restore Button Action Listener in Tab 2
        examDataGUI.addRestoreButtonListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int confirm = JOptionPane.showConfirmDialog(examDataGUI
                    , "Confirm to restore to previous saved data?"
                    ,"Restore"
                    ,JOptionPane.OK_CANCEL_OPTION);

                if(confirm == JOptionPane.OK_OPTION){
                ArrayList<GetData> listTable = new ArrayList<>() ;

                    for(int i = 0; i < examDataGUI.getMarkTableRowCount(); i++){

                        listTable.add(new GetData((String)examDataGUI.getMarkTableCell(i,2),(String)examDataGUI.getMarkTableCell(i,0),
                                                    (String)examDataGUI.getMarkTableCell(i,1),
                                                    "","","",
                                                    (String)examDataGUI.getMarkTableCell(i,3), (String)examDataGUI.getMarkTableCell(i,4),
                                                    "", "",
                                                    (String)examDataGUI.getMarkTableCell(i,5), "",
                                                    "",""
                                                    ));
                    }

                    examDataGUI.setMarkTableRowCount(0);
                    ArrayList<GetData> listData = null; 

                    for(int i = 0; i<listTable.size(); i++){
                        try{
                            listData = listTable.get(i).getDataCheckMark();
                            examDataGUI.addMarkTable(new Object[]{listData.get(0).getName(), listData.get(0).getRegNum(), listData.get(0).getIc(), listData.get(0).getProgName(), listData.get(0).getFaculty(), listData.get(0).getPaperCode(), listData.get(0).getPractical(), listData.get(0).getCoursework()});
                        }catch (Exception ex) {
                            String message = ex.getMessage();
                            examDataGUI.setStatusMessage(message);
                        }
                    }

                }
        }});
    }
    
    
}
