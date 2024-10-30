package CommandTests;
import org.Commands.MkdirCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MkdirCommandTest {
    private MkdirCommand mkdirCommand;
    private File testDir;
    private File workingDirectory;
    @BeforeEach
    void setUp() {
        workingDirectory = new File(System.getProperty("user.dir")); // Current directory
        // Set up a temporary working directory for tests
        testDir = new File(System.getProperty("java.io.tmpdir"), "mkdirTestDir");
        testDir.mkdir(); // Create the test directory
        mkdirCommand = new MkdirCommand(testDir); // Initialize MkdirCommand with the test directory
    }
    @AfterEach
    void tearDown() {
        // Clean up after each test
        File[] files = testDir.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete(); // Delete any created directories/files
            }
        }
        testDir.delete(); // Delete the test directory
    }
    @Test
    void makeAbsolute() {
        // Test relative path
        String relativePath = "subDir";
        File absolutePath = mkdirCommand.makeAbsolute(relativePath);
        assertEquals(new File(testDir, relativePath).getAbsolutePath(), absolutePath.getAbsolutePath());
        // Test absolute path
        String absoluteInputPath = workingDirectory.getAbsolutePath() + File.separator + "src";
        File absoluteFile = mkdirCommand.makeAbsolute(absoluteInputPath);
        assertEquals(absoluteInputPath, absoluteFile.getAbsolutePath());
    }
    @Test
    void mkdir_createsNewDirectory() throws IOException {
        String newDir = "newDir";
        mkdirCommand.mkdir(newDir); // Attempt to create the directory
        // Verify that the directory was created
        File createdDir = new File(testDir, newDir);
        assertTrue(createdDir.exists(), "The directory should exist after creation.");
    }
    @Test
    void mkdir_parentDirectoryDoesNotExist() {
        String nonExistingDir = "nonExistingDir/directory"; // Trying to create a directory with a non-existing parent
        assertThrows(NoSuchFileException.class, () -> {
            mkdirCommand.mkdir(nonExistingDir);
        }, "Expected NoSuchFileException when parent directory does not exist.");
    }
    @Test
    void mkdir_directoryAlreadyExists() throws IOException {
        String existingDir = "existingDir";
        mkdirCommand.mkdir(existingDir); // Create the directory first
        assertThrows(IOException.class, () -> {
            mkdirCommand.mkdir(existingDir); // Attempt to create it again
        }, "Expected IOException when the directory already exists.");
    }
    @Test
    void mkdir_creationFails() {
        // Attempting to create a directory in a location that cannot be created
        String invalidDir = ""; // Empty string as a directory name
        assertThrows(IOException.class, () -> {
            mkdirCommand.mkdir(invalidDir);
        }, "Expected IOException when trying to create an invalid directory.");
    }

}