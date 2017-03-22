/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jens, Patrick, Casper
 */
public class TeacherViewController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private TableColumn<Student, Integer> colTotalAbsence;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtName;
    @FXML
    private Label lblStudent;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnDelete;

    private StudentModel studentModel;
    private Student tmpStudent;
    private CheckInModel checkInModel;
    @FXML
    private TableView<Student> tblStudents;
    @FXML
    private TableColumn<Student, String> colStudents;

    public TeacherViewController() throws IOException, SQLException {
        studentModel = StudentModel.getInstance();
        checkInModel = CheckInModel.getInstance();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataBind();
        teacherTblClicked2();
    }

    /**
     * Sets instance variables from Student. Takes instance variables from
     * Absence through Student. Runs the checkBoxMethod.
     */
    private void dataBind() {
        //I define the mapping of the table's columns to the objects that are added to it.
        colStudents.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getName()));
        tblStudents.setItems(studentModel.getAllStudents());
    }

    public void setModel(StudentModel studentModel) {
        this.studentModel = studentModel;
    }

    /**
     * Takes user input from txtName and txtCurrentClass. Adds the input to the
     * observable arraylist Student through studentModel.
     *
     * @param event
     */
    @FXML
    private void handleAddAction(ActionEvent event) throws SQLException {
        //First I create a new Student:
        String name = txtName.getText().trim();
        studentModel.addStudent(new Student(name));

        //I reset the GUI for adding new persons
        txtName.clear();
        txtName.requestFocus();
    }

    @FXML
    private void signOutBtn(ActionEvent event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MainView.fxml"));
//            Parent Main = loader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(Main));

//            stage.show();
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }

    @FXML
    private void handleDeleteAction(ActionEvent event) throws SQLException {
        Student selectedItem = tblStudents.getSelectionModel().getSelectedItem();
        tmpStudent = selectedItem;
        studentModel.deleteStudent(tmpStudent);
        tblStudents.getItems().remove(selectedItem);
        
        tblStudents.getSelectionModel().clearSelection();
    }

    private void teacherTblClicked2() {
        tblStudents.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Student rowData = row.getItem();
                    try {
                        checkInModel.setCheckInListById(rowData.getId());
                        createInfoView(row);

                    } catch (SQLException ex) {
                        Logger.getLogger(TeacherViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return row;
        });
    }

    private void createInfoView(TableRow row) {
          try 
        {
            Stage mainViewStage = (Stage) row.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/StudentInfoTeacher.fxml"));
            Parent Login = loader.load();
            StudentInfoTeacherController sic = loader.getController();
            sic.setStudent((Student)row.getItem());
            Stage stage = new Stage();
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
