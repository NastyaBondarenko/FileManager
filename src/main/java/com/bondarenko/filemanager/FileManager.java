package com.bondarenko.filemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {

    public static int countFiles(String path) {
        int countFiles = 0;
        File dir = new File(path);
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                countFiles += countFiles(file.getAbsolutePath());
            } else {
                countFiles++;
            }
        }
        return countFiles;
    }

    public static int countDirs(String path) {
        int countDirs = 0;
        File dir = new File(path);
        for (File element : dir.listFiles()) {
            if (element.isDirectory()) {
                countDirs++;
                countDirs += countDirs(element.toString());
            }
        }
        return countDirs;
    }

    public static void copy(String from, String to) {
        File fileToCopy = new File(from);
        File fileCopied = new File(to);
        copyFileContent(fileToCopy, fileCopied);
    }

    public void clean(File file) {
        File[] list = file.listFiles();
        checkFileIsExisting(list);
        for (File files : list) {
            if (files.isDirectory()) {
                File newDir = new File(file + File.separator + files.getName());
                clean(newDir);
            }
            files.delete();
        }
        file.delete();
    }

    public void move(String from, String to) {
        File pathFrom = new File(from);
        File pathTo = new File(to, pathFrom.getName());
        pathFrom.renameTo(pathTo);
    }

    private static void copyFileContent(File from, File to) {
        if (from.isDirectory()) {
            if (!to.exists()) {
                to.mkdir();
            }
            String[] files = from.list();
            for (int i = 0; i < files.length; i++) {
                copyFileContent(new File(from, files[i]), new File(to, files[i]));
            }
        } else {
            try (FileInputStream fileInputStream = new FileInputStream(from);
                 FileOutputStream fileOutputStream = new FileOutputStream(to)) {
                int length;
                byte[] buffer = new byte[1024];
                while ((length = fileInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }
            } catch (IOException exception) {
                throw new RuntimeException("Cant read or write FileContent", exception);
            }
        }
    }

    private static void checkFileIsExisting(File[] file) {
        if (file == null) {
            throw new NullPointerException("File is not exist");
        }
    }
}


