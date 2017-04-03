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
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
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
import javafx.util.Callback;

/**
 * @author Jens, Patrick, Casper, Kasper
 */
public class TeacherViewController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private TableColumn<Student, Double> colTotalAbsence;
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
    @FXML
    private TableView<Student> tblStudents;
    @FXML
    private TableColumn<Student, String> colStudents;
    
    private StudentModel studentModel;
    private Student tmpStudent;
    private CheckInModel checkInModel;
    private Task<Void> task;
    private Thread th;

    public TeacherViewController() throws IOException, SQLException {
        studentModel = StudentModel.getInstance();
        checkInModel = CheckInModel.getInstance();
    }

    /**
     * Override of the initialize method, uses the databind and
     * teacherTblClicked2 methods to bind the data to the tableview
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataBind();
        teacherTblClicked2();
    }

    /**
     * Sets instance variables from Student. Takes instance variables from
     * Absence through Student. Runs the checkBoxMethod.
     * Fills in the absence column with data from the checkInModel
     */
    private void dataBind() {
        task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                LocalDateTime ldt = LocalDateTime.now();
                colStudents.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getName()));
                colTotalAbsence.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, Double>, ObservableValue<Double>>() {
                    @Override
                    public ObservableValue<Double> call(TableColumn.CellDataFeatures<Student, Double> param) {
                        try {
                            double abs = checkInModel.teacherViewAttendance(ldt, param.getValue()).getIsAttendance();
                            return new SimpleDoubleProperty(abs).asObject();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        return new SimpleDoubleProperty().asObject();
                    }
                });
                return null;
            }
        };

        th = new Thread(task);
        th.start();

        tblStudents.setItems(studentModel.getAllStudents());
    }

    public void setModel(StudentModel studentModel) {
        this.studentModel = studentModel;
    }

    /**
     * Handles the adding of new students, uses the data in the txtName field.
     * adds the "Esbjerg - CS2016A - " in front, this is supposed to be a temporary fix for classes/school
     * after adding a student, it resets the textfield
     * @param event
     */
    @FXML
    private void handleAddAction(ActionEvent event) throws SQLException {
        String name = ("Esbjerg - CS2016A - ") + txtName.getText().trim();
        studentModel.addStudent(new Student(name));
        txtName.clear();
        txtName.requestFocus();
    }
    
    /*
    * closes the TeacherWindow
    */
    @FXML
    private void signOutBtn(ActionEvent event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }
    
    /*
    * deletes the selected student and removes him from the database
    */
    @FXML
    private void handleDeleteAction(ActionEvent event) throws SQLException {
        Student selectedItem = tblStudents.getSelectionModel().getSelectedItem();
        tmpStudent = selectedItem;
        checkInModel.deleteAttendanceByStudentId(tmpStudent);
        studentModel.deleteStudent(tmpStudent);
        tblStudents.getItems().remove(selectedItem);

        tblStudents.getSelectionModel().clearSelection();
    }
    
    /* checks for double clicks and if a tablerow is clicked twice then it use
    * the method createInfoView and opens the StudentInfoTeacherView
    */
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
    
    /*Method that opens the StudentInfoTeacherView
    *
    */
    private void createInfoView(TableRow row) {
        try {

            Stage mainViewStage = (Stage) row.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/StudentInfoTeacher.fxml"));
            Parent Login = loader.load();
            StudentInfoTeacherController sic = loader.getController();
            sic.setStudent((Student) row.getItem());
            Stage stage = new Stage();
            stage.setTitle(tblStudents.getSelectionModel().getSelectedItem().getName());
            stage.setScene(new Scene(Login));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainViewStage);
            stage.show();

        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }
}
