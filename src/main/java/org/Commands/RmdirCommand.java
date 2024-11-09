package org.Commands;

import java.io.File;

public class RmdirCommand {
    public boolean rmdir(String sourcePath) {
        File directory = new File(sourcePath);

        if (!directory.exists()) {
            System.out.println("Directory does not exist: " + directory.getAbsolutePath());
            return false;
        }
        if (!directory.isDirectory()) {
            System.out.println("Path is not a directory: " + directory.getAbsolutePath());
            return false;
        }

        boolean success = deleteEmptyDirectory(directory);

        if (success) {
            System.out.println("Directory deleted: " + directory.getAbsolutePath());
        } else {
            System.out.println("Directory is not empty");
        }
        return success;
    }

    private boolean deleteEmptyDirectory(File directory) {

        File[] files = directory.listFiles();
        if (files != null && files.length > 0) {

            return false;
        }

        return directory.delete();
    }
}
