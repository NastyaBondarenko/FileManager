package com.bondarenko.filemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {

    public static int countFiles(String path) {
        int count = 0;
        File dir = new File(path);
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                count += countFiles(file.getAbsolutePath());
            } else {
                count++;
            }
        }
        return count;
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

    public void copy(String from, String to) {
        File pathFrom = new File(from);
        File pathTo = new File(to, pathFrom.getName());
        try {
            if (pathFrom.isDirectory()) {
                pathTo.mkdirs();
                File[] files = pathFrom.listFiles();
                for (File file : files) {
                    copy(file.getAbsolutePath(), pathTo.getAbsolutePath());
                }
            } else if (pathFrom.isFile()) {
                copyFile(pathFrom, pathTo);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("File or directory is not found");
        }
    }

    public void copyFile(File pathFrom, File pathTo) throws IOException {
        int length = (int) pathFrom.length();
        byte[] buffer = new byte[length];
        if (length != 0) {
            FileInputStream fileInputStream = new FileInputStream(pathFrom);
            FileOutputStream fileOutputStream = new FileOutputStream(pathTo);
            try {
                fileInputStream.read(buffer);// чтение байтов из файла
                fileOutputStream.write(buffer);//запись байтов в файл.
            } catch (IOException exception) {
                throw new IllegalArgumentException();
            } finally {
                fileInputStream.close();
                fileOutputStream.close();
            }
        }
    }

    // Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться перемищение.
    public void move(String from, String to) {
        File pathFrom = new File(from);
        File pathTo = new File(to, pathFrom.getName());
        pathFrom.renameTo(pathTo);
    }

    public void clean(File file) {
        File[] list = file.listFiles();
        checkNotNull(list);
        for (File files : list) {
            if (files.isDirectory()) {
                File newDir = new File(file + File.separator + files.getName());
                clean(newDir);
            }
            files.delete();
        }
        file.delete();
    }

    private static void checkNotNull(File[] file) {
        if (file == null) {
            throw new NullPointerException("Don't have permission to use file or not exist file");
        }
    }
}

//принимает путь к папке, возвращает количество файлов в папке и всех подпапках по пути
//public static int countDirs(String path) - принимает путь к папке,
// возвращает количество папок в папке и всех подпапках по пути

//public static void copy(String from, String to) - метод по копированию папок и файлов.

//метод по перемещению папок и файлов.

