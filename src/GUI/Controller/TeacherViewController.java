/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.Absence;
import BE.Student;
import GUI.Model.StudentModel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Casper
 */
public class TeacherViewController implements Initializable {

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
//lo@FXML
    private TableColumn<Student, Date> colTimeStamp;
//    cal variable of StudentModel
    private StudentModel studentModel;
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
    
    boolean userLoggedIn = false;
    @FXML
    private TableView<Student> tblSumAttendance;
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
    private Label userNameLbl;

    public TeacherViewController() {
        studentModel = StudentModel.getInstance();
        

//        lblStudent.setVisible(true);
    }
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataBind();

     
//        btnAdd.setVisible(false);
//        btnLoad.setVisible(false);
//        btnSave.setVisible(false);
//        txtName.setVisible(false);
//        txtCurrentClass.setVisible(false);
//        lblClass.setVisible(false);
//        lblStudent.setVisible(false);
    
         btnAdd.setVisible(true);
        btnLoad.setVisible(true);
        btnSave.setVisible(true);
        txtName.setVisible(true);
        txtCurrentClass.setVisible(true);
        lblClass.setVisible(true);
        lblStudent.setVisible(true);
        userNameLbl.getText();
        
        
        
    }
    /**
     * Sets the value of the instance variables name and currentClass from the
     * class Student to colPresent and colClass. Binds tblNames to the
     * observable list Person through the method getAllPersons.
     */
    private void dataBind() {
        //I define the mapping of the table's columns to the objects that are added to it.
        colPresent.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getName()));
       colClass.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getCurrentClass()));
       tblPresent.setItems(studentModel.getAllStudents()); 
       testClass();
//      colTimeStamp.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getTimeStamp()));
      colMonday.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getAbsences().getMonday()));
      colTuesday.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getAbsences().getTuesday()));
      colWednesday.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getAbsences().getWednesday()));
      colThursday.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getAbsences().getThursday()));
      colFriday.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getAbsences().getFriday()));
      colTotalAbsence.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getAbsences().getTotalAbsence()));
        
        tblSumAttendance.setItems(studentModel.getAllStudents());
    }



    
    public void testClass() {

        colAttendance.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, CheckBox>, ObservableValue<CheckBox>>() {
            
            @Override
            public ObservableValue<CheckBox> call(
                    TableColumn.CellDataFeatures<Student, CheckBox> arg0) {
                Student s = arg0.getValue();

                CheckBox checkBox = new CheckBox();

                checkBox.selectedProperty().setValue(s.isAttendance());

                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov,
                            Boolean old_val, Boolean new_val) {
                        
                        s.setAttendance(new_val);
                    }
                });
                return new SimpleObjectProperty<CheckBox>(checkBox);
            }
        });
    }

    public void setModel(StudentModel studentModel) {
        this.studentModel = studentModel;
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        //First I create a new Person:
        String name = txtName.getText().trim();
        String currentClass = txtCurrentClass.getText().trim();
        Date timeStamp = new Date();
        timeStamp.setYear(117);
        studentModel.addNewStudent(name, currentClass, timeStamp, null);
        

        //I reset the GUI for adding new persons
        txtName.clear();
        txtName.requestFocus();
        txtCurrentClass.clear();
        txtCurrentClass.requestFocus();

    }

    @FXML
    private void handleSave(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            Window win = root.getScene().getWindow();
            File file = fileChooser.showSaveDialog(win);
            studentModel.SaveStudentsToFile(file);

        } catch (IOException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save error");
            alert.setHeaderText("Error when saving ratings:");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleLoad(ActionEvent event) {
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

    private void btnTeacherLogin(ActionEvent event) {
    
    }
    public void teacherButtons(){
//        tblSumAttendance.setOnMouseClicked(MouseEvent e) {
//
//        if (e.getButton() == MouseButton.PRIMARY) {
//        // Code to set the underlying data item to the new item
//        ObservableList<Item> row = (ObservableList<Item>) getTableRow().getItem();
//        row.set(columnIndex, newItem);
//    }
//});
    }

    @FXML
    private void populateTable(MouseEvent event) {
//    tblSumAttendance.getSelectionModel().clearSelection();
//    tblSumAttendance.refresh();
            Student s = tblSumAttendance.getSelectionModel().getSelectedItem();
           

            
      colMonday.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, Integer>, ObservableValue<Integer>>() {      
        @Override
        public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Student, Integer> param) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    });
          
    colTuesday.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getAbsences().getTuesday()));
            colWednesday.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getAbsences().getWednesday()));
            colThursday.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getAbsences().getThursday()));
            colFriday.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getAbsences().getFriday()));
            colTotalAbsence.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getAbsences().getTotalAbsence()));
        }
        
            
//        return null;
//    });

    @FXML
    private void signOutBtn(ActionEvent event) {
        try {
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

