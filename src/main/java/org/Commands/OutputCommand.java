package org.Commands;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;


public class OutputCommand {
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

    public void output(String text,String file) {
        if (!is_validfilename(file) || file.equals("")){
            System.out.println("Invalid file name please rewrite a valid one");
            return;
        }
        else if (!iscontainingTXT(file)){
            System.out.println("Invalid file name please write the file name ends with .txt");
            return;
        }
        else {
            String currentDir = System.getProperty("user.dir");
            File tar_file = new File(currentDir, file);
            try (FileWriter writer = new FileWriter(tar_file)) {
                writer.write(text);
                System.out.println("Text is inserted successfully in " + tar_file.getName());
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }
    }
}
