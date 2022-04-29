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

public class FileManagerTest {
    FileManager fileManager = new FileManager();

    @BeforeEach
    public void before() throws IOException {//прибрать exception
        new File("testDirectory").mkdir();
        new File("testDirectory/src").mkdir();
        new File("testDirectory/src/dir1").mkdir();
        new File("testDirectory/src/dir1/dir2").mkdir();
        new File("testDirectory2").mkdir();
        FileOutputStream fileOutputStream = new FileOutputStream("testDirectory/src/dir1/dir2/file1.txt");//отправка даних в файл на диске
        fileOutputStream.write("file1".getBytes());//записує єдиний байт у вихідний потік
        fileOutputStream.close();//закриває вихідний потім, записувать більш не можна
    }

    @AfterEach
    public void after() {
        fileManager.clean(new File("testDirectory"));
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
    public void testCopy() {
        fileManager.copy("C:\\Users\\ihor_PC\\Desktop\\testnew\\папка1", "C:\\Users\\ihor_PC\\Desktop\\testnew\\папка2");
    }

    @Test
    public void testMoveFilesOrDirs() throws IOException {
        fileManager.move("C:\\Users\\ihor_PC\\Desktop\\testnew\\moveFrom", "C:\\Users\\ihor_PC\\Desktop\\testnew\\moveTo");
    }

}


