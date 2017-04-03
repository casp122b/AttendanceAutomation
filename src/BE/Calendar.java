/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

import java.sql.Timestamp;

/**
 * @author Jens, Patrick, Casper, Kasper
 */
public class Calendar {
    private int id;
    private Timestamp schoolDate;
    
    public Calendar(int id, Timestamp schoolDate) {
        this.id = id;
        this.schoolDate = schoolDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getSchoolDate() {
        return schoolDate;
    }

    public void setSchoolDate(Timestamp schoolDate) {
        this.schoolDate = schoolDate;
    }
}
