/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author user
 */
public class AudioMerger {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String file1="C:\\Users\\user\\Desktop\\hp\\day9\\JavaApplication4\\file1.mp3";
        String file2="C:\\Users\\user\\Desktop\\hp\\day9\\JavaApplication4\\file2.mp3";
        
        /**
         * read from file 1 append to file 2
         */
        
        FileInputStream fis=new FileInputStream(file1);
        FileOutputStream fos=new FileOutputStream(file2,true);
        
        byte[] b=new byte[fis.available()];
        fis.read(b);
        fos.write(b);
        /**
         * alternative
         * fos.write(fis.read());
         */
    }
}
