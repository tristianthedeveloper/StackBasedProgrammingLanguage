package com.tristian.stacklanguage.label;

import com.tristian.stacklanguage.Main;
import com.tristian.stacklanguage.commands.CommandParser;
import com.tristian.stacklanguage.section.Section;
import com.tristian.stacklanguage.var.Variable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

// literally just a section but inlined

/**
 * @see Section
 */
public class Label {


    public static List<Label> labels;
    private List<String> commands; // commands in this label
    private String name;

    public Label(String name) {
        this.name = name;
        this.commands = new ArrayList<>();
    }


    public static Label parseLabel(List<String> labelStuff) {
        return null; // todo
    }

    /**
     * LABEL SYNTAX:
     * NAME: {
     * <p>
     * }
     *
     * @param file File to try to parse.
     * @return
     */
    public static Label parseLabel(File file) {
        Label label = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            Scanner s = new Scanner(fileInputStream);
            String line;
            String name = "";
            List<String> commands = new LinkedList<>();
            while (s.hasNextLine() && !(line = s.nextLine()).equals("}")) {
                if ((line.matches("^([a-zA-Z]+[a-zA-Z0-9])+?: *+\\{ *+"))) { // go ahead have as many spaces as you want, freak.
                    System.out.println("name " + name);
                    name = line.split(":")[0];
                    continue;
                }
                if ("".equals(name))
                    continue; // rip
                if (line.matches(" *+}")) // end of label
                    break;
                commands.add(line.trim());
            }
            label = new Label(name);
            label.commands = commands;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return label;
    }


    public static void main(String[] args) {

        Label l = parseLabel(new File("./programs/loop_with_jne_example.sasm"));
        l.run();

    }

    public static Label fromName(String name) {
        // you know the deal. cAsE sEnSATiVE
        return labels.stream().filter(label -> label.name.equals(name)).findFirst().orElse(null);
    }



    public void run() {
        this.commands.forEach(x -> {
            if (Variable.VariableParser.tryAddVariable(x) != null) return;
            CommandParser.runCommand(x);
        });
    }

    public static List<Label> getLabels() {
        return labels;
    }

    public void addCommand(String command) {
        this.commands.add(command);
    }

    public String getName() {
        return name;


    }


    @Override
    public String toString() {
        return "Label@" + this.hashCode() + "{name=" + this.name + ",commands=" + this.commands;
    }


    static {
        labels = new ArrayList<>();
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
