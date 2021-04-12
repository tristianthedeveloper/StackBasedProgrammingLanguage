package com.tristian.stacklanguage.file;

import com.tristian.stacklanguage.label.Label;
import com.tristian.stacklanguage.section.Section;
import com.tristian.stacklanguage.util.FileUtil;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class StackFileInterpreter {

//    line 28 needs commands = Arrays.asList('stuff', 31 for sections.
// 47 for labels.

    /**
     * The default program.
     */
    public static List<String> defaultStackFile;

    private List<String[]> commands;

    private Label labelToAdd;

    private List<Section> sections = new LinkedList<>();

    public static StackFileInterpreter start() {

        StackFileInterpreter interpreter = new StackFileInterpreter();
        interpreter.commands = new LinkedList<>();
        interpreter.labelToAdd = null;

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
     * @param args The arguments of said command.
     */
    public void addCommand(String[] args) {
        this.commands.add(args);
        System.out.println("added cmd");
    }

    public File create() {
        long time = System.nanoTime();
        File tempFile = null;


        final String[] sectionName = new String[1];
        List<String> sectionCommands = new ArrayList<>();
        // clean this up, this looks horrific.

        try {
            tempFile = new File("./Main.java");
            tempFile.createNewFile();
            FileWriter writer = new FileWriter(tempFile);
            int i = 0;
            for (String s : defaultStackFile) {

                writer.write(s);
                if (i == 28) { // where to insert thing


                    System.out.println(commands);
                    writer.write("this.commands = Arrays.asList(new String[] { ");
                    final boolean[] flag = {false};
                    commands.forEach((args) -> {
                        if (String.join(" ", args).equals(""))
                            return; // skip empty strings.
                        if ((sectionName[0] = Section.parseSectionName(String.join(" ", args))) != null) {
                            flag[0] = true; // stop, found a section!
                            return;
                        }
                        if (flag[0]) {
                            String stringedArgs = String.join(" ", args);
                            if (!"done".equals(stringedArgs)) {
                                System.out.println("adding cmd " + String.join(" ", args));
                                sectionCommands.add(stringedArgs); // add section command into section command listy thing
                            }
                        } else

                            try {
                                writer.write("\"" +
                                        String.join(" ", args) + "\",");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                    });
                    // end declaration with an empty string :shrug:
                    writer.write("\"\"});");
                }
                if (i == 31) {

                    sections.forEach(section -> {
                        sectionName[0] = section.getInformation().getName();
                        try {
                            writer.write("Section " + sectionName[0] + " = new Section(\"" + sectionName[0] + "\");");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        section.getCommands().forEach(x -> {
                            System.out.println("cmd adding: " + x);
                            try { // tell our compiled program to run our section.
                                writer.write(sectionName[0] + ".addCommand(\"" + x.replaceAll("\"", "") + "\")" + ";");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        try {
                            writer.write("SectionStorage.sections.add(" + sectionName[0] + ");");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                }
                if (i == 46) {

                    if (labelToAdd == null)
                        continue;
                    List<String> commands = labelToAdd.getCommands();
                    writer.write("Label label = new Label(\"" + labelToAdd.getName() + "\");");
                    commands.forEach(command -> {
                        try {
                            writer.write("label.addCommand(\"" + command.replaceAll("[\"]", "\\\\\"") + "\");");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    writer.write("Label.labels.add(label);");
                }
                writer.write("\n");
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

            this.labelToAdd = Label.parseLabel(file);
            this.sections = Section.loadSectionsFromFile(file);
            System.out.println(this.labelToAdd);
            int a = this.labelToAdd.getCommands().size();

            boolean flag = false;
            boolean flag_2 = false;
            while (s.hasNextLine()) {
                String input = s.nextLine();

                if (this.sections.stream().anyMatch(ree -> input.startsWith("begin section " + ree.getInformation().getName()))) {
                    System.out.println("Skippin");
                    a = this.sections.stream().filter(ree -> input.startsWith("begin section " + ree.getInformation().getName())).findFirst().orElse(null).getCommands().size();
                    flag = true;
                }
                if (this.labelToAdd != null && input.startsWith(this.labelToAdd.getName() + ":") && !flag_2) {
                    flag = true;
                }
                if (flag) {
                    flag_2 = true;
                    flag = a-- != 0;
                    System.out.println("skipping");
                    continue;
                }
                if ("done".equals(input))
                    continue;
                System.out.println(input);

                String[] args = input.split(" "); // split it into arguments
                System.out.println(Arrays.toString(args));
                addCommand(args);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("loaded file: " + file.getName() + " in " + (System.nanoTime() - time) + " nanos");

    }


}
