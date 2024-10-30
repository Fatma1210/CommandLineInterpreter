package org.Commands;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;


public class OutputCommand {
    public void output(String text,String file) {
        String currentDir = System.getProperty("user.dir");
        File tar_file = new File(currentDir, file);
        try(FileWriter writer = new FileWriter(tar_file)) {
            writer.write(text);
            System.out.println("Text is inserted successfully in " + tar_file.getName());
        }
         catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
