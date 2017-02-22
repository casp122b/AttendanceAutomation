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
    // instance variables
    private final int monday;
    private final int tuesday;
    private final int wednesday;
    private final int thursday;
    private final int friday;
    private int totalAbsence;

    /**
     * Constructor for the Absence class
     * @param monday
     * @param tuesday
     * @param wednesday
     * @param thursday
     * @param friday 
     */
    public Absence(int monday, int tuesday, int wednesday, int thursday, int friday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
    }
    
    /**
     * calculates absence percentage adding the value of monday, tuesday, wednesday, thursday, friday and dividing it by 5
     * @return 
     */
    public int getTotalAbsence() {
        return totalAbsence = (monday + tuesday + wednesday + thursday + friday)/5;
    }
    
    /**
     * Gets the value of monday
     * @return 
     */
    public int getMonday() {
        return monday;
    }

    /**
     * Gets the value of tuesday
     * @return 
     */
    public int getTuesday() {
        return tuesday;
    }
    
    /**
     * Gets the value of wednesday
     * @return 
     */
    public int getWednesday() {
        return wednesday;
    }

    /**
     * Gets the value of thursday
     * @return 
     */
    public int getThursday() {
        return thursday;
    }

    /**
     * Gets the value of friday
     * @return 
     */
    public int getFriday() {
        return friday;
    }
}
