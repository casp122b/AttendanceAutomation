/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Casper
 */
public class StudentInfoTeacherController implements Initializable {

    @FXML
    private TableColumn<StudentCheckIn, Timestamp> colTimeStamp;
    @FXML
    private TableColumn<StudentCheckIn, Double> colAttendance;
    @FXML
    private Button btnDidAttend;
    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<StudentCheckIn> tblStudentInfo;
    @FXML
    private Button btnDeleteDate;
    
    private StudentModel studentModel;
    private StudentCheckIn studCheckIn;
    private CheckInModel checkInModel;
    private Student student;
    @FXML
    private Label studPieChart;

    public StudentInfoTeacherController() throws IOException, SQLException 
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
            MakePieChart();
            tblStudentInfo.setItems(CheckInModel.getInstance().getStudentCheckIn());
        } catch (IOException | SQLException ex) 
        {
            Logger.getLogger(StudentInfoTeacherController.class.getName()).log(Level.SEVERE, null, ex);
        }
        databind();
  datePicker.setValue(LocalDate.now());
    } 
       public void MakePieChart() throws SQLException {
      checkInModel.setTest();
           double  Attendsize = checkInModel.getStudentCheckIn().size();
      int DaysTotal = checkInModel.getSchoolDate().size();       
               
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Attended", Attendsize),
                new PieChart.Data("Days Total", DaysTotal));
        final PieChart chart = new PieChart(pieChartData);
        studPieChart.setGraphic(chart);
       }

    @FXML
    private void handleAttendance(ActionEvent event) throws SQLException 
    {
        if(datePicker.getValue() != null){
        LocalDateTime test = datePicker.getValue().atTime(LocalTime.now());
        //LocalDate dateTime = datePicker.getValue();
        java.sql.Timestamp sqlDate = java.sql.Timestamp.valueOf(test);
        int studentId = student.getId();
        double getArraySize = checkInModel.getStudentCheckIn().size(); //Number of timeStamps on a specific student.
        checkInModel.setTest(); //Calculation of schooldays from 01-02-2017 untill now taken taken from database.
        int schoolDaysUntillNow = checkInModel.getSchoolDate().size(); //SchoolDays from 01-02-2017 to now taken from observableList Calendar.
        double daysAway = schoolDaysUntillNow - getArraySize;
        double absence = ((daysAway - 1) * 100) / schoolDaysUntillNow;
//        System.out.println("Here is your absence " + absence);
        
//        double isAttendance = (teletubbies);
//        if(getArraySize == 0.00)
//        {
//            getArraySize++;
            double isAttendance = absence;
            checkInModel.addStudentCheckIn(new StudentCheckIn(sqlDate, studentId, isAttendance));
            StudentCheckIn studCheckIn = new StudentCheckIn(sqlDate, studentId, isAttendance);  
            MakePieChart();
//        }
//        else
//        {
//            getArraySize++;
//            double isAttendance = absence;
//            checkInModel.addStudentCheckIn(new StudentCheckIn(sqlDate, studentId, isAttendance));
//            StudentCheckIn studCheckIn = new StudentCheckIn(sqlDate, studentId, isAttendance);  
            
//        }

//        checkIn.add(studCheckIn); 


        }}
    
    @FXML
    private void handleDeleteAction(ActionEvent event) throws SQLException {
        if(tblStudentInfo.getSelectionModel().getSelectedItem() != null){
        StudentCheckIn selectedItem = tblStudentInfo.getSelectionModel().getSelectedItem();
        studCheckIn = selectedItem;
        checkInModel.deleteStudent(studCheckIn);
        tblStudentInfo.getItems().remove(selectedItem);
        tblStudentInfo.getSelectionModel().clearSelection();
      MakePieChart();
    }}

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
    
        
        

      
    
    

                

}
