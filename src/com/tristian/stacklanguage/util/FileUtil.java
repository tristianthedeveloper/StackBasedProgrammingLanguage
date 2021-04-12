package com.tristian.stacklanguage.util;

import com.tristian.stacklanguage.file.StackFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtil {

    /**
     * Check if a file exists and if it's of the right extension to interpret.
     *
     * @param file The file to check.
     */
    public static void checkFile(File file) {
        Stream<String> extensions = Arrays.stream(StackFile.extensions);
        if (file.exists()) {
            if (extensions.anyMatch(file.getName()::endsWith)) {
                return;
            }
        }
        throw new IllegalArgumentException("Invalid File: File either doesn't exist or has the wrong extension. Valid extensions: " +
                extensions.map(e -> "." + e).collect(Collectors.joining(", ")));
    }

    /**
     * @param fis Input stream to read.
     * @return All the lines from a file input stream, with each entry being one line.
     */
    public static List<String> readAll(FileInputStream fis) {
        InputStreamReader reader = new InputStreamReader(fis);
        Scanner scanner = new Scanner(reader);
        List<String> returnList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            returnList.add(scanner.nextLine());
        }
        return returnList;
    }


}
