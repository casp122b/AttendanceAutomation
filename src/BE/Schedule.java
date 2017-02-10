/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author patrick
 */
public class Schedule {
    private ArrayList<Date> schClass;
    
    public Schedule()
    {
        schClass = new ArrayList<>();
    }

    /**
     * @return the schClass
     */
    public ArrayList<Date> getSchClass() {
        return schClass;
    }

    /**
     * @param schClass the schClass to set
     */
    public void setSchClass(ArrayList<Date> schClass) {
        this.schClass = schClass;
    }
    

}
