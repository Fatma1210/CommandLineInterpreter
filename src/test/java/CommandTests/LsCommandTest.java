package CommandTests;
import org.Commands.LsCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import static org.junit.jupiter.api.Assertions.*;

class LsCommandTest {
    private LsCommand lsCommand;
    private File workingDirectory;

    @BeforeEach
    void setUp() {
        // Initialize working directory and LsCommand instance
        workingDirectory = new File(System.getProperty("user.dir")); // Current directory
        lsCommand = new LsCommand(workingDirectory);
    }
    @Test
    void testMakeAbsoluteWithRelativePath() {
        String relativePath = "src";
        File absoluteFile = lsCommand.makeAbsolute(relativePath);
        assertTrue(absoluteFile.isAbsolute());
    }
    @Test
    void testMakeAbsoluteWithAbsolutePath() {
        String absolutePath = workingDirectory.getAbsolutePath() + File.separator + "src";
        File absoluteFile = lsCommand.makeAbsolute(absolutePath);
        assertEquals(absolutePath, absoluteFile.getAbsolutePath());
    }
    @Test
    void testLsWithNonExistingDirectory() {
        String nonExistentPath = "nonexistentfolder";
        assertThrows(NoSuchFileException.class, () -> lsCommand.ls(nonExistentPath, false, false));
    }
    @Test
    void testLsWithEmptyDirectory() throws IOException {
        // Create a temporary empty directory
        File tempDir = new File(workingDirectory, "emptyDir");
        tempDir.mkdir();
        // Initialize LsCommand with the empty directory
        LsCommand lsCommand = new LsCommand(tempDir);
        // Expect IllegalArgumentException to be thrown for empty directory
        assertThrows(IllegalArgumentException.class, () -> lsCommand.ls(tempDir.getAbsolutePath(), false, false));
        // Clean up by deleting the temporary directory after the test
        tempDir.delete();
    }
    @Test
    void testLsWithFileInsteadOfDirectory() throws IOException {
        // Create a temporary file
        File tempFile = new File(workingDirectory, "example.txt");
        tempFile.createNewFile();
        // Initialize LsCommand with the file
        LsCommand lsCommand = new LsCommand(tempFile);
        // Expect IllegalArgumentException to be thrown for file instead of directory
        assertThrows(IllegalArgumentException.class, () -> lsCommand.ls(tempFile.getAbsolutePath(), false, false));
        // Clean up by deleting the temporary file after the test
        tempFile.delete();
    }
    @Test
    void testLsWithExistingDirectory() {
        // Test listing files in the current directory
        assertDoesNotThrow(() -> lsCommand.ls(workingDirectory.getAbsolutePath(), false, false));
    }
    @Test
    void testLsWithShowHidden() {
        // Test with showing hidden files (assuming hidden files exist in the working directory)
        assertDoesNotThrow(() -> lsCommand.ls(workingDirectory.getAbsolutePath(), true, false));
    }
    @Test
    void testLsWithReverseOrder() {
        // Test with reverse order
        assertDoesNotThrow(() -> lsCommand.ls(workingDirectory.getAbsolutePath(), false, true));
    }
    @Test
    void testLsWithShowHiddenAndReverseOrderInExistingDirectory() {
        // Test with both hidden files and reverse order in an existing directory
        assertDoesNotThrow(() -> lsCommand.ls(workingDirectory.getAbsolutePath(), true, true));
    }
    @Test
    void testCurrentDirectoryIsEmpty() throws IOException {
        // Create a temporary empty directory
        File tempDir = new File(workingDirectory, "currentEmptyDir");
        tempDir.mkdir();
        // Initialize LsCommand with the empty directory
        LsCommand lsCommand = new LsCommand(tempDir);
        // Expect IllegalArgumentException to be thrown for empty directory
        assertThrows(IllegalArgumentException.class, () -> lsCommand.ls( false, false));
        // Clean up by deleting the temporary directory after the test
        tempDir.delete();
    }
    @Test
    void testCurrentDirectoryIsFile() throws IOException {
        // Create a temporary file
        File tempFile = new File(workingDirectory, "currentFile.txt");
        tempFile.createNewFile();
        // Initialize LsCommand with the working directory
        LsCommand lsCommand = new LsCommand(tempFile);
        // Expect IllegalArgumentException to be thrown when trying to list a file
        assertThrows(IllegalArgumentException.class, () -> lsCommand.ls(false, false));
        // Clean up by deleting the temporary file after the test
        tempFile.delete();
    }
    @Test
    void testLsInCurrentDirectory() {
        // Test ls in the current working directory without any path
        assertDoesNotThrow(() -> lsCommand.ls(false, false));
    }
    @Test
    void testLsCurrentDirectoryWithShowHidden() {
        // Test showing hidden files only in the current directory
        assertDoesNotThrow(() -> lsCommand.ls(true, false));
    }
    @Test
    void testLsCurrentDirectoryWithReverseOrder() {
        // Test reverse order only in the current directory
        assertDoesNotThrow(() -> lsCommand.ls(false,true));
    }
    @Test
    void testLsInCurrentDirectoryWithHiddenAndReverseOrder() {
        // Test ls in the current directory with both hidden files and reverse order enabled
        assertDoesNotThrow(() -> lsCommand.ls(true,true));
    }
}