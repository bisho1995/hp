
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 *
 * @author user
 */
public class StudentUse  {
    public static void main(String[] args) {
        System.out.println("Starting Program");
        (new StudentUse()).readMyObject();
    }
    void readMyObject()
    {
        ObjectInputStream ois=null;
        try
        {
            FileInputStream fis=new FileInputStream("ObjectSaveToFile.txt");
            ois=new ObjectInputStream(fis);
            //System.out.println("Printing object 1");
            Student ob = (Student) ois.readObject();
            System.out.println("Printing object");
            ob.getDetails();
        }catch(Exception e)
        {
            System.err.println("Error "+e+ " in function readMyObject");
        }
        finally
        {
            try
            {
                ois.close();
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        
    }
    void writeMyObject() {
        Student ob=new Student("1","Bisvarup Mukherjee","address 1");
        ObjectOutputStream oos=null;
        try
        {
            oos=new ObjectOutputStream(new FileOutputStream("ObjectSaveToFile.txt"));
            oos.writeObject(ob);
        }
        catch(Exception ex)
        {
            System.out.println("Error is  "+ex);
        }
        finally
        {
            try
            {
                oos.close();
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }
}
