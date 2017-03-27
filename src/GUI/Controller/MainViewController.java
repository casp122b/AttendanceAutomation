package GUI.Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import BE.Student;
import GUI.Model.CheckInModel;
import GUI.Model.StudentModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Jens, Patrick, Casper, Kasper
 */
public class MainViewController implements Initializable 
{
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<Student> tblStudents;
    @FXML
    private TableColumn<Student, String> colStudents;
    @FXML
    private Button btnTeacher;
    @FXML
    private TableColumn<Student, Integer> colTotalAbsence;
    
    private StudentModel studentModel;
    
    private CheckInModel checkInModel;

    public MainViewController() throws IOException, SQLException 
    {
        studentModel = StudentModel.getInstance();
         
        checkInModel = CheckInModel.getInstance();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        dataBind();
        teacherTblDoubleClick();
        colTotalAbsence.setVisible(false);
    }
    /**
     * Sets the value of the instance variables name and currentClass from the
     * class Student to colPresent and colClass. Binds tblPresent to the
     * observable list Student through the method getAllStudents.
     */
    private void dataBind() {
       //I define the mapping of the table's columns to the objects that are added to it.
        colStudents.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getName()));
        tblStudents.setItems(studentModel.getAllStudents());
    }

    public void setModel(StudentModel studentModel) 
    {
        this.studentModel = studentModel;
    }

    /**
     * Hides MainView and opens Login.fxml
     * @param event 
     */
    @FXML
    private void btnTeacherLogin(ActionEvent event) 
    {
        try {
            
            Stage mainViewStage = (Stage) btnTeacher.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/Login.fxml"));
            Parent Login = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Teacher Login");
            stage.setScene(new Scene(Login));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainViewStage);
            stage.show();

        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }
    private void teacherTblDoubleClick() 
    {
        tblStudents.setRowFactory(tv -> 
        {
        TableRow<Student> row = new TableRow<>();
        row.setOnMouseClicked(event -> 
        {
            if (event.getClickCount() == 2 && (!row.isEmpty())) 
            {
                Student rowData = row.getItem();
                try 
                {
                    checkInModel.setCheckInListById(rowData.getId());
                    createInfoView(row);

                    } catch (SQLException ex) 
                    {
                        Logger.getLogger(StudentInfoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return row;
        });
    }
//
    private void createInfoView(TableRow row) 
    {
        try 
        {
            Stage mainViewStage = (Stage) row.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/StudentInfo.fxml"));
            Parent Login = loader.load();
            StudentInfoController sic = loader.getController();
            sic.setStudent((Student)row.getItem());
            Stage stage = new Stage();
            stage.setTitle(tblStudents.getSelectionModel().getSelectedItem().getName());
            stage.setScene(new Scene(Login));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainViewStage);
            stage.show();

        } catch (Exception e) 
        {
            System.out.println("Something went wrong");
        }
    }
}
