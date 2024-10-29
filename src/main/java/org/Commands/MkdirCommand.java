package org.Commands;
import java.io.File;
import java.nio.file.NoSuchFileException;
import java.io.IOException;

public class MkdirCommand {
    private File workingDirectory; // Stores the current working directory

    public MkdirCommand(File workingDirectory) {
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

    //creates a new directory(or directories) with the given name(s) in a given directory
    public void mkdir(String newDir) throws NoSuchFileException, IOException {
        File f = makeAbsolute(newDir);
        // Check if the parent directory exists
        if (!f.getParentFile().exists()) {
            throw new NoSuchFileException(newDir, null, "does not exist.");
        }
        // Check if the directory already exists
        if (f.exists()) {
            throw new IOException("Directory " + newDir + " already exists.");
        }
        // Attempt to create the directory
        boolean created = f.mkdir();
        if (!created) {
            throw new IOException("Cannot create directory " + newDir + ".");
        }
    }
}