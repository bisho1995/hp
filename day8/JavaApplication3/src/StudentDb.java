import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * This program/application gives the user 3 choices 
 * 1. to register a new student
 * 2. to show all the available students
 * 3. to find a particular student
 * 
 * This code creates separate files for all students, each file is an object having its details
 * 
 * @author Bisvarup Mukherjee
 * @version 13/07/2017
 */
public class StudentDb {
    static final String FOLDER_NAME = "class";
    static final BufferedReader RED = new BufferedReader(new InputStreamReader(System.in));
    
    
    /**
     * This function checks if the default folder exists or not, if it does not exist it creates the folder
     * after checking the permissions
     * If folder cannot be created it says so and the program is terminated 
     */
    public static void createDefaultFolder()
    {
       File f=new File(FOLDER_NAME);
       if(f.exists())
       {
           /**folder already exists
            *
            */
            
           ;
       }
       else
       {
           /**This means the folder does not exist
            * so create the folder now
           */
           try
           {
               f.mkdir();
           }catch(Exception e)
           {
               System.out.println("Error in making default directory, the program is going to terminate now.\nThe error is "+e);
               System.exit(0);
           }
       }
    }
    
    
    /**
     * 
     * @param args Takes input from the console, so even if args are present it discards them
     * @throws IOException 
     */
    public static void main(String[] args)  {

        int ch = 4;
        createDefaultFolder();
        do {
            System.out.println("1.Register\n2.Show All\n3.Show Particular Student\n4.Exit");
            
            try {
             ch = Integer.parseInt(RED.readLine());   
            } catch (Exception e) {
                System.out.println("Error in taking ch(choice) as input "+e);
            }
            
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
     * This function reads a student object details.
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
            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }

    /**
     * This function shows all the students' details who have registered
     */
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

    /**
     * This function returns the absolute path of the default FOLDER
     * @return String, returns the absolute path of FOLDER within which the text files are saved
     */
    private static String getAbsolutePathOfFolder() {
        String location = "";
        try {
            File tmp = new File(FOLDER_NAME + File.separator + "tmp.txt");
            tmp.createNewFile();
            location = tmp.getAbsolutePath().substring(0, tmp.getAbsolutePath().lastIndexOf("\\"));
            //System.out.println(location);
            tmp.delete();
        } catch (IOException e) {
            System.out.println("Error in getAbsolutePath " + e);
        }
        return location;
    }
    
    /**
     * This function registers a new Student to the system, which means creating the associated text file in the default FOLDER
     */
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
        } catch (IOException e) {
            System.out.println("Error in registerAStudent " + e);
        }
    }
    
    /**
     * This function takes in student id and tries to search for the student
     * if it finds the student information it displays it otherwise it prints an
     * appropriate error message
     * 
     * 
     */
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
        } catch (IOException e) {
            System.out.println("Error in search a student " + e);
        }
    }
}