import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;


/**
 * This program/application gives the user 3 choices 
 * 1. to register a new student
 * 2. to show all the available students
 * 3. to find a particular student
 * 
 * This code creates separate files for all students, each file is an object having its details
 * 
 * @author Bisvarup Mukherjee
 */
public class StudentDb {
    static final String FOLDER_NAME = "class";
    static final BufferedReader RED = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {

        int ch = 4;
        do {
            System.out.println("1.Register\n2.Show All\n3.Show Particular Student\n4.Exit");
            ch = Integer.parseInt(RED.readLine());
            switch (ch) {
                case 1:
                    registerAStudent();

                    break;
                case 2:
                    showAll();
                    break;
                case 3:
                    searchAStudent();
                    break;
                case 4:
                    System.out.println("Exiting program");
                    break;
            }
        } while (ch != 4);
    }

    /**
     * This function writes an object to its appropriate file in the class folder
     * 
     * @param ob It is an object of type student- it is the student object which is to be written to the file
     */
    static void writeMyObject(Student ob) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("class\\" + ob.id + ".txt"));
            oos.writeObject(ob);
        } catch (Exception ex) {
            System.out.println("Error is  " + ex);
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
                System.out.println("Error in closing oos " + e);
            }
        }
    }
    
    /**
     * 
     * @param filename This is the filename which is to be searched in the class folder
     */
    static void readMyObject(String filename) {
        ObjectInputStream ois = null;
        try {
            FileInputStream fis = new FileInputStream(filename);
            ois = new ObjectInputStream(fis);
            //System.out.println("Printing object 1");
            Student ob = (Student) ois.readObject();
            //System.out.println("Printing object");
            ob.getDetails();
            //System.out.println("End of function");
        } catch (Exception e) {
            System.err.println("Error in readMyObject " + e + " in function readMyObject");
        } finally {
            try {
                ois.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    private static void showAll() {

        try {
            String location = getAbsolutePathOfFolder();

            File tmp = new File(location);
            String list[] = tmp.list();
            for (String fileName: list) {
                fileName = FOLDER_NAME + File.separator + fileName;
                //System.out.println("Filename is "+fileName);
                readMyObject(fileName);
            }
        } catch (Exception ex) {
            System.out.println("In case 2 Error is " + ex);
        }

    }

    private static String getAbsolutePathOfFolder() {
        String location = "";
        try {
            File tmp = new File(FOLDER_NAME + File.separator + "tmp.txt");
            tmp.createNewFile();
            location = tmp.getAbsolutePath().substring(0, tmp.getAbsolutePath().lastIndexOf("\\"));
            //System.out.println(location);
            tmp.delete();
        } catch (Exception e) {
            System.out.println("Error in getAbsolutePath " + e);
        }
        return location;
    }

    private static void registerAStudent() {
        try {
            System.out.println("Enter Name of Student");
            String name = RED.readLine();
            System.out.println("Enter id of Student");
            String id = RED.readLine();
            System.out.println("Enter address of Student");
            String address = RED.readLine();

            Student ob = new Student(id, name, address);
            writeMyObject(ob);
        } catch (Exception e) {
            System.out.println("Error in registerAStudent " + e);
        }
    }

    private static void searchAStudent() {
        try {
            System.out.println("Enter id of Student");
            String idOfStudent = RED.readLine();
            idOfStudent = FOLDER_NAME + File.separator + idOfStudent + ".txt";
            File f = new File(idOfStudent);
            if (!f.exists()) {
                System.out.println("No such student exists ");
            } else {
                readMyObject(idOfStudent);
            }
        } catch (Exception e) {
            System.out.println("Error in search a student " + e);
        }
    }

}