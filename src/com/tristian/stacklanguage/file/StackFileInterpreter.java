package com.tristian.stacklanguage.file;

import com.tristian.stacklanguage.commands.CommandParser;
import com.tristian.stacklanguage.commands.ICommand;
import com.tristian.stacklanguage.util.FileUtil;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StackFileInterpreter {


    /**
     * The default program.
     */
    public static List<String> defaultStackFile;

    private HashMap<String, String[]> commands;

    File f;

    public static StackFileInterpreter start() {

        StackFileInterpreter interpreter = new StackFileInterpreter();
        interpreter.commands = new LinkedHashMap<>();

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
        this.commands.put(cmd, args);
        System.out.println("added cmd");
    }

    ;

    public File create() {
        long time = System.nanoTime();
        File tempFile = null;
        try {
            tempFile = new File("./Main.java");
            tempFile.createNewFile();
            FileWriter writer = new FileWriter(tempFile);
            int i = 0;
            for (String s : defaultStackFile) {
                writer.write(s);
                if (i == 23) { // where to insert thing


                    System.out.println(commands);
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
                CommandParser.Commands command;
                if ((command = CommandParser.Commands.valueOfThing(args[0])) != null) {
                    try {
                        // add the class name!
                        addCommand(command.commandClass.getSimpleName(), args);
                        // screw it!
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("loaded file: " + file.getName() + " in " + (System.nanoTime() - time) + " nanos");

    }


}
