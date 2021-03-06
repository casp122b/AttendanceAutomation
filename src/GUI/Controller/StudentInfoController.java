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
public class StudentInfoController implements Initializable {

    @FXML
    private TableColumn<StudentCheckIn, Timestamp> colTimeStamp;
    @FXML
    private Button btnDidAttend;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<StudentCheckIn> tblStudentInfo;
    @FXML
    private Label pieChart;
    @FXML
    private Label absenceLbl;
    
    private StudentModel studentModel;
    private StudentCheckIn studCheckIn;
    private CheckInModel checkInModel;
    private Student student;  

    public StudentInfoController() throws IOException, SQLException {
        studentModel = StudentModel.getInstance();
        checkInModel = CheckInModel.getInstance();
    }

    /*makes a Piechart that contains the student´s total attended and 
    * all the school Days from 01-02-2017, that we set on a label called pieChart.
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
        pieChart.setGraphic(chart);
        pieChart.setStyle("-fx-font: 10 arial;");
        chart.setLabelsVisible(false);
    }

    /**
     * Initializes the controller class.
     * calls the piechart method and updates the absence
     * sets the data of the tableview
     * hides the datePicker so that it can't be changed from current date
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            MakePieChart();
            tblStudentInfo.setItems(CheckInModel.getInstance().getStudentCheckIn());
            currentAbsence();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(StudentInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        databind();
        datePicker.setValue(LocalDate.now());
        datePicker.setVisible(false);
    }

    void setStudent(Student student) {
        this.student = student;
    }

    //gets the timeStamps and Total Attendance on the student from StudentCheckIn
    private void databind() {
        colTimeStamp.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getDateTime()));
    }

    //sets a Timestamp and the Total Attendance into the Tableview and into the Database 
    @FXML
    private void handleAttendance(ActionEvent event) throws SQLException {
        LocalDateTime test = datePicker.getValue().atTime(LocalTime.now());
        StudentCheckIn studCheckIn = checkInModel.calcAttendance(test, student);
        MakePieChart();
        currentAbsence();
    }

    public void currentAbsence() throws SQLException {
        absenceLbl.setText(checkInModel.showStudentAbsence());
    }
}
