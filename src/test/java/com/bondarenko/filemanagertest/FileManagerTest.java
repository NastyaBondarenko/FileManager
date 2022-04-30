package com.bondarenko.filemanagertest;

import com.bondarenko.filemanager.FileManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        fileOutputStream.write("file1".getBytes());
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
    @DisplayName("when Count Directories then Quantity Of Directories Returned")
    public void whenCountDirs_thenQuantityOfDirsReturned() {
        int expectedCount = 3;
        int actualCount = fileManager.countDirs("testDirectory");

        assertEquals(expectedCount, actualCount);
    }

    @Test
    @DisplayName("when Copy File then Duplicated File Created")
    public void whenCopyFile_thenDuplicatedFileCreated() {
        String from = "testDirectory/src/dir1/dir2";
        String to = "testDirectory2";

        assertEquals(1, fileManager.countFiles(from));
        assertEquals(0, fileManager.countFiles(to));

        fileManager.copy(from, to);

        assertEquals(1, fileManager.countFiles(from));
        assertEquals(1, fileManager.countFiles(to));
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
    @DisplayName("when Move FileContent then FileContent Is Moved")
    public void whenMoveFileContent_thenFileContentIsMoved() {
        File dir1 = new File("testDirectory/src");
        File dir2 = new File("testDirectory2");

        assertEquals(0, fileManager.countDirs(dir2.getAbsolutePath()));

        fileManager.move(dir1.getPath(), dir2.getPath());

        assertEquals(3, fileManager.countDirs(dir2.getAbsolutePath()));
    }
}


