/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Model;

import BE.StudentCheckIn;
import BLL.CheckInManager;
import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author patrick
 */
public class CheckInModel {
    
    private static CheckInModel INSTANCE;
    private ObservableList<StudentCheckIn> studentCheckIn;
    
    private final CheckInManager checkInMgr;

    
    
    /**
     * Constructs a new StudentManager and creates an observable arraylist out  of the observable list Student.
     */
    private CheckInModel() throws IOException
    {
        checkInMgr = new CheckInManager();
    }
    
    /**
     * The method to get a reference to this Singleton:
     *
     * @return
     */
    public static synchronized CheckInModel getInstance() throws IOException, SQLException
    {
        if (INSTANCE == null)
        {
            INSTANCE = new CheckInModel();
        }
        return INSTANCE;
    }
    
    public ObservableList<StudentCheckIn> getStudentCheckIn() {
        return studentCheckIn;
    }

    public void setStudentCheckIn(ObservableList<StudentCheckIn> studentCheckIn) {
        this.studentCheckIn = studentCheckIn;
    }
    
    public void SetCheckInListById(int id) throws SQLException{
                
        studentCheckIn = FXCollections.observableArrayList();
        studentCheckIn.addAll(checkInMgr.getAllCheckInsById(id));
    }
}
