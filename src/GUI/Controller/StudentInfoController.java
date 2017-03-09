/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.StudentCheckIn;
import GUI.Model.CheckInModel;
import GUI.Model.StudentModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.converter.DateTimeStringConverter;

/**
 * FXML Controller class
 *
 * @author Casper
 */
public class StudentInfoController implements Initializable {

    @FXML
    private TableColumn<StudentCheckIn, String> colTimeStamp;
    @FXML
    private TableColumn<StudentCheckIn, Integer> colAttendance;
    @FXML
    private Button btnDidAttend;
    @FXML
    private Button btnDidNotAttend;
    @FXML
    private DatePicker datePicker;
    
    private ObservableList<StudentCheckIn> checkIn;
    @FXML
    private TableView<StudentCheckIn> tblStudentInfo;
    
    private StudentModel studentModel;

    public StudentInfoController() throws IOException, SQLException {
        studentModel = StudentModel.getInstance();
        checkIn = FXCollections.observableArrayList();
    }
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            checkIn.addAll(CheckInModel.getInstance().getStudentCheckIn());
        } catch (IOException | SQLException ex) {
            Logger.getLogger(StudentInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        DataBind();
        
    } 

    private void DataBind() {
        colTimeStamp.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getDateTime()));
        tblStudentInfo.setItems(checkIn);
    }
    
}
