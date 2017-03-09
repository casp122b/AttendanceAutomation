/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import GUI.Model.StudentModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;

/**
 * FXML Controller class
 *
 * @author Casper
 */
public class StudentInfoController implements Initializable {

    @FXML
    private TableColumn<?, ?> colTimeStamp;
    @FXML
    private TableColumn<?, ?> colAttendance;
    @FXML
    private Button btnDidAttend;
    @FXML
    private Button btnDidNotAttend;
    @FXML
    private DatePicker datePicker;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        StudentModel.getInstance().getStudent();
    }    
    
}
