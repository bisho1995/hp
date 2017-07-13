
import java.io.FileInputStream;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *This class reads data from a file and displays it on the console
 * it reads 1MB of data from file and prints it on the console
 * in case of read size less than 1MB it creates a byte array 
 * with just the size of the remaining characters 
 * @author user
 */
public class FileRead1 {
    private String path;
    private int byteSize;
    
    /**
     *This constructor initializes the path to the path variable 
     * @param path this accepts the path of the file which is to be read and contents displayed on the screen 
     */
    FileRead1(String path)
    {
        this.path=path; //initializing the path
        this.byteSize=1024*1024; //size of the byte array its 1MB
    }
    
    /**
     * This function reads the data from the file and prints it to the console
     * 
     */
    private void readData()
    {
        System.out.println("Attempting to read data from "+this.path);
        FileInputStream fis=null;
        try
        {
            fis=new FileInputStream(this.path);
            int size=0;
            if(fis.available()<this.byteSize)
            {
                size=fis.available();
            }
            else
            {
                size=this.byteSize;
            }
            byte buf[]=new byte[size];
            
            while(fis.read(buf)!=-1)
            {
                //System.out.println(new String(buf));
                printByteArray(buf);
            }
            
        }
        catch(Exception e)
        {
            System.out.println("System encountered an error "+e);
        }
        finally
        {
            if(fis!=null)
            {
                try
                {
                    fis.close();
                }
                catch(Exception ex)
                {
                    System.out.println("Error in closing the file "+ex);
                }
            }
        }
    }
    
    /**
     * This method prints the byte array character by character to the console
     * @param b this is the byte array which is to be passed to the function so its converted to String
     */
    public void printByteArray(byte b[])
    {
        for(int i=0;i<b.length;i++)
        {
            System.out.print((char)b[i]);
        }
        //System.out.println();
    }
    
    
    /**
     * This is where the execution begins, supply the path to the object and it works
     * @param args not needed
     */
    public static void main(String args[])
    {
        FileRead1 obj=new FileRead1("C:\\Users\\user\\Desktop\\hp\\day7\\JavaApplication1\\src\\FileOperation1.java");
        obj.readData();
    }
}
