package GUI.Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import BE.Student;
import GUI.Model.StudentModel;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author Jens, Patrick, Casper
 */
public class MainViewController implements Initializable 
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

    private StudentModel studentModel;

    public MainViewController() 
    {
        studentModel = StudentModel.getInstance();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        dataBind();
        studentModel.prefixedStudentList();
    }
    /**
     * Sets the value of the instance variables name and currentClass from the
     * class Student to colPresent and colClass. Binds tblPresent to the
     * observable list Student through the method getAllStudents.
     */
    private void dataBind() {
       //I define the mapping of the table's columns to the objects that are added to it.
        colPresent.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getName()));
        colClass.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getCurrentClass()));
        checkBoxMethod();
        tblPresent.setItems(studentModel.getAllStudents());
    }

    /**
     * Constructs a checkbox to be displayed in colAttendance.
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
     * Hides MainView and opens Login.fxml
     * @param event 
     */
    @FXML
    private void btnTeacherLogin(ActionEvent event) 
    {
        try {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/Login.fxml"));
            Parent Login = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(Login));
            stage.show();
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }
}
