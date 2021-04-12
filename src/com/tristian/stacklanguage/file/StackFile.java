package com.tristian.stacklanguage.file;

import com.tristian.stacklanguage.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class StackFile {

    /************
     * Valid file extensions *
     *************/
    public static final String[] extensions = {"sasm", "stack", "stck", "wtf"}; // get it because this language just triggers "wtf is this" whenever you look at it
    private File                 file;
    private FileInputStream reader;


    public StackFile(File file) {
        this.file = file;
        FileUtil.checkFile(file);
        try {
            this.reader = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public StackFile(String file) {
        this(new File(file));
    }


    public List<String> lines() {
        return FileUtil.readAll(reader);
    }

    public File getFile() {
        return file;
    }
}
