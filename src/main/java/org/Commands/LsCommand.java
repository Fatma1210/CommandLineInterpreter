package org.Commands;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class LsCommand {
    private File workingDirectory; // Stores the current working directory

    public LsCommand(File workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    // Converts a relative path to an absolute path
    public File makeAbsolute(String sourcePath) {
        File f = new File(sourcePath);
        if (!f.isAbsolute()) {
            f = new File(workingDirectory.getAbsolutePath(), sourcePath);
        }
        return f.getAbsoluteFile();
    }

    // Lists all files in a specified directory, optionally showing hidden files and in reverse order
    public void ls(String sourcePath, boolean showHidden, boolean reverseOrder) throws NoSuchFileException {
        File f = makeAbsolute(sourcePath);
        if (!f.exists()) {
            throw new NoSuchFileException(f.getAbsolutePath(), null, "does not exist.");
        }
        if (!f.isDirectory()) {
            throw new IllegalArgumentException(f.getAbsolutePath() + " is not a directory.");
        }
        String[] arr = f.list();
        if (arr == null || arr.length == 0) { // Check if the directory is empty
            throw new IllegalArgumentException("The directory is empty.");
        }
        else{
            // Filter hidden files if showHidden is false
            List<String> fileList = new ArrayList<>();
            for (String fileName : arr) {
                if (showHidden || !fileName.startsWith(".")) {
                    fileList.add(fileName);
                }
            }
            // Sort in reverse order if specified
            if (reverseOrder) {
                Collections.reverse(fileList);
            }
            // Print the files
            for (String fileName : fileList) {
                System.out.println(fileName);
            }
        }
    }

    // Lists all files in the current working directory, optionally showing hidden files and in reverse order
    public void ls(boolean showHidden, boolean reverseOrder) {
        if (!workingDirectory.isDirectory()) {
            throw new IllegalArgumentException(workingDirectory.getAbsolutePath() + " is not a directory.");
        }
        String[] arr = workingDirectory.list();
        // Check if the directory is empty
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("The directory is empty.");
        }
        List<String> fileList = new ArrayList<>();
        // Filter hidden files if showHidden is false
        for (String fileName : arr) {
            if (showHidden || !fileName.startsWith(".")) { // Show hidden files if specified
                fileList.add(fileName);
            }
        }
        // Sort in reverse order if specified
        if (reverseOrder) {
            Collections.reverse(fileList);
        }
        // Print the files
        for (String fileName : fileList) {
            System.out.println(fileName);
        }
    }
}