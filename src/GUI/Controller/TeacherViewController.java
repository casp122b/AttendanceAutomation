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
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    private TableView<Student> tblPresent;
    @FXML
    private TableColumn<Student, String> colPresent;
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

    boolean userLoggedIn = false;
    private StudentModel studentModel;
    private Student tmpStudent;
    private CheckInModel checkInModel;

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
        colPresent.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getName()));
        tblPresent.setItems(studentModel.getAllStudents());
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
    private void handleAddAction(ActionEvent event) {
        //First I create a new Student:
        String name = txtName.getText().trim();
//        studentModel.addNewStudent(name, null);

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
    private void handleDeleteAction(ActionEvent event) {
        Student selectedItem = tblPresent.getSelectionModel().getSelectedItem();
        tblPresent.getItems().remove(selectedItem);
        tblPresent.getSelectionModel().clearSelection();
        
    }

//    private void teacherTblClicked() {
//
//        tblPresent.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
//                    if (mouseEvent.getClickCount() == 2) {
//
//                    }
//                }
//
//            }
//        });
//    }
    private void teacherTblClicked2() {
        tblPresent.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Student rowData = row.getItem();
                    try {
                        checkInModel.SetCheckInListById(rowData.getId());
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
        try {
            Stage mainViewStage = (Stage) row.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/StudentInfo.fxml"));
            Parent Login = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(Login));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainViewStage);
            stage.show();

        } catch (Exception e) {
            System.out.println("Something went wrong");
        }

        //StudentModel.getInstance().setStudent(tblPresent.getSelectionModel().getSelectedItem());
    }

}
