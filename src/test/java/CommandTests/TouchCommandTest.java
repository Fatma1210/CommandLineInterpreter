package CommandTests;

import org.Commands.TouchCommand;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
class TouchCommandTest {
    @Test
    void givenANewFileName_theFileShouldBeCreated() throws Exception { // this test passes a  new file name to
        // the touch method and asserts that this file got created
        String fileName = "testFile.txt";
        TouchCommand touchCommand = new TouchCommand();
        touchCommand.touch(fileName);

        assertTrue(new File(fileName).exists());

        Files.delete(Path.of(fileName));
    }

    @Test
    void givenAnExistingFile_thenTimestampsShouldBeUpdated() throws Exception { //this test creates a new file first and stores the timestamp for this
        // file before and after
        // calling the touch method and comparing both timestamps
        String fileName = "testFile.txt";
        File testFile = new File(fileName);
        testFile.createNewFile();

        TouchCommand touchCommand = new TouchCommand();
        BasicFileAttributes attr = Files.readAttributes(testFile.toPath(), BasicFileAttributes.class);
        FileTime initialLastModifiedTime = attr.lastModifiedTime();
        FileTime initialLastAccessTime = attr.lastAccessTime();

        Thread.sleep(1000);

        touchCommand.touch(fileName);
        attr = Files.readAttributes(testFile.toPath(), BasicFileAttributes.class);
        FileTime updatedLastModifiedTime = attr.lastModifiedTime();
        FileTime updatedLastAccessTime = attr.lastAccessTime();

        assertTrue(updatedLastModifiedTime.toMillis() > initialLastModifiedTime.toMillis());
        assertTrue(updatedLastAccessTime.toMillis() > initialLastAccessTime.toMillis());


        Files.delete(testFile.toPath());
    }

    @Test
    void givenExistingFileWith_a_option_thenAccessTimeShouldBeUpdated() throws Exception { // this test ensures that when using
        // the -a option only the access time is updated and not the modification time
        TouchCommand touchCommand = new TouchCommand();
        String filePath = "testFile.txt";
        new File(filePath).createNewFile();

        BasicFileAttributes attr = Files.readAttributes(Path.of(filePath), BasicFileAttributes.class);
        FileTime initialAccessTime = attr.lastAccessTime();
        FileTime initialmodificationTime = attr.lastModifiedTime();

        Thread.sleep(1000);
        touchCommand.touch("-a " + filePath);

        attr = Files.readAttributes(Path.of(filePath), BasicFileAttributes.class);
        FileTime updatedAccessTime = attr.lastAccessTime();
        FileTime updatedmodificationTime = attr.lastModifiedTime();

        assertTrue(updatedAccessTime.toMillis() > initialAccessTime.toMillis());
        assertEquals(updatedmodificationTime.toMillis(), initialmodificationTime.toMillis());
        Files.delete(Path.of(filePath));
    }
    @Test
    void givenExistingFileWith_m_option_thenAccessTimeShouldBeUpdated() throws Exception { // this test ensures that when using
        // the -m option only the modification time is updated and not the access time
        TouchCommand touchCommand = new TouchCommand();
        String filePath = "testFile.txt";
        new File(filePath).createNewFile();

        BasicFileAttributes attr = Files.readAttributes(Path.of(filePath), BasicFileAttributes.class);
        FileTime initialAccessTime = attr.lastAccessTime();
        FileTime initialmodificationTime = attr.lastModifiedTime();

        Thread.sleep(1000);
        touchCommand.touch("-m " + filePath);

        attr = Files.readAttributes(Path.of(filePath), BasicFileAttributes.class);
        FileTime updatedAccessTime = attr.lastAccessTime();
        FileTime updatedmodificationTime = attr.lastModifiedTime();

        assertTrue(updatedmodificationTime.toMillis()> initialmodificationTime.toMillis());
        assertEquals(updatedAccessTime.toMillis() ,initialAccessTime.toMillis());
        Files.delete(Path.of(filePath));
    }
    @Test
    void givenNonExistentFileWith_c_option_thenFileShouldNotBeCreated() throws Exception {// this test ensures that when using -c option a new file is not created
        TouchCommand touchCommand = new TouchCommand();
        String filePath = "nonExistentFile.txt";

        touchCommand.touch("-c " + filePath);

        assertFalse(new File(filePath).exists());
    }
    @Test
    void givenExistingFileWithCustomTimeOption_whenTouchCalled_thenTimestampsAreUpdated() throws Exception { // this test ensures that when the user enters some custom time using -t
        // option the access time and modification time get updated to that custom time
        TouchCommand touchCommand = new TouchCommand();
        String filePath = "testFile.txt";
        new File(filePath).createNewFile();
        String customTime = "200408310000";

        touchCommand.touch("-t" + customTime + " " + filePath);

        long expectedTime = new SimpleDateFormat("yyyyMMddHHmm").parse(customTime).getTime();
        BasicFileAttributes attr = Files.readAttributes(Path.of(filePath), BasicFileAttributes.class);
        FileTime updatedModificationTime = attr.lastModifiedTime();
        FileTime updatedAccessTime = attr.lastAccessTime();
        assertEquals(expectedTime, updatedModificationTime.toMillis());
        assertEquals(expectedTime, updatedAccessTime.toMillis());

        Files.delete(Path.of(filePath));
    }


}


