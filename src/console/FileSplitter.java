package console;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileSplitter {
    public static void main(String[] args) throws IOException {
        System.out.println("Добро пожаловать в FileSplitter. Для того, чтобы разбить ваш текстовый файл " +
                "на составляющие, введите команду, соответствующую следующему шаблону:\n\n" +
                "split [-d] [-l num|-c num|-n num] [-o ofile] file\n\n" +
                "Опция -d отвечает за нумерацию файлов (опция активна - нумерация " +
                "в числовом формате; опция не активна - нумерация проводится в формате aa, ab,.. az, ba, bb,..)\n" +
                "Одна из трёх следующих опций отвечает за параметр разбиения:\n" +
                "-l num указывает размер выходных файлов в строчках (по умолчанию num = 100).\n" +
                "-c num указывает размер выходных файлов в символах\n" +
                "-n num указывает только кол-во выходных файлов, а размер при этом определяется автоматически\n" +
                "Опция -o ofile задаёт базовую часть имени выходного файла по следующему принципу:\n" +
                "1. опция неактивна - имя файла X;\n2. опция равна - (прочерку) - имя файла наследуется от входного;\n" +
                "3. опция равна ofile - имя файла ofile.\nПозиция file выделена под путь к входному файлу (или его имя,\n " +
                "если файл лежит в корневой папке программы).\n\n" +
                "Введите команду:");
        Scanner consoleInput = new Scanner(System.in);
        String parameter = consoleInput.nextLine();
        String[] parameters = parameter.split(" ");
        File origFile = new File(parameters[parameters.length - 1]);
        String origFilePath = parameters[parameters.length - 1];//Получение из исходной строки ссылки на файл
        String origFileName = origFile.getName().replace(".txt", "");
        int d;
        if (parameter.contains("-d")) d = 1; //Проверка наличия флага -d
        else d = 0;
        int n = 100;
        String newFileNameBase = "X";
        if (d == 1) {
            n = Integer.parseInt(parameters[3]);
            if (!parameter.contains(" -o ")) newFileNameBase = "X";
            else if (parameters[5].equals("-")) newFileNameBase = origFileName;
            else newFileNameBase = parameters[5];
        }
        if (d == 0) {
            n = Integer.parseInt(parameters[2]);
            if (!parameter.contains(" -o ")) newFileNameBase = "X";
            else if (parameters[4].equals("-")) newFileNameBase = origFileName;
            else newFileNameBase = parameters[4];
        }
        if (parameter.contains(" -l ") && d == 1) stringSplitterNumberName(origFilePath, n, newFileNameBase);
        if (parameter.contains(" -l ") && d == 0) stringSplitterLetterName(origFilePath, n, newFileNameBase);
        if (parameter.contains(" -c ") && d == 1) charSplitterNumberName(origFilePath, n, newFileNameBase);
        if (parameter.contains(" -c ") && d == 0) charSplitterLetterName(origFilePath, n, newFileNameBase);
        if (parameter.contains(" -n ") && d == 1) numOfFilesSplitterNumberName(origFilePath, n, newFileNameBase);
        if (parameter.contains(" -n ") && d == 0) numOfFilesSplitterLetterName(origFilePath, n, newFileNameBase);
    }

    public static void stringSplitterNumberName(String origFilePath, int stringsCountMax, String newFileBaseName) throws IOException {
        System.out.println("Введите путь для создания новых файлов:");
        Scanner in = new Scanner(System.in);
        String path = in.nextLine();
        int fileNum = 1;
        Scanner origFile = new Scanner(new File(origFilePath), UTF_8.name());
        while (origFile.hasNextLine()) {
            int stringsCount = 0;
            File newFile = new File(path + newFileBaseName + fileNum + ".txt");
            if (newFile.createNewFile()){
                PrintWriter writer = new PrintWriter(newFile);
                while (stringsCount < stringsCountMax && origFile.hasNextLine()) {
                    writer.println(origFile.nextLine());
                    stringsCount++;
                }
                writer.close();
            }
            else {
                System.out.println("Ошибка! Файл с заданным именем уже существует.");
                break;
            }
            fileNum++;
        }
        origFile.close();
        System.out.println("Работа завершена.");
    }

    public static void stringSplitterLetterName(String origFilePath, int stringsCountMax, String newFileBaseName) throws IOException {
        System.out.println("Введите путь для создания новых файлов:");
        Scanner in = new Scanner(System.in);
        String path = in.nextLine();
        String[] alphabet = "abcdefghijklmnopqrstuvwxyz".split("");
        int x = 0;
        int y = 0;
        Scanner origFile = new Scanner(new File(origFilePath), UTF_8.name());
        while (origFile.hasNextLine()) {
            int stringsCount = 0;
            File newFile = new File(path + newFileBaseName + alphabet[x] + alphabet[y] + ".txt");
            if (newFile.createNewFile()){
                PrintWriter writer = new PrintWriter(newFile);
                while (stringsCount < stringsCountMax && origFile.hasNextLine()) {
                    writer.println(origFile.nextLine());
                    stringsCount++;
                }
                writer.close();
            }
            else {
                System.out.println("Ошибка! Файл с заданным именем уже существует.");
                break;
            }
            if (y != 25) y++;
            else if (x != 25) {
                x++;
                y = 0;
            }
        }
        origFile.close();
        System.out.println("Работа завершена.");
    }

    public static void charSplitterNumberName(String origFilePath, int charsCountMax, String newFileBaseName) throws IOException {
        System.out.println("Введите путь для создания новых файлов:");
        Scanner in = new Scanner(System.in);
        String path = in.nextLine();
        int fileNum = 1;
        int charsCount = 0;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(origFilePath));
        int symbol = bufferedReader.read();
        File newFile = new File(path + newFileBaseName + fileNum + ".txt");
        if (newFile.createNewFile()) {
            PrintWriter writer = new PrintWriter(newFile);
            while (symbol != -1) {
                char c = (char) symbol;
                writer.print(c);
                charsCount++;
                if (charsCount == charsCountMax) {
                    writer.close();
                    fileNum++;
                    charsCount = 0;
                    newFile = new File(path + newFileBaseName + fileNum + ".txt");
                    if (newFile.createNewFile()) writer = new PrintWriter(newFile);
                    else System.out.println("Ошибка! Файл с заданным именем уже существует.");
                }
                symbol = bufferedReader.read();
            }
            writer.close();
        }
        else System.out.println("Ошибка! Файл с заданным именем уже существует.");
        System.out.println("Работа завершена.");
    }

    public static void charSplitterLetterName(String origFilePath, int charsCountMax, String newFileBaseName) throws IOException {
        System.out.println("Введите путь для создания новых файлов:");
        Scanner in = new Scanner(System.in);
        String path = in.nextLine();
        String[] alphabet = "abcdefghijklmnopqrstuvwxyz".split("");
        int x = 0;
        int y = 0;
        int charsCount = 0;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(origFilePath));
        int symbol = bufferedReader.read();
        File newFile = new File(path + newFileBaseName + alphabet[x] + alphabet[y] + ".txt");
        if (newFile.createNewFile()) {
            PrintWriter writer = new PrintWriter(newFile);
            while (symbol != -1) {
                char c = (char) symbol;
                writer.print(c);
                charsCount++;
                if (charsCount == charsCountMax) {
                    writer.close();
                    charsCount = 0;
                    if (y != 25) y++;
                    else if (x != 25) {
                        x++;
                        y = 0;
                    }
                    newFile = new File(path + newFileBaseName + alphabet[x] + alphabet[y] + ".txt");
                    if (newFile.createNewFile()) writer = new PrintWriter(newFile);
                    else System.out.println("Ошибка! Файл с заданным именем уже существует.");
                }
                symbol = bufferedReader.read();
            }
            writer.close();
        }
        else System.out.println("Ошибка! Файл с заданным именем уже существует.");
        System.out.println("Работа завершена.");
    }

    public static void numOfFilesSplitterNumberName(String origFilePath, int filesCountMax, String newFileBaseName) throws IOException{
        System.out.println("Введите путь для создания новых файлов:");
        Scanner in = new Scanner(System.in);
        String path = in.nextLine();
        int fileNum = 1;
        long charsCount = 0;
        File origFile = new File(origFilePath);
        System.out.println(origFile.length());
        long charsCountMax = origFile.length() / filesCountMax;
        System.out.println(charsCountMax);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(origFilePath));
        int symbol = bufferedReader.read();
        File newFile = new File(path + newFileBaseName + fileNum + ".txt");
        if (newFile.createNewFile()) {
            PrintWriter writer = new PrintWriter(newFile);
            while (symbol != -1) {
                char c = (char) symbol;
                writer.print(c);
                charsCount++;
                if (charsCount == charsCountMax) {
                    writer.close();
                    fileNum++;
                    charsCount = 0;
                    newFile = new File(path + newFileBaseName + fileNum + ".txt");
                    if (newFile.createNewFile()) writer = new PrintWriter(newFile);
                    else System.out.println("Ошибка! Файл с заданным именем уже существует.");
                }
                symbol = bufferedReader.read();
            }
            writer.close();
        }
        else System.out.println("Ошибка! Файл с заданным именем уже существует.");
        System.out.println("Работа завершена.");
    }

    public static void numOfFilesSplitterLetterName(String origFilePath, int filesCountMax, String newFileBaseName) throws IOException {
        System.out.println("Введите путь для создания новых файлов:");
        Scanner in = new Scanner(System.in);
        String path = in.nextLine();
        String[] alphabet = "abcdefghijklmnopqrstuvwxyz".split("");
        int x = 0;
        int y = 0;
        long charsCount = 0;
        File origFile = new File(origFilePath);
        System.out.println(origFile.length());
        long charsCountMax = origFile.length() / filesCountMax;
        System.out.println(charsCountMax);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(origFilePath));
        int symbol = bufferedReader.read();
        File newFile = new File(path + newFileBaseName + alphabet[x] + alphabet[y] + ".txt");
        if (newFile.createNewFile()) {
            PrintWriter writer = new PrintWriter(newFile);
            while (symbol != -1) {
                char c = (char) symbol;
                writer.print(c);
                charsCount++;
                if (charsCount == charsCountMax) {
                    writer.close();
                    if (y != 25) y++;
                    else if (x != 25) {
                        x++;
                        y = 0;
                    }
                    charsCount = 0;
                    newFile = new File(path + newFileBaseName + alphabet[x] + alphabet[y] + ".txt");
                    if (newFile.createNewFile()) writer = new PrintWriter(newFile);
                    else System.out.println("Ошибка! Файл с заданным именем уже существует.");
                }
                symbol = bufferedReader.read();
            }
            writer.close();
        }
        else System.out.println("Ошибка! Файл с заданным именем уже существует.");
        System.out.println("Работа завершена.");
    }
}
