package org.Commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AppendCommand {
    public void append(String text,String file) {
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
