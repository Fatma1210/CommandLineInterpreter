package CommandTests;
import org.Commands.OutputCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OutputCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    @BeforeEach
    void setUp() { // redirect the output to outstream to help with testing
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() { // restore the original output after each test
        System.setOut(originalOut);
    }

    @Test
    void writhingHello_worldintoThefile() throws IOException {
        OutputCommand outputCommand = new OutputCommand();
        String text = "Hello world";
        String filename = "output.txt";
        outputCommand.output(text,filename);
        File outputFile = new File(System.getProperty("user.dir"), filename);
        String fileContent = "";
        if (outputFile.exists()){
            fileContent = Files.readString(Paths.get(outputFile.getAbsolutePath()));
        }
        assertEquals(fileContent,text);
    }
    @Test
    void Writinginafilethatisnotexists() throws IOException {
        OutputCommand outputCommand = new OutputCommand();
        String text = "Hello world";
        String filename = "output.txt";
        File checkingfile = new File(System.getProperty("user.dir"), filename);
        boolean isFileExists = false;
        outputCommand.output(text,filename);
        isFileExists = checkingfile.exists();
        assertTrue(isFileExists);
    }
    @Test
    void Writininginvalidfilewithsymbols(){
        OutputCommand outputCommand = new OutputCommand();
        String text = "Hello world";
        String filename = "out*put.txt";
        outputCommand.output(text,filename);
        assertTrue(outContent.toString().contains("Invalid file name please rewrite a valid one"));
    }
    @Test
    void Writinginvalidfilewithmissingtheextension(){
        OutputCommand outputCommand = new OutputCommand();
        String text = "Hello world";
        String filename = "output";
        outputCommand.output(text,filename);
        assertTrue(outContent.toString().contains("Invalid file name please write the file name ends with .txt"));
    }
    @Test
    void emptyFilename(){
        OutputCommand outputCommand = new OutputCommand();
        String text = "Hello world";
        String filename = "";
        outputCommand.output(text,filename);
        assertTrue(outContent.toString().contains("Invalid file name please rewrite a valid one"));
    }
    @Test
    void validFilename(){
        OutputCommand outputCommand = new OutputCommand();
        String text = "Hello world";
        String filename = "output.txt";
        outputCommand.output(text,filename);
        assertTrue(outContent.toString().contains("Text is inserted successfully in "));
    }

}