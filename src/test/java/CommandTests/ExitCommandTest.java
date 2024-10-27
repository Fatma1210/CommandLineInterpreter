package CommandTests;

import org.Commands.ExitCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
public class ExitCommandTest {
    @Test
    void TestExit()  {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        ExitCommand exitCommand = new ExitCommand() ;
         exitCommand.Exit() ;
        String result = outputStream.toString().trim();
        System.setOut(System.out);
        Assertions.assertEquals("Existing Program", result);
    }
}
