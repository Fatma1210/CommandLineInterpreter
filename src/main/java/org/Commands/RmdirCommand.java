package org.Commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class RmdirCommand {
    public void rmdir(String sourcePath) {
        File directory = new File(sourcePath);

        if (!directory.exists()) {
            System.out.println("Directory does not exist: " + directory.getAbsolutePath());
            return;
        }
        if (!directory.isDirectory()) {
            System.out.println("Path is not a directory: " + directory.getAbsolutePath());
            return;
        }

        deleteDirectory(directory);

        if (directory.exists()) {
            System.out.println("Failed to delete directory: " + directory.getAbsolutePath());
        } else {
            System.out.println("Directory deleted: " + directory.getAbsolutePath());
        }
    }

    private void deleteDirectory(File file) {
        if (file.isDirectory()) {
            for (File subfile : file.listFiles()) {
                if (subfile != null) {
                    deleteDirectory(subfile);
                }
            }
        }
        file.delete();
    }
}
