package com.tristian.stacklanguage.section;

import com.tristian.stacklanguage.commands.CommandParser;
import com.tristian.stacklanguage.var.Variable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Section {


    private SectionInformation information;
    private CopyOnWriteArrayList<String> commands;

    public Section(SectionInformation information) {
        this.information = information;
        this.commands = new CopyOnWriteArrayList<>();
    }
    public Section(String name) {
        this(() -> name);
    }

    /**
     * Run all of the commands stored in the commands list.
     */
    public void execute() {
        for (String next : commands) {
            if (Variable.VariableParser.tryAddVariable(next) != null) {
                continue;
            }
            CommandParser.runCommand(next);
        }
    }

//    TODO make a functio nthat returns a list of sections from one file. this method is just for testing
    public static Section loadSectionFromFile(File file) throws FileNotFoundException {
        Section section;
        String name = "";
        Scanner s = new Scanner(file);
        CopyOnWriteArrayList<String> commandsFound = new CopyOnWriteArrayList<>();
        while (s.hasNextLine()) {
            String line = s.nextLine();
            if ("".equals(line))
                continue;
            if (!name.equals("") && line.replaceAll(" ", "").equals("done"))
                break;
            if (line.startsWith("begin section ")) {
                 // start of section pog
                String[] split = line.split("begin section ");
                name = split[1].replace(":", "");
                continue;
            }
            commandsFound.add(line);
        }
        section = new Section(name);
        section.commands = commandsFound;
        SectionStorage.addSection(section);
        return section;
    }

    public SectionInformation getInformation() {
        return this.information;
    }

    public void addCommand(String command) {
        this.commands.add(command);
    }

    public CopyOnWriteArrayList<String> getCommands() {
        return commands;
    }

    public static String parseSectionName(String line) {
        if (line.startsWith("begin section ")) {
            // start of section pog
            String[] split = line.split("begin section ");
            System.out.println("split: " + Arrays.toString(split));
            System.out.println(split[1].replace(":", ""));
            return split[1].replace(":", "");
        }
        return null;
    }


//    TODO scope variables
    public static void main(String[] args) {

        try {
            System.out.println(loadSectionFromFile(new File("./section.sasm")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Section{" +
                "name=" + information.getName() +
                ", commands=" + commands +
                '}';
    }
}
