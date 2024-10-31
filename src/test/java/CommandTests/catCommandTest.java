package CommandTests;
import org.Commands.catCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class catCommandTest {
    private catCommand catCommand;
    private File testpath;
    private File originalPath;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    catCommandTest(){
        testpath = new File("C:\\Users\\77\\Desktop\\testt\\");
        System.setProperty("user.dir", testpath.getAbsolutePath());
    }

    @BeforeEach
    void setUp() {// redirect the output to outstream to help with testing
        System.setOut(new PrintStream(outContent));
        // save the original path so we can restore it after each test
        originalPath = new File(System.getProperty("user.dir"));
    }

    @AfterEach
    void tearDown() { // restore the original output after each test
        System.setOut(originalOut);
        System.setProperty("user.dir", originalPath.getAbsolutePath());
    }

    @Test
    void Printallindirectory() throws IOException {
        List<String> list = new ArrayList<>();
        list.add("*");
        catCommand catCommand = new catCommand(testpath);
        catCommand.cat(list);
        StringBuilder expectedString = new StringBuilder();
        String[] files = testpath.list();
        for (String file : files) {
            if ( file.endsWith(".txt")) {
                String content = new String(Files.readAllBytes(Paths.get(testpath.getAbsolutePath(), file)));
                expectedString.append(content).append("\n");
            }
        }
        assertTrue(outContent.toString().contains(expectedString.toString()));
    }

    @Test
    void printspecificfiles() throws IOException {
        List<String> list = new ArrayList<>();
        list.add("input.txt");
        list.add("example.txt");
        catCommand = new catCommand(testpath);
        catCommand.cat(list);
        StringBuilder expectedString = new StringBuilder();
        for (String file : list) {
            if (file.endsWith(".txt")) {
                String content = new String(Files.readAllBytes(Paths.get(testpath.getAbsolutePath(), file)));
                expectedString.append(content).append("\n");
            }
        }
        assertTrue(outContent.toString().contains(expectedString.toString()));
    }

    @Test
    void catwithoutputcommand() throws IOException {
        List<String> list = new ArrayList<>();
        list.add("input.txt");
        list.add("example.txt");
        list.add(">");
        list.add("output.txt");
        catCommand = new catCommand(testpath);
        catCommand.cat(list);
        StringBuilder expectedString = new StringBuilder();
        int i=0;
        while (list.get(i) != ">"){
            if (list.get(i).endsWith(".txt")) {
                String content = new String(Files.readAllBytes(Paths.get(testpath.getAbsolutePath(), list.get(i))));
                expectedString.append(content).append("\n");
            }
            i++;
        }
        String overwritedfilecontent = Files.readString(Paths.get(testpath.getAbsolutePath(), list.getLast()));
        assertEquals(expectedString.toString(), overwritedfilecontent);
    }
    @Test
    void catwithappendcommand() throws IOException {
        List<String> list = new ArrayList<>();
        list.add("input.txt");
        list.add("example.txt");
        list.add(">>");
        list.add("output.txt");
        catCommand = new catCommand(testpath);
        catCommand.cat(list);
        StringBuilder expectedString = new StringBuilder();
        int i=0;
        while (list.get(i) != ">>"){
            if (list.get(i).endsWith(".txt")) {
                String content = new String(Files.readAllBytes(Paths.get(testpath.getAbsolutePath(), list.get(i))));
                expectedString.append(content).append("\n");
            }
            i++;
        }
        String filecontent = Files.readString(Paths.get(testpath.getAbsolutePath(), list.getLast()));
        assertTrue(filecontent.contains(expectedString.toString()));
    }
    @Test
    void emptylist() throws IOException {
        List<String> list = new ArrayList<>();
        catCommand = new catCommand(testpath);
        catCommand.cat(list);
        assertTrue(outContent.toString().contains("No files found"));
    }
    @Test
    void readNonexistentfile() throws IOException {
        List<String> list = new ArrayList<>();
        list.add("nonexist.txt");
        catCommand = new catCommand(testpath);
        catCommand.cat(list);
        assertTrue(outContent.toString().contains("Error when Reading files: "));
    }

}