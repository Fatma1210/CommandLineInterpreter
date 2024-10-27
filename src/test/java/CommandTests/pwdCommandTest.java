package CommandTests;

import org.Commands.PwdCommand;
import org.Commands.cdCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class pwdCommandTest {
    String initialDir = System.getProperty("user.dir") ;
    @Test
    void PrintTheCurrentDirectoryOfThisProject() throws IOException {
        PwdCommand PwdCommand = new PwdCommand() ;
        String result = PwdCommand.Pwd();
        Assertions.assertEquals("C:\\Users\\hp\\IdeaProjects\\CLI", result);
    }
    @Test
    void PrintTheDirectoryAfterGoingToParentDirectory()throws IOException {
        var cdCommand = new cdCommand();
        String result = cdCommand.cd("..");
        PwdCommand PwdCommand = new PwdCommand() ;
        result = PwdCommand.Pwd() ;
        Assertions.assertEquals("C:\\Users\\hp\\IdeaProjects", result);
        System.setProperty("user.dir" , initialDir) ;
    }
    @Test
    void PrintTheDirectoryAfterGoingToGrandParentDirectory()throws IOException {
        var cdCommand = new cdCommand();
        String result = cdCommand.cd("..\\..");
        PwdCommand PwdCommand = new PwdCommand() ;
        result = PwdCommand.Pwd() ;
        Assertions.assertEquals("C:\\Users\\hp", result);
        System.setProperty("user.dir" , initialDir) ;
    }

}
