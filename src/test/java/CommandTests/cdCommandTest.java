package CommandTests;

import org.Commands.cdCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class cdCommandTest {
String initialDir = System.getProperty("user.dir") ;
    @Test
    void changeToParentDirectory() throws IOException {
        var cdCommand = new cdCommand();
        String result = cdCommand.cd("..");
        Assertions.assertEquals("C:\\Users\\hp\\IdeaProjects", result);
        System.setProperty("user.dir" , initialDir) ;
    }
    @Test
    void changeToGrandParentDirectory() throws IOException {
        var cdCommand = new cdCommand();
        String result = cdCommand.cd("..\\..");

        Assertions.assertEquals("C:\\Users\\hp", result);
        System.setProperty("user.dir" , initialDir) ;
    }
    @Test
    void changeToCurrentDirectory() throws IOException {
        var cdCommand = new cdCommand();
        String result = cdCommand.cd(".");

        Assertions.assertEquals("C:\\Users\\hp\\IdeaProjects\\CLI", result);
        System.setProperty("user.dir" , initialDir) ;
    }
    @Test
    void changeToNonExisitingDirectory() throws IOException {
        var cdCommand = new cdCommand();
        Assertions.assertThrows(NoSuchFileException.class, () -> {
            cdCommand.cd("source");
        });
        System.setProperty("user.dir" , initialDir) ;
    }
    @Test
    void changeToDirectoryInGrandParentDirectory() throws IOException {
        var cdCommand = new cdCommand();
        String result = cdCommand.cd("..\\..\\github");

        Assertions.assertEquals("C:\\Users\\hp\\github", result);
        System.setProperty("user.dir" , initialDir) ;
    }
    @Test
    void changeToWindowsDirectory() throws IOException {
        var cdCommand = new cdCommand();
        String result = cdCommand.cd("..\\..\\..\\..\\Windows");

        Assertions.assertEquals("C:\\Windows", result);
        System.setProperty("user.dir" , initialDir) ;
    }
    @Test
    void changeToDirectoryHomeDirectoryWithEmptyPath() throws IOException {
        var cdCommand = new cdCommand();
        String result = cdCommand.cd("");

        Assertions.assertEquals("C:\\Users\\hp", result);
        System.setProperty("user.dir" , initialDir) ;
    }
    @Test
    void changeToDirectoryHomeDirectoryWithtilde() throws IOException {
        var cdCommand = new cdCommand();
        String result = cdCommand.cd("~");

        Assertions.assertEquals("C:\\Users\\hp", result);
        System.setProperty("user.dir" , initialDir) ;
    }
}
