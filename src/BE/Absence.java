/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

import java.io.Serializable;

/**
 *
 * @author Jens, Patrick, Casper
 */
public class Absence implements Serializable{
    private final int monday;
    private final int tuesday;
    private final int wednesday;
    private final int thursday;
    private final int friday;
    private int totalAbsence;

    public Absence(int monday, int tuesday, int wednesday, int thursday, int friday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
    }
    
    public int getTotalAbsence() {
        return totalAbsence = (monday + tuesday + wednesday + thursday + friday)/5;
    }
    
    public int getMonday() {
        return monday;
    }

    public int getTuesday() {
        return tuesday;
    }
    
    public int getWednesday() {
        return wednesday;
    }

    public int getThursday() {
        return thursday;
    }

    public int getFriday() {
        return friday;
    }
}
