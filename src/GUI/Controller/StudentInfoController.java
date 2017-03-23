package GUI.Controller;

import BE.Student;
import BE.StudentCheckIn;
import GUI.Model.CheckInModel;
import GUI.Model.StudentModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Casper
 */
public class StudentInfoController implements Initializable {

    @FXML
    private TableColumn<StudentCheckIn, Timestamp> colTimeStamp;
    @FXML
    private TableColumn<StudentCheckIn, Double> colAttendance;
    @FXML
    private Button btnDidAttend;
    @FXML
    private DatePicker datePicker;
//    private ObservableList<StudentCheckIn> checkIn;
    @FXML
    private TableView<StudentCheckIn> tblStudentInfo;
  
    
    private StudentModel studentModel;
    private StudentCheckIn studCheckIn;
    private CheckInModel checkInModel;
    private Student student;

    public StudentInfoController() throws IOException, SQLException 
    {
        studentModel = StudentModel.getInstance();
        checkInModel = CheckInModel.getInstance();
    
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        try 
        {
            tblStudentInfo.setItems(CheckInModel.getInstance().getStudentCheckIn());
        } catch (IOException | SQLException ex) 
        {
            Logger.getLogger(StudentInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        databind();
        datePicker.setValue(LocalDate.now());
datePicker.setVisible(false);
    } 
 void setStudent(Student student) 
    {
        this.student = student;
    }
    
  private void databind() 
    {
        colTimeStamp.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getDateTime()));
//        tblStudentInfo.setItems(checkIn);
        colAttendance.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getIsAttendance()));
//        tblStudentInfo.setItems(checkIn);
    }
    
    @FXML
    private void handleAttendance(ActionEvent event) throws SQLException 
    {
        LocalDateTime test = datePicker.getValue().atTime(LocalTime.now());
        //LocalDate dateTime = datePicker.getValue();
        java.sql.Timestamp sqlDate = java.sql.Timestamp.valueOf(test);
        int studentId = student.getId();
        double getArraySize = checkInModel.getStudentCheckIn().size(); //Number of timeStamps on a specific student.
        checkInModel.setTest(); //Calculation of schooldays from 01-02-2017 untill now taken taken from database.
        int schoolDaysUntillNow = checkInModel.getSchoolDate().size(); //SchoolDays from 01-02-2017 to now taken from observableList Calendar.
        double daysAway = schoolDaysUntillNow - getArraySize;
        double absence = ((daysAway - 1) * 100) / schoolDaysUntillNow;
        
        double isAttendance = absence;
            checkInModel.addStudentCheckIn(new StudentCheckIn(sqlDate, studentId, isAttendance));
            StudentCheckIn studCheckIn = new StudentCheckIn(sqlDate, studentId, isAttendance);  
        
//        if(getArraySize == 0.00)
//        {
//            getArraySize++;
//            double isAttendance = (60 - getArraySize) * 100 / 60;
//            StudentCheckIn studCheckIn = new StudentCheckIn(sqlDate, studentId, isAttendance);  
//            checkInModel.addStudentCheckIn(new StudentCheckIn(sqlDate, studentId, isAttendance));
//        }
//        else
//        {
//            getArraySize++;
//            double isAttendance = (60 - getArraySize) * 100 / 60;
//            StudentCheckIn studCheckIn = new StudentCheckIn(sqlDate, studentId, isAttendance);  
//            checkInModel.addStudentCheckIn(new StudentCheckIn(sqlDate, studentId, isAttendance));
//        }
    

   
}
}




