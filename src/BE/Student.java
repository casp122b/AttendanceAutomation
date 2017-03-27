/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

/**
 *
 * @author Jens, Patrick, Casper, Kasper
 */
public class Student 
{
    private int id;
    private String name;

    /**
     * Constructor for the Student class
     * @param name 
     */
    public Student (String name){
        this(-1, name);
    }

    public Student(int id, Student s) {
        this(id, s.getName());
    }
    
    public Student(int id, String name){
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the value of name
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of name
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns name, currentClass and attendance as strings
     * @return 
     */
    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }
}
