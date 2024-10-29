package org.Commands;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
public class MvCommand {
    public void mv(String sourcePath, String destinationPath) {
        File sourceFile = new File(sourcePath);
        File destinationFile = new File(destinationPath);

        if (!sourceFile.exists()) {
            System.out.println("Error: Source file does not exist.");
            return;
        }

        try {
            if (destinationFile.isDirectory()) {

                File targetFile = new File(destinationFile, sourceFile.getName());
                Files.move(sourceFile.toPath(), targetFile.toPath());
                System.out.println("File moved to directory: " + targetFile.getAbsolutePath());
            } else {

                Files.move(sourceFile.toPath(), destinationFile.toPath());
                System.out.println("File renamed to: " + destinationFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("An error occurred : " + e.getMessage());
        }
    }
}
