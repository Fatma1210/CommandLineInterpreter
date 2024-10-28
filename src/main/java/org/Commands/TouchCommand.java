package org.Commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TouchCommand {
    public void touch(String args) {
        boolean updateAccessTime = true;
        boolean updateModificationTime = true;
        boolean createIfNotExists = true;
        String customTime = null;
        String fileName = null;

        // Split arguments by space
        String[] arguments = args.split(" ");
        Set<String> validOptions = new HashSet<>(Set.of("-a", "-m", "-c"));
        // Process arguments
        for (String arg : arguments) {
            if (arg.equals("-a")) {
                updateAccessTime = true;
                updateModificationTime = false;
            } else if (arg.equals("-m")) {
                updateModificationTime = true;
                updateAccessTime = false;
            } else if (arg.equals("-c")) {
                createIfNotExists = false;
            } else if (arg.startsWith("-t")) {
                customTime = arg.substring(2);
            } else {
                fileName = arg;
            }
        }

        for (String arg : arguments) {
            if (!validOptions.contains(arg) && !arg.startsWith("-t") && !arg.equals(fileName)) {
                System.out.println("invalid option '" + arg + "' will be ignored.");
            }
        }

        long time = System.currentTimeMillis();
        if (customTime != null) {
            time = parseCustomTime(customTime);
        }
        if (fileName == null) {
            System.out.println("Error: No file specified.");
            return;
        }

        File file = new File(fileName);


        if (!file.exists() && !createIfNotExists) {
            System.out.println("File does not exist and creation is disabled.");
            return;
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("File created: " + file.getAbsolutePath());
                return; // Exit early after creating the file
            } catch (IOException e) {
                System.out.println("An error occurred while creating the file: " + e.getMessage());
                return;
            }
        }

        try {
            BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            long lastModifiedTime = updateModificationTime ? time : attr.lastModifiedTime().toMillis();
            long lastAccessTime = updateAccessTime ? time : attr.lastAccessTime().toMillis();


            Files.setLastModifiedTime(file.toPath(), FileTime.fromMillis(lastModifiedTime));
            Files.setAttribute(file.toPath(), "lastAccessTime", FileTime.fromMillis(lastAccessTime));

            System.out.println("Timestamps updated for: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("An error occurred while updating timestamps: " + e.getMessage());
        }
    }

    private long parseCustomTime(String customTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        try {
            Date date = sdf.parse(customTime);
            return date.getTime();
        } catch (ParseException e) {
            System.out.println("Error parsing custom time: " + e.getMessage());
            return System.currentTimeMillis();
        }
    }
}
