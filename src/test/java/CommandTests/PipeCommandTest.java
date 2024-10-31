package CommandTests;

import org.Commands.PipeCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PipeCommandTest {
    private PipeCommand pipecommand;
    private File testpath;
    private File originalPath;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    PipeCommandTest(){
        testpath = new File("C:\\Users\\77\\Desktop\\testt\\");
        System.setProperty("user.dir", testpath.getAbsolutePath());
        pipecommand = new PipeCommand();
    }
    @BeforeEach
    void setUp() {// redirect the output to outstream to help with testing
        System.setOut(new PrintStream(outContent));
        originalPath = new File(System.getProperty("user.dir"));
    }

    @AfterEach
    void tearDown() { // restore the original output after each test
        System.setOut(originalOut);
        System.setProperty("user.dir", originalPath.getAbsolutePath());
    }
    @Test
    void splitcommandlinetest(){
        String commandline = "ls -r";
        String[] parts = pipecommand.SplitingCommandLine(commandline);
        assertEquals("ls",parts[0]);
        assertEquals("-r",parts[1]);
    }
    @Test
    void pipeTwocommands() throws IOException {
        String Commandline1 = "ls";
        String Commandline2 = "cat";
        pipecommand.pipe(Commandline1,Commandline2);
        String[] array = testpath.list();
        StringBuilder expectedoutput = new StringBuilder();
        if (array != null) {
            for (String file : array) {
                if (file.endsWith(".txt")) {
                    String content = new String(Files.readAllBytes(Paths.get(testpath.getAbsolutePath(), file)));
                    expectedoutput.append(content).append("\n");
                }
            }
        }
        assertTrue(outContent.toString().contains(expectedoutput.toString()));
    }
    @Test
    void invalidcommand() throws IOException {
        String Commandline1 = "mv";
        String Commandline2 = "cat";
        pipecommand.pipe(Commandline1,Commandline2);
        String Expectedtext = "invalid command";
        assertTrue(outContent.toString().contains(Expectedtext));
    }
    @Test
    void testSortingcommand(){
        List<String> list = new ArrayList<>();
        list.add("b_second");
        list.add("e_fifth");
        list.add("a_first");
        list.add("d_fourth");
        list.add("c_third");
        List<String> sortedlist = pipecommand.sort(list);
        List<String> expectedlist = new ArrayList<>(Arrays.asList("a_first","b_second","c_third","d_fourth","e_fifth"));
        assertEquals(expectedlist,sortedlist);
    }
    @Test
    void testuniqcommand(){
        List<String> list = new ArrayList<>();
        list.add("element1");
        list.add("element1");
        list.add("element1");
        list.add("element1");
        list.add("element2");
        list.add("element2");
        list.add("element3");
        list.add("element4");
        List<String> uniquelist = pipecommand.uniq(list);
        List<String> expectedlist = new ArrayList<>(Arrays.asList("element1","element2","element3","element4"));
        assertEquals(expectedlist, uniquelist);
    }
}