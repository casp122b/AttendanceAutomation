/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import java.util.Date;
import BE.Schedule;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author patrick
 */
public class ScheduleManager {
    
    
    public void createSchedule()
    {
        Schedule s = new Schedule();
        ArrayList<Date> classList = new ArrayList<Date>();
        Date class1 = new Date();
        
        class1.setMonth(2);
        class1.setDate(2);
        class1.setHours(8);
        class1.setMinutes(20);
        class1.setSeconds(0);
        
        Date class2 = new Date();
        
        class2.setMonth(2);
        class2.setDate(2);
        class2.setHours(9);
        class2.setMinutes(05);
        class2.setSeconds(0);
        
        Date class3 = new Date();
        
        class3.setMonth(2);
        class3.setDate(2);
        class3.setHours(10);
        class3.setMinutes(5);
        class3.setSeconds(0);
        
        classList.add(class1);
        classList.add(class2);
        classList.add(class3);
        
        s.setSchClass(classList);
    }
}
