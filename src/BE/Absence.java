/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

/**
 *
 * @author patrick
 */
public class Absence {
    private int monday;
    private int tuesday;
    private int wednesday;
    private int thursday;
    private int friday;
    private int totalAbsence;

    public int getTotalAbsence() {
        return totalAbsence;
    }

    public void setTotalAbsence(int totalAbsence) {
        this.totalAbsence = totalAbsence;
    }

    public Absence() {
        totalAbsence();
    }

    public Absence(int monday, int tuesday, int wednesday, int thursday, int friday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
    }
    
    

    public int getMonday() {
        return monday;
    }

    public void addMonday(int monday) {
        this.monday += monday;
    }

    public int getTuesday() {
        return tuesday;
    }

    public void addTuesday(int tuesday) {
        this.tuesday += tuesday;
    }

    public int getWednesday() {
        return wednesday;
    }

    public void addWednesday(int wednesday) {
        this.wednesday += wednesday;
    }

    public int getThursday() {
        return thursday;
    }

    public void addThursday(int thursday) {
        this.thursday += thursday;
    }

    public int getFriday() {
        return friday;
    }

    public void addFriday(int friday) {
        this.friday += friday;
    }
    
    private void totalAbsence(){
        this.totalAbsence = (monday + tuesday + wednesday + thursday + friday)/5;
    }
    
    public void setMonday(int monday)
    {
        this.monday = monday;
    }
    
}
