package org.Commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AppendCommand {
    private boolean is_validfilename(String filename) {
        String invalidChars = "\\/:*?\"<>|";
        for (char ch : invalidChars.toCharArray()) {
            if (filename.indexOf(ch) != -1) {
                return false;
            }
        }
        return true;
    }
    private boolean iscontainingTXT(String filename) {
        return filename.endsWith(".txt");
    }
    public void append(String text,String file) {
        if (!is_validfilename(file) || file.isEmpty()){
            System.out.println("Invalid file name please rewrite a valid one");
            return;
        }
        else if (!iscontainingTXT(file)){
            System.out.println("Invalid file name please write the file name ends with .txt");
            return;
        }
        String currentDir = System.getProperty("user.dir");
        File tar_file = new File(currentDir, file);
        try(FileWriter writer = new FileWriter(tar_file,true)){
            writer.write(text);
            System.out.println("Text is Appended successfully in " + tar_file.getName());
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
