
import javazr.io.File;
import java.util.Scanner;

/**
 * This code gets the files and folders in a particular directory
 * and it does it recursively
 * @author Bisvarup Mukherjee
 * @date 12/07/2017
 * 
 */
public class FileOperation1 {
    static void getFileList(File f)
    {
        /**
         * check if the file is a file or a directory
         */
        if(f.isFile())
        {
            System.out.println(""+f);
        }
        /**
         * what to do if it is a folder
         */
        else 
        {
            String list[]=f.list();
            for(String lf:list)
            {
                File tmpFile=new File(f.getAbsolutePath(),lf);
                if(tmpFile.isDirectory() && !tmpFile.isHidden())
                System.out.println(tmpFile+" "+tmpFile.isDirectory());
                {
                    System.out.println("Files in "+lf+" are ");
                    try
                    {
                        getFileList(tmpFile);    
                    }catch(Exception e)
                    {
                        System.out.println(e);
                    }
                    
                    System.out.println("End of listing");
                }
                else
                {
                    
                    System.out.println(tmpFile);
                }
            }    
        }
        
    }
    /**
     * 
     * @param args does not require any arguments
     */
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        //String path="H:\\Arnab\\Photoshoop\\Adobe Photoshop CS6 13.0.1 Final  Multilanguage (cracked dll) [ChingLiu]\\Photoshop_CS6_13_0_1_update";
		String path="H:\\";
        File f=new File(path);
        getFileList(f);
    }
}
