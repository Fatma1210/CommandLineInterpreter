package CommandTests;

import org.Commands.RmdirCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
class RmdirCommandTest {
    private RmdirCommand rmdirCommand = new RmdirCommand();
  @Test
    void GivenDirectoryShouldBeDeleted(@TempDir Path tempDir) { // this test creates a directory and passes it as an input to the
      // rmdir function and asserts that th directory
      // existence equals false after applying the rmdir function
        File testDirectory = tempDir.toFile();

        assertTrue(testDirectory.exists() && testDirectory.isDirectory());

        rmdirCommand.rmdir(testDirectory.getAbsolutePath());

        assertFalse(testDirectory.exists());
    }

    @Test
    void givenNonExistentDirectory_whenRmdirCalled_thenPrintsDirectoryDoesNotExist() { // this test captures the output printed
      // to the console in the case of deleting a non_existing
      // directory and compare it with the expected result which is an error message to the user : Directory does not exist:
        String nonExistentDirPath = "nonExistentDir";

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));     rmdirCommand.rmdir(nonExistentDirPath);

        assertTrue(outputStreamCaptor.toString().trim().contains("Directory does not exist:"));

    }
    @Test
    void givenFilePath_whenRmdirCalled_thenPrintsPathIsNotDirectory() throws IOException { //this test creates a text file (any non-directory file)
      // and passes it as an input to the rmdir file and compares
      // the string captured from the output to the expected error message which is :Path is not a directory:
        File tempFile = File.createTempFile("testFile", ".txt");
        String filePath = tempFile.getAbsolutePath();

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        rmdirCommand.rmdir(filePath);

        assertTrue(outputStreamCaptor.toString().trim().contains("Path is not a directory:"));

        tempFile.delete();
    }
    @Test
    void givenNonEmptyDirectory_thenDirectoryShouldNotBeDeleted(@TempDir Path tempDir) throws IOException { // this test creates a directory that contains a file (non-empty directory)
      // and attempts to delete it

        File nonEmptyDir = tempDir.toFile();
        new File(nonEmptyDir, "file.txt").createNewFile();
        rmdirCommand.rmdir(nonEmptyDir.getAbsolutePath());
        assertTrue(nonEmptyDir.exists());
    }

}