package org.Commands;

import java.io.File;
import java.util.Scanner;

public class Rmcommand {
    public void rm(String[] args) {
        boolean v = false;
        boolean i = false;
        boolean r = false;

        for (String arg : args) {
            if (arg.equals("-v")) {
                v = true;
            } else if (arg.equals("-i")) {
                i = true;
            } else if (arg.equals("-r")) {
                r = true;
            } else {
                deleteFileOrDirectory(arg, v, i, r);
            }
        }
    }

    private void deleteFileOrDirectory(String path, boolean v, boolean i, boolean r) {
        File file = new File(path);

        if (!file.exists()) {
            System.out.println("File or directory does not exist: " + path);
            return;
        }

        if (file.isDirectory()) {
            if (r) {
                deleteDirectory(file, v, i);
            } else {
                System.out.println("Error: Cannot remove directory without -r option: " );
            }
        } else {
            handleFileDeletion(file, v, i);
        }
    }

    private void handleFileDeletion(File file, boolean verbose, boolean interactive) {
        if (interactive) {
            System.out.print("Are you sure you want to delete the file '" + file.getName() + "'? (y/n): ");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("no")) {
                System.out.println("Deletion cancelled for: " + file.getName());
                return;
            }
        }

        boolean deleted = file.delete();
        if (deleted) {
            // Print the deletion confirmation regardless if in interactive mode
            if (interactive || verbose) {
                System.out.println("Successfully deleted file: " + file.getName());
            }
        } else {
            System.out.println("Failed to delete file: " + file.getAbsolutePath());
        }
    }


    private void deleteDirectory(File directory, boolean verbose, boolean interactive) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file, verbose, interactive);
                } else {
                    handleFileDeletion(file, verbose, interactive);
                }
            }
        }

        boolean deletedDir = directory.delete();
        if (deletedDir) {
            if (verbose) {
                System.out.println("Deleted directory: " + directory.getAbsolutePath());
            } else {
                System.out.println("Successfully deleted directory: " + directory.getName());
            }
        } else {
            System.out.println("Failed to delete directory: " + directory.getAbsolutePath());
        }
    }
}
