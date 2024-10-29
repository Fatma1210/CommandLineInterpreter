package CommandTests;

import org.Commands.Rmcommand;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class RmcommandTest {

    @Test
    void givenNonExistentPath_thenPrintsErrorMessage() {
        Rmcommand rmCommand = new Rmcommand();
        String[] args = {"nonExistentFile.txt"};

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        rmCommand.rm(args);

        assertTrue(outputStreamCaptor.toString().contains("File or directory does not exist: nonExistentFile.txt"));
    }

    @Test
    void givenExistingFile_thenFileShouldBeDeleted() throws Exception { // this test ensures that a given file is deleted when using rm method
        Rmcommand rmCommand = new Rmcommand();
        String filePath = "testFile.txt";
        new File(filePath).createNewFile();

        rmCommand.rm(new String[]{filePath});

        assertFalse(new File(filePath).exists());

    }

    @Test
    void givenExistingDirectoryWithoutROption_thenAnErrorMessageIsPrinted() throws Exception { //this test ensures that an error message is printed to
        // the user if the user attempted to delete a directory without the r option
        Rmcommand rmCommand = new Rmcommand();
        String dirPath = "testDir";
        new File(dirPath).mkdir();

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        rmCommand.rm(new String[]{dirPath});

        assertTrue(outputStreamCaptor.toString().contains("Error: Cannot remove directory without -r option:"));
    }

    @Test
    void givenExistingDirectoryWith_R_Option_thenDirectoryShouldBeDeleted() throws Exception { //this test ensures that r option successfully deletes a directory
        Rmcommand rmCommand = new Rmcommand();
        String dirPath = "testDir";
        new File(dirPath).mkdir();
        new File(dirPath + "/file.txt").createNewFile();

        rmCommand.rm(new String[]{"-r", dirPath});

        assertFalse(new File(dirPath).exists());

    }

    @Test
    void givenExistingDirectoryWith_V_thenAllFilesAndDirectoryShouldBeDeleted() throws Exception { //this test ensures that when using the v option all
        // the files inside the directory will be listed as deleted
        Rmcommand rmCommand = new Rmcommand();
        String dirPath = "testDir";
        new File(dirPath).mkdir();
        String file1Path = dirPath + "/file1.txt";
        String file2Path = dirPath + "/file2.txt";
        new File(file1Path).createNewFile();
        new File(file2Path).createNewFile();

        rmCommand.rm(new String[]{"-r", "-v", dirPath});
        assertFalse(new File(file1Path).exists());
        assertFalse(new File(file2Path).exists());
        assertFalse(new File(dirPath).exists());

    }
    @Test
    void givenExistingFileWith_I_option_whenRmCalledAndUserConfirmsYes_thenFileShouldBeDeleted() throws Exception {
        Rmcommand rmCommand = new Rmcommand();
        String filePath = "testFile.txt";
        new File(filePath).createNewFile();

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        String simulatedInput = "y\n"; //simulated input to test if the user entered yes
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        rmCommand.rm(new String[]{"-i", filePath});


        assertFalse(new File(filePath).exists());

        // assert that the output contains the expected conformation message and  the deletion message
        assertTrue(outputStreamCaptor.toString().contains("Are you sure you want to delete the file '" + filePath + "'?"));
        assertTrue(outputStreamCaptor.toString().contains("Successfully deleted file: " + filePath));
    }
    @Test
    void givenExistingFileWith_I_option_whenRmCalledAndUserConfirmsNo_thenFileIsNotDeleted() throws Exception {
        Rmcommand rmCommand = new Rmcommand();
        String filePath = "testFile.txt";
        new File(filePath).createNewFile();

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        String simulatedInput = "n\n"; ////simulated input to test if the user entered no
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        rmCommand.rm(new String[]{"-i", filePath});
        assertTrue(new File(filePath).exists(), "File should not be deleted.");

        assertTrue(outputStreamCaptor.toString().contains("Are you sure you want to delete the file '" + filePath + "'?"));
        assertTrue(outputStreamCaptor.toString().contains("Deletion cancelled for: " + new File(filePath).getName()));
    }


}