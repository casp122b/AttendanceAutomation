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
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
    @FXML
    private Label pieChart;

    public StudentInfoController() throws IOException, SQLException 
    {
        studentModel = StudentModel.getInstance();
        checkInModel = CheckInModel.getInstance();
    
    }

 
//makes a Piechart that contains the student´s total attended and all the school Days from 01-02-2017, that we set on a label called pieChart.
    public void MakePieChart() throws SQLException {
   checkInModel.setTest();
        double  Attendsize = checkInModel.getStudentCheckIn().size();
      int DaysTotal = checkInModel.getSchoolDate().size();       
               
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Attended", Attendsize),
                new PieChart.Data("Days Total", DaysTotal));
        final PieChart chart = new PieChart(pieChartData);
        pieChart.setGraphic(chart);
        
        
        

      
    
    

                
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
            MakePieChart();
            tblStudentInfo.setItems(CheckInModel.getInstance().getStudentCheckIn());
        } catch (IOException | SQLException ex) 
        {
            Logger.getLogger(StudentInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        databind();
        datePicker.setValue(LocalDate.now());
datePicker.setVisible(false);


//        start();
    } 
 void setStudent(Student student) 
    {
        this.student = student;
    }
 
    //gets the timeStamps and Total Attendance on the student from StudentCheckIn
  private void databind() 
    {
        colTimeStamp.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getDateTime()));
//        
        colAttendance.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getIsAttendance()));
//      

    }
    //sets a Timestamp and the Total Attendance into the Tableview and into the Database 
    @FXML
    private void handleAttendance(ActionEvent event) throws SQLException 
    {
        LocalDateTime test = datePicker.getValue().atTime(LocalTime.now());
      
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
         MakePieChart();


}
}






