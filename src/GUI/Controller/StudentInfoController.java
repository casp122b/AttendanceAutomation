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
    private TableColumn<StudentCheckIn, String> colAttendance;
    @FXML
    private Button btnDidAttend;
    @FXML
    private DatePicker datePicker;
//    private ObservableList<StudentCheckIn> checkIn;
    @FXML
    private TableView<StudentCheckIn> tblStudentInfo;
    @FXML
    private Button btnDeleteDate;
    
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
    } 

    @FXML
    private void handleAttendance(ActionEvent event) throws SQLException 
    {
        LocalDateTime test = datePicker.getValue().atTime(LocalTime.now());
        //LocalDate dateTime = datePicker.getValue();
        java.sql.Timestamp sqlDate = java.sql.Timestamp.valueOf(test);
        int studentId = student.getId();
        String isAttendance = "Did attend";

        StudentCheckIn studCheckIn = new StudentCheckIn(sqlDate, studentId, isAttendance);  
        checkInModel.addStudentCheckIn(new StudentCheckIn(sqlDate, studentId, isAttendance));
//        checkIn.add(studCheckIn); 
    }
    
    @FXML
    private void handleDeleteAction(ActionEvent event) throws SQLException {
        StudentCheckIn selectedItem = tblStudentInfo.getSelectionModel().getSelectedItem();
        studCheckIn = selectedItem;
        checkInModel.deleteStudent(studCheckIn);
        tblStudentInfo.getItems().remove(selectedItem);
        
        tblStudentInfo.getSelectionModel().clearSelection();
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
}
