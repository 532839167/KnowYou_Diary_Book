package com.diarybook.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.text.Document;

/**
 * diary functions: write and read
 */

public class Diary {

    public static void addDiary(String pathname, String title, String txt) {
        // pathname是以用户ID命名的文件夹
        File dirfile = new File(pathname);
        BufferedWriter bufw = null;
        // mkdir
        dirfile.mkdirs();

        // create diary file
        File file = new File(dirfile, title + ".txt");
        try {
            // write
            bufw = new BufferedWriter(new FileWriter(file, true));
            bufw.write(txt);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

            if (bufw != null) {
                try {
                    bufw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public static void read(File file, Document doc) {

        // read file, and write its content
        try (BufferedReader bufr = new BufferedReader(new FileReader(file));) {
            String txt = null;
            // get line separator, Linux和Windows下的 line separator是不一样的
            String line = System.getProperty("line.separator");
            while ((txt = bufr.readLine()) != null) {

                doc.insertString(doc.getLength(), txt + line, null);

            }

        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

}
