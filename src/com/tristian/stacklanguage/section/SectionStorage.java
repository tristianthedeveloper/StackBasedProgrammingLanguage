package com.tristian.stacklanguage.section;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class SectionStorage {


    public static CopyOnWriteArrayList<Section> sections;




    public static Section getSectionByName(String name) {
//        cASe SenSiTive
        return sections.stream().filter(e -> e.getInformation().getName().equals(name)).findFirst().orElse(null);
    }



    static {
        sections = new CopyOnWriteArrayList<>();
    }

    public static void addSection(Section section) {
        sections.add(section);
    }
}
