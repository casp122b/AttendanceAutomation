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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @author Jens, Patrick, Casper, Kasper
 */
public class StudentInfoTeacherController implements Initializable {

    @FXML
    private TableColumn<StudentCheckIn, Timestamp> colTimeStamp;
    @FXML
    private Button btnDidAttend;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<StudentCheckIn> tblStudentInfo;
    @FXML
    private Button btnDeleteDate;
    @FXML
    private Label studPieChart;
    @FXML
    private Label absenceLbl;
    
    private StudentModel studentModel;
    private StudentCheckIn studCheckIn;
    private CheckInModel checkInModel;
    private Student student;

    public StudentInfoTeacherController() throws IOException, SQLException {
        studentModel = StudentModel.getInstance();
        checkInModel = CheckInModel.getInstance();
    }

    /**
     * Initializes the controller class. updates absence values with currentAbsence
     * makes the Piechart
     * set the data for the tableview
     * 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            currentAbsence();
            MakePieChart();
            tblStudentInfo.setItems(CheckInModel.getInstance().getStudentCheckIn());
        } catch (IOException | SQLException ex) {
            Logger.getLogger(StudentInfoTeacherController.class.getName()).log(Level.SEVERE, null, ex);
        }
        databind();
        datePicker.setValue(LocalDate.now());

    }

    /* a Piechart that contains the student´s total attended and all the 
    * school Days from the start of the semester, that we set on a label called pieChart.
    */
    public void MakePieChart() throws SQLException {
        checkInModel.setTest();
        double Attendsize = checkInModel.getStudentCheckIn().size();
        int DaysAttended = (int) Attendsize;
        int DaysTotal = checkInModel.getSchoolDate().size() - DaysAttended;
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                    new PieChart.Data("Attended", Attendsize),
                    new PieChart.Data("Days Total", DaysTotal));
        final PieChart chart = new PieChart(pieChartData);
        studPieChart.setGraphic(chart);
        studPieChart.setStyle("-fx-font: 10 arial;");
        chart.setLabelsVisible(false);
    }
    
    /* sets a Timestamp and the Total Attendance into the Tableview and into 
    * the Database and updates the Piechart.
    */
    @FXML
    private void handleAttendance(ActionEvent event) throws SQLException {
        if (datePicker.getValue() != null) {
            LocalDateTime test = datePicker.getValue().atTime(LocalTime.now());
            StudentCheckIn studCheckIn = checkInModel.calcAttendance(test, student);
            currentAbsence();
            MakePieChart();
        }
    }

    /*Deletes the selected day, where the student have 
    * clicked attended and updates the absence and  Piechart
    */
    @FXML
    private void handleDeleteAction(ActionEvent event) throws SQLException {
        if (tblStudentInfo.getSelectionModel().getSelectedItem() != null) {
            StudentCheckIn selectedItem = tblStudentInfo.getSelectionModel().getSelectedItem();
            setStudCheckIn(selectedItem);

            studCheckIn = selectedItem;
            checkInModel.deleteCheckIn(studCheckIn);

            tblStudentInfo.getItems().remove(selectedItem);
            tblStudentInfo.getSelectionModel().clearSelection();
            currentAbsence();
            MakePieChart();
        }
    }

    void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return this.student;
    }
    
    //gets the timeStamps and Total Attendance on the student from StudentCheckIn
    private void databind() {
        colTimeStamp.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getDateTime()));
//        colAttendance.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getIsAttendance()));
    }

    /**
     * @param studCheckIn the studCheckIn to set
     */
    public void setStudCheckIn(StudentCheckIn studCheckIn) {
        this.studCheckIn = studCheckIn;
    }

    public void currentAbsence() throws SQLException {
        absenceLbl.setText(checkInModel.showStudentAbsence());
    }

}
