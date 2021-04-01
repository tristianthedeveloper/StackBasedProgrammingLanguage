package com.tristian.stacklanguage.file;

import com.tristian.stacklanguage.commands.CommandParser;
import com.tristian.stacklanguage.commands.ICommand;
import com.tristian.stacklanguage.section.Section;
import com.tristian.stacklanguage.util.FileUtil;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StackFileInterpreter {

//    line 25 needs commands = Arrays.asList('stuff', 31 for sections.


    /**
     * The default program.
     */
    public static List<String> defaultStackFile;

    private List<String[]> commands;

    File f;

    public static StackFileInterpreter start() {

        StackFileInterpreter interpreter = new StackFileInterpreter();
        interpreter.commands = new LinkedList<>();

        return interpreter;
    }


    static {
        defaultStackFile = new ArrayList<>();
        try {
            defaultStackFile = FileUtil.readAll(new FileInputStream(StackFileInterpreter.class.getResource("stackfiledefault.txt").getFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param cmd  The command to add
     * @param args The arguments of said command.
     */
    public void addCommand(String cmd, String[] args) {
        this.commands.add(args);
        System.out.println("added cmd");
    }

    ;

    public File create() {
        long time = System.nanoTime();
        File tempFile = null;


        final String[] sectionName = new String[1];
        List<String> sectionCommands = new ArrayList<>();
        try {
            tempFile = new File("./Main.java");
            tempFile.createNewFile();
            FileWriter writer = new FileWriter(tempFile);
            int i = 0;
            for (String s : defaultStackFile) {

                writer.write(s);
                if (i == 25) { // where to insert thing


                    System.out.println(commands);
                    writer.write("this.commands = Arrays.asList(new String[] { ");
                    final boolean[] flag = {false};
                    commands.forEach((args) -> {
                        if (String.join(" ", args).equals(""))
                            return; // skip empty strings.
                        if ((sectionName[0] = Section.parseSectionName(Arrays.stream(args).collect(Collectors.joining(" ")))) != null) {
                            flag[0] = true; // stop, found a section!
                            return;
                        }
                        if (flag[0]) {
                            String stringedArgs = String.join(" ", args);
                            if (!"done".equals(stringedArgs)) {
                                System.out.println("adding cmd " + Arrays.stream(args).collect(Collectors.joining(" ")));
                                sectionCommands.add(stringedArgs); // add section command into section command listy thing
                            }
                        } else

                            try {
                                writer.write("\"" +
                                        Arrays.stream(args).collect(Collectors.joining(" ")) + "\",");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                    });
                    // end declaration with an empty string :shrug:
                    writer.write("\"\"});");
                    /*
                    writer.write("public void runInterpretedCode() {");
                    commands.forEach((cmd, args) -> {
                        System.out.println("args: " + Arrays.toString(args));
                        ;

                        String arrayCreation = "new String[] { " + Arrays.stream(args).map(e -> "\"" + e + "\"").collect(Collectors.joining(",")) + " }";

                        try {
                            // screw it!
                            if (args.length > 0 && !CommandParser.Commands.fromClass(cmd).special) {
                                writer.write("new " + cmd + "().run(Arrays.copyOfRange(" + arrayCreation + ", 1, " + args.length + "));");
                                return;
                            } else if (args.length > 0 && CommandParser.Commands.fromClass(cmd).special) {
                                writer.write("new " + cmd + "().run(" + arrayCreation + ");");
                                return;
                            }
                            writer.write("new " + cmd + "().run(Arrays.copyOfRange(" + arrayCreation + ", 0, 0));");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    writer.write("}");
                     */
                }
                if (i == 31) {
                    writer.write("Section section = new Section(\"" + sectionName[0] + "\");");
                    sectionCommands.forEach(x -> {
                        System.out.println("cmd adding: " + x);
                        try { // tell our compiled program to run our section.
                            writer.write("section.addCommand(\"" + x.replaceAll("\"", "") + "\")" + ";");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    writer.write("SectionStorage.sections.add(section);");
                }
                i++;
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Interpreted " + commands.size() + " commands in " + (System.nanoTime() - time) + " nanos.");
        return tempFile;
    }


    public void loadFromFile(File file) {
        long time = System.nanoTime();
        try {
            Scanner s = new Scanner(file);
            while (s.hasNextLine()) {

                String input = s.nextLine();
                System.out.println(input);
                String[] args = input.split(" "); // split it into arguments
                System.out.println(Arrays.toString(args));
                addCommand(args[0] + "", args);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("loaded file: " + file.getName() + " in " + (System.nanoTime() - time) + " nanos");

    }


}
