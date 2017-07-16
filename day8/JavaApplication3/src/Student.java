
import java.io.Serializable;

/**
 * This class keeps the records of a student, the records are id,name,address it
 * also has a function called getDetails which would get the details of the
 * student object
 *
 * @version 13/07/2017
 *
 * @author Bisvarup
 */
public class Student implements Serializable {

    public String id;
    public String name, address;

    /**
     * This is the constructor which gives the initial values of the reference
     * variables
     *
     * @param id this is the of the student
     * @param name this is the name of the student
     * @param address this is the address of the student
     */
    public Student(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    /**
     * This function gives or outputs the details of the student object this
     * function prints the details to the console window
     */
    public void getDetails() {
        System.out.println("ID = " + this.id + " name = " + this.name + " address = " + this.address);
    }
}
