/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.Student;
import GUI.Model.StudentModel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Jens, Patrick, Casper
 */
public class TeacherViewController implements Initializable 
{
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<Student> tblPresent;
    @FXML
    private TableColumn<Student, String> colPresent;
    @FXML
    private TableColumn<Student, String> colClass;
    @FXML
    private TableColumn<Student, CheckBox> colAttendance;
    @FXML
    private TableColumn<Student, Integer> colMonday;
    @FXML
    private TableColumn<Student, Integer> colTuesday;
    @FXML
    private TableColumn<Student, Integer> colWednesday;
    @FXML
    private TableColumn<Student, Integer> colThursday;
    @FXML
    private TableColumn<Student, Integer> colFriday;
    @FXML
    private TableColumn<Student, Integer> colTotalAbsence;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCurrentClass;
    @FXML
    private Button btnLoad;
    @FXML
    private Label lblStudent;
    @FXML
    private Label lblClass;
    @FXML
    private Button btnClose;
    
    boolean userLoggedIn = false;
    private StudentModel studentModel;
    
    public TeacherViewController() 
    {
        studentModel = StudentModel.getInstance();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        dataBind();
    }
    /**
     * Sets instance variables from Student. Takes instance variables from Absence through Student.
     * Runs the checkBoxMethod.
     */
    private void dataBind() {
      //I define the mapping of the table's columns to the objects that are added to it.
      colPresent.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getName()));
      colClass.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getCurrentClass()));
      colMonday.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getAbsences().getMonday()));
      colTuesday.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getAbsences().getTuesday()));
      colWednesday.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getAbsences().getWednesday()));
      colThursday.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getAbsences().getThursday()));
      colFriday.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getAbsences().getFriday()));
      colTotalAbsence.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getAbsences().getTotalAbsence()));
      tblPresent.setItems(studentModel.getAllStudents()); 
      checkBoxMethod();
    }

    /**
     * This method constructs the checkboxes in colAttendance. A listener is added to be able to register, when a checkbox is checked.
     */
    public void checkBoxMethod() 
    {
        colAttendance.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, CheckBox>, ObservableValue<CheckBox>>() {
            @Override
            public ObservableValue<CheckBox> call(
                    TableColumn.CellDataFeatures<Student, CheckBox> arg0) {
                Student s = arg0.getValue();

                CheckBox checkBox = new CheckBox();
                checkBox.selectedProperty().setValue(s.isAttendance());
                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov,
                            Boolean old_val, Boolean new_val) 
                    {
                        s.setAttendance(new_val);
                    }
                });
                return new SimpleObjectProperty<CheckBox>(checkBox);
            }
        });
    }

    public void setModel(StudentModel studentModel) 
    {
        this.studentModel = studentModel;
    }

    /**
     * Takes user input from txtName and txtCurrentClass. Adds the input to the observable arraylist Student through studentModel.
     * @param event 
     */
    @FXML
    private void handleAddAction(ActionEvent event) 
    {
        //First I create a new Student:
        String name = txtName.getText().trim();
        String currentClass = txtCurrentClass.getText().trim();
        studentModel.addNewStudent(name, currentClass, null);

        //I reset the GUI for adding new persons
        txtName.clear();
        txtName.requestFocus();
        txtCurrentClass.clear();
        txtCurrentClass.requestFocus();
    }

    /**
     * Opens FileChooser to make it possible for teachers to save a file using a desired name.
     * Data from Present, Class and Attendance are stored.
     * @param event 
     */
    @FXML
    private void handleSave(ActionEvent event) 
    {
        try {
            FileChooser fileChooser = new FileChooser();
            Window win = root.getScene().getWindow();
            File file = fileChooser.showSaveDialog(win);
            studentModel.SaveStudentsToFile(file);

        } catch (IOException ex) 
        {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save error");
            alert.setHeaderText("Error when saving ratings:");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Opens FileChooser and gives the posibility to choose a file. You can load data into tblPresent.
     * @param event 
     */
    @FXML
    private void handleLoad(ActionEvent event) 
    {
        try {
            FileChooser fileChooser = new FileChooser();
            Window win = root.getScene().getWindow();
            File file = fileChooser.showOpenDialog(win);
            studentModel.LoadPersonsFromFile(file);

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Load error");
            alert.setHeaderText("Error when loading students:");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void signOutBtn(ActionEvent event) {
        try {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MainView.fxml"));
            Parent Main = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(Main));
            
            stage.show();
            
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }
    }

