package com.leeson.components;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by lisen on 2018/1/3.
 *
 * @author lisen < 4533548588@qq.com >
 */

public class JavaDemo {
    public static void main(String[] a){
        File newFile = new File("d:\\newContact.txt");

        File fileDir = new File("d:\\contacts");
        if (fileDir.exists()){
            if (fileDir.isDirectory()){
                File[] files = fileDir.listFiles();
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    if (file.getName().contains(".txt")){
                        wrap(file,newFile);
                    }
                }
            }
        }
    }
    public static void wrap(File file,File newFile){

        BufferedReader reader = null;

        BufferedWriter writer = null;
        try {
            FileReader fileReader = new FileReader(file);
//            InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file),"UTF-8");
            reader = new BufferedReader(fileReader);

            FileWriter fileWriter = new FileWriter(newFile,true);
            writer = new BufferedWriter(fileWriter);

            String lineStr = null;
            while ( (lineStr = reader.readLine()) != null){
                writer.newLine();
                writer.write(lineStr);
                writer.flush();
            }
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (reader != null){
                    reader.close();
                }
                if (writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
