package com.bondarenko.filemanagertest;

import com.bondarenko.filemanager.FileManager;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FileManagerTest {

    FileManager fileManager = new FileManager();

    @BeforeEach
    public void before() throws IOException {
        new File("testDirectory").mkdir();
        new File("testDirectory/src").mkdir();
        new File("testDirectory/src/dir1").mkdir();
        new File("testDirectory/src/dir1/dir2").mkdir();
        new File("testDirectory2").mkdir();
        FileOutputStream fileOutputStream = new FileOutputStream("testDirectory/src/dir1/dir2/file1.txt");
        fileOutputStream.write("Hello world".getBytes());
        fileOutputStream.close();
    }

    @AfterEach
    public void after() {
        fileManager.clean(new File("testDirectory"));
        fileManager.clean(new File("testDirectory2"));
    }

    @Test
    @DisplayName("when Count Files then Quantity Of Files Returned")
    public void whenCountFiles_thenQuantityOfFilesReturned() {
        int expectedCount = 1;
        int actualCount = fileManager.countFiles("testDirectory/src/dir1/dir2");

        assertEquals(expectedCount, actualCount);
    }

    @Test
    @DisplayName("when Count Files In Empty Dir then Quantity Of Files Is Zero")
    public void whenCountFilesInEmptyDir_thenQuantityOfFilesIsZero() {
        int expectedCount = 0;
        int actualCount = fileManager.countFiles("testDirectory2");

        assertEquals(expectedCount, actualCount);
    }

    @Test
    @DisplayName("when Count Files In Not Existing Dir then Null Pointer Exception Returned")
    public void whenCountFilesInNotExistingDir_thenNullPointerExceptionReturned() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            fileManager.countFiles("notExistingDir");
        });
    }

    @Test
    @DisplayName("when Count Dirs then Quantity Of Dirs Returned")
    public void whenCountDirs_thenQuantityOfDirsReturned() {
        int expectedCount = 3;
        int actualCount = fileManager.countDirs("testDirectory");

        assertEquals(expectedCount, actualCount);
    }

    @Test
    @DisplayName("when Count Not Existing Dirs then Null Pointer Exception Returned")
    public void whenCountNotExistingDirs_thenNullPointerExceptionReturned() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            fileManager.countDirs("notExistingDir");
        });
    }

    @Test
    @DisplayName("when Copy File then The Same Content Created")
    public void whenCopyFile_thenTheSameFileContentCreated() {
        File fileWhichCopy = new File("testDirectory/src/dir1/dir2/file1.txt");
        String from = "testDirectory/src/dir1/dir2";
        String to = "testDirectory2";

        String expectedContent = fileManager.readFile(fileWhichCopy.getAbsolutePath());

        fileManager.copy(from, to);
        File copiedFile = fileManager.getFile(to);

        String actualContent = fileManager.readFile(copiedFile.getAbsolutePath());

        assertEquals(expectedContent, actualContent);
        assertEquals("Hello world", actualContent);
    }

    @Test
    @DisplayName("when Copy File then Quantity Of Files In Direction Dir Increased")
    public void whenCopyFile_thenQuantityOfFiles_InDirectionDirIncreased() {
        String from = "testDirectory/src/dir1/dir2";
        String to = "testDirectory2";

        assertEquals(1, fileManager.countFiles(from));
        assertEquals(0, fileManager.countFiles(to));

        fileManager.copy(from, to);

        assertEquals(1, fileManager.countFiles(from));
        assertEquals(1, fileManager.countFiles(to));
    }

    @Test
    @DisplayName("when Move File then FileContent Is Moved")
    public void whenMoveFile_thenFileContentIsMoved() {
        String from = "testDirectory/src/dir1/dir2/file1.txt";
        String to = "testDirectory2";

        String expectedContent = fileManager.readFile(from);
        fileManager.move(from, to);
        File movedFile = fileManager.getFile(to);

        String actualContent = fileManager.readFile(movedFile.getAbsolutePath());

        assertEquals(expectedContent, actualContent);
        assertEquals("Hello world", actualContent);
    }

    @Test
    @DisplayName("when Move Dir then Quantity Of Dirs In DirectionDir Increased")
    public void whenMoveDir_thenQuantityOfDirs_InDirectionDirIncreased() {
        File dir1 = new File("testDirectory/src");
        File dir2 = new File("testDirectory2");

        assertEquals(0, fileManager.countDirs(dir2.getAbsolutePath()));

        fileManager.move(dir1.getPath(), dir2.getPath());

        assertEquals(3, fileManager.countDirs(dir2.getAbsolutePath()));
    }

    @Test
    @DisplayName("when Move File then Quantity Of Files In DirectionDir become One More")
    public void whenMoveFile_thenQuantityOfFiles_InDirectionDir_becomeOneMore() {
        File dir1 = new File("testDirectory/src/dir1/dir2");
        File dir2 = new File("testDirectory2");

        assertEquals(0, fileManager.countFiles(dir2.getAbsolutePath()));

        fileManager.move(dir1.getPath(), dir2.getPath());

        assertEquals(1, fileManager.countFiles(dir2.getAbsolutePath()));
    }

    @Test
    @DisplayName("whe Clean FileContent then FileContent Is Not Exist")
    public void whenCleanFileContent_thenFileContentIsNotExist() {
        File dir = new File("testDirectory/src");

        assertEquals(2, fileManager.countDirs(dir.getAbsolutePath()));

        fileManager.clean(dir);

        assertFalse(dir.exists());
    }

    @Test
    @DisplayName("when Clean Not Existing File then NullPointerException Returned")
    public void whenCleanNotExistingFile_thenNullPointerException_Returned() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            File file = null;
            fileManager.clean(file);
        });
    }
}


