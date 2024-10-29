package CommandTests;

import org.Commands.MvCommand;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
class MvCommandTest {

    @Test
    void givenNonExistentSourceFile_AnErrorMessageIsPrinted() { // this test ensures that if a non-existent source file path
        // is passed the user gets an error message
        MvCommand mvCommand = new MvCommand();
        String nonExistentFilePath = "nonExistentFile.txt";
        String destinationPath = "SomeDestination.txt";

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        mvCommand.mv(nonExistentFilePath, destinationPath);

        assertTrue(outputStreamCaptor.toString().contains("Error: Source file does not exist."));
    }
    @Test
    void givenExistingFileWIthA_DirectoryPath_thenFileShouldBeMoved() throws Exception { // this test ensures that if a valid directory path is passed to the mv
        // method it moves the file to the new directory passed
        MvCommand mvCommand = new MvCommand();
        String sourceFilePath = "testFile.txt";
        String destinationDirPath = "testDir";

        new File(sourceFilePath).createNewFile();
        new File(destinationDirPath).mkdir();

        mvCommand.mv(sourceFilePath, destinationDirPath);
        assertTrue(new File(destinationDirPath, "testFile.txt").exists());

        Files.delete(Path.of(destinationDirPath, "testFile.txt"));
        Files.delete(Path.of(destinationDirPath));
    }
    @Test
    void givenExistingFileWithANonDirectoryPath_thenFileShouldBeRenamed() throws Exception { // this test ensures that if the second parameter passed to the mv method is not
        // a valid directory then the file is renamed to this passed parameter
        MvCommand mvCommand = new MvCommand();
        String sourceFilePath = "testFile.txt";
        String renamedFilePath = "renamedFile.txt";
        new File(sourceFilePath).createNewFile();
        mvCommand.mv(sourceFilePath, renamedFilePath);
        assertFalse(new File(sourceFilePath).exists());
        File renamedFile = new File(renamedFilePath);
        assertTrue(renamedFile.exists());
        Files.delete(Path.of(renamedFilePath));
    }
}