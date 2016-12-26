/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examdatabase;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import querylist.Invigilator;

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

                    new UpdateData( (String)examDataGUI.getMarkTableCell(i,1),
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
        
        examDataGUI.addAddPaperTab3ButtonListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                    JPanel panel = new JPanel(new GridLayout(0, 1));
                    JTextField paperCodeField = new JTextField();
                    JTextField paperNameField = new JTextField();
                    JTextField lecturerField = new JTextField();
                    JTextField tutorField = new JTextField();
                    JTextField facultyField = new JTextField();

                    panel.add(new JLabel("Paper Code: "));
                    panel.add(paperCodeField);
                    panel.add(new JLabel("Paper Name: "));
                    panel.add(paperNameField);
                    panel.add(new JLabel("Lecturer: "));
                    panel.add(lecturerField);
                    panel.add(new JLabel("Tutor: "));
                    panel.add(tutorField);
                    panel.add(new JLabel("Faculty: "));
                    panel.add(facultyField);

                    int result = JOptionPane.showConfirmDialog(null, panel, "Add",
                        JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                    }
                
              }
            
        });
        
        examDataGUI.addAddCandidateButtonListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                   
                JPanel panel = new JPanel(new GridLayout(0, 1));
                JTextField studentIDField = new JTextField();
                JTextField studentNameField = new JTextField();
                JTextField studentICField = new JTextField();
                JTextField examIDField = new JTextField();
                JTextField ProgrammeNameField = new JTextField();
                JTextField ProgrammeGroupField = new JTextField();

                    panel.add(new JLabel("Register Number: "));
                    panel.add(studentIDField);
                    panel.add(new JLabel("Name: "));
                    panel.add(studentNameField);
                    panel.add(new JLabel("IC number: "));
                    panel.add(studentICField);
                    panel.add(new JLabel("Exam ID: "));
                    panel.add(examIDField);
                    panel.add(new JLabel("Programme Name: "));
                    panel.add(ProgrammeNameField);
                    panel.add(new JLabel("Programme Group: "));
                    panel.add(ProgrammeGroupField);

                    int result = JOptionPane.showConfirmDialog(null, panel, "Add",
                        JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                    try {
                        new DataWriter().insertCandidate(studentICField.getText(), studentNameField.getText(),
                                studentIDField.getText(), ProgrammeNameField.getText(),
                                ProgrammeGroupField.getText(), examIDField.getText());
                    } catch (Exception ex) {
                        Logger.getLogger(ExamDataControl.class.getName()).log(Level.SEVERE, null, ex);
                        int error = JOptionPane.showConfirmDialog(null, ex.getMessage(), "ERROR", 
                                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                        System.out.println("check");
                    }
                    }
                
              }
            
        });
        
        //add Search Button Action Listener in Tab 3
        examDataGUI.addSearchButtonTab3Listener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GetData getData = new GetData();
                
                getData.setLecturer(examDataGUI.getLecturerField3().getText());
                getData.setTutor(examDataGUI.getTutorField3().getText());
                getData.setProgName(examDataGUI.getProgrammeBox3().getSelectedItem().toString());
                getData.setPaperCode(examDataGUI.getPaperCodeField3().getText());
                getData.setPaperDesc(examDataGUI.getPaperNameField3().getText());
        
            examDataGUI.setStatusMessage("");
            ArrayList<GetData> list = null;

            examDataGUI.setPaperTable3RowCount(0);

            try {
                list = getData.getCourseStructure();
                int i = 0;
                for(i = 0; i<list.size(); i++){
                    examDataGUI.addPaperToPaperTable3(new Object[]{list.get(i).getPaperCode(), list.get(i).getPaperDesc(), list.get(i).getLecturer(), list.get(i).getTutor(), list.get(i).getProgName(), list.get(i).getProgGroup(), list.get(i).getExamWeight(), list.get(i).getCourseworkWeight()});
                }

            } catch (Exception ex) {
                String message = ex.getMessage();
                examDataGUI.setStatusMessage(message);
            }
            
        }});
        
        examDataGUI.addSaveButtonTab3Listener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int confirm = JOptionPane.showConfirmDialog(examDataGUI
                        , "Confirm to save?"
                        ,"Save"
                        ,JOptionPane.OK_CANCEL_OPTION);
                GregorianCalendar calendar = new GregorianCalendar();

                if(confirm == JOptionPane.OK_OPTION){

                for(int i = 0; i < examDataGUI.getMarkTableRowCount(); i++){

                    new UpdateData( (String)examDataGUI.getMarkTableCell(i,1),
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
            }
        });
        
        //add Search Button Action Listener in Tab 3
        examDataGUI.addSearchButtonTab4Listener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GetData getData = new GetData();
                
                getData.setLecturer(examDataGUI.getLecturerField3().getText());
                getData.setTutor(examDataGUI.getTutorField3().getText());
                getData.setProgName(examDataGUI.getProgrammeBox3().getSelectedItem().toString());
                getData.setPaperCode(examDataGUI.getPaperCodeField3().getText());
                getData.setPaperDesc(examDataGUI.getPaperNameField3().getText());
        
                examDataGUI.setStatusMessage("");
                ArrayList<GetData> list = null;

                examDataGUI.setInvTable4RowCount(0);

                try {
                    list = getData.getInvigilatorInfo(  (String)examDataGUI.getDateBox4().getSelectedItem(),
                                                        (String)examDataGUI.getSessionBox4().getSelectedItem(),
                                                        (String)examDataGUI.getBlockBox4().getSelectedItem(),
                                                        (String)examDataGUI.getVenueBox4().getSelectedItem(),
                                                        examDataGUI.getStaffIDField4().getText(),
                                                        (String)examDataGUI.getStatusBox4().getSelectedItem());
                    int i = 0;
                    for(i = 0; i<list.size(); i++){
                        System.out.println(list.get(i).getDate());
                        examDataGUI.addInvigilatorTable4(new Object[]{list.get(i).getDate(), list.get(i).getSession(), list.get(i).getBlock(), list.get(i).getVenueName(), list.get(i).getStaffID(), list.get(i).getInvStatus()});
                    }

                } catch (Exception ex) {
                    Logger.getLogger(ExamDataControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            
        }});
        
    }
    
    
}
