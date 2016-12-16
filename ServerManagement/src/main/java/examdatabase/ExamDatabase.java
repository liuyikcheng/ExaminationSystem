/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examdatabase;
import java.sql.*;


/**
 *
 * @author Krissy
 */
public class ExamDatabase {
    public final static String TABLE_CANDIDATE_ATTENDANCE = "CandidateAttendance";
    public final static String TABLE_CANDIDATE_INFO = "CandidateInfo";
    public final static String TABLE_CHIEF_AND_RELIEF = "ChiefAndRelief";
    public final static String TABLE_COLLECTOR = "Collector";
    public final static String TABLE_COURSE_STRUCTURE = "CourseStructure";
    public final static String TABLE_INVIGILATOR_AND_ASSISTANT = "InvigilatorAndAssistant";
    public final static String TABLE_PAPER = "Paper";
    public final static String TABLE_PAPER_INFO = "PaperInfo";
    public final static String TABLE_PROGRAMME = "Programme";
    public final static String TABLE_SESSION_AND_DATE = "SessionAndDate";
    public final static String TABLE_STAFF_INFO = "StaffInfo";
    public final static String TABLE_STUDENT_MARK = "StudentMark";
    public final static String TABLE_VENUE = "Venue";
    
    public class CandidateAttendance {
        public final static String ID = "CI_id";
        public final static String CANDIDATE_INFO_IC = "IC";
        public final static String STATUS = "Status";
        public final static String ATTENDANCE = "Attendance";
        public final static String TABLE_NUMBER = "TableNumber";
    }
    
    public class CandidateInfo {
        public final static String ID = "CI_id";
        public final static String CANDIDATE_INFO_IC = "IC";
        public final static String NAME = "Name";
        public final static String REGISTER_NUMBER = "RegNum";
        public final static String EXAM_ID = "ExamID";
    }
    
    public class ChiefAndRelief {
        public final static String ID = "CR_id";
        public final static String BLOCK = "BLOCK";
        public final static String STATUS = "Status";
        public final static String ATTENDANCE = "Attandance";
        public final static String SIGN_IN_TIME = "SignInTime";
    }
    
    public class PaperInfo {
        public final static String ID = "PI_id";
        public final static String PAPER_CODE = "PaperCode";
        public final static String PAPER_DESCRIPTION = "PaperDescription";
        public final static String EXAM_WEIGHT = "ExamWeight";
        public final static String COURSEWORK_WEIGHT = "CourseworkWeight";
    }
}


