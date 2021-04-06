package com.tristian.stacklanguage;

import com.tristian.stacklanguage.commands.CommandParser;
import com.tristian.stacklanguage.file.StackFileInterpreter;
import com.tristian.stacklanguage.interpreter.JarOutStream;
import com.tristian.stacklanguage.label.Label;
import com.tristian.stacklanguage.register.Accumulator;
import com.tristian.stacklanguage.register.ExtraAccumulator;
import com.tristian.stacklanguage.var.Variable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.tristian.stacklanguage.label.Label.parseLabel;

public class Main {

    private static Main instance;

    private LStack LStack;

    public static void main(String[] args) {
        args = new String[]{
                "--interpret",
                "./programs/array_reversal.sasm"
        };
        System.out.println(Arrays.toString(args));

        if (args.length > 1) {
            if (args[0].equalsIgnoreCase("--interpret")) {
                JarOutStream.loadPaths();
                String fileName = args[1]; // should be
                StackFileInterpreter file = StackFileInterpreter.start();
                file.loadFromFile(new File(fileName));
                try {
                    File compiled = new JarOutStream().compile(file.create());
                    new JarOutStream().run(compiled);
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        new Main().load();
    }

    private void load() {
        new Accumulator();
        new ExtraAccumulator();
        instance = this;
        this.LStack = LStack.setUpStack();
        // for testing labels
//        Label l = parseLabel(new File("./programs/loop_with_jne_example.sasm"));
//        System.out.println(l.toString());
//        l.run();

        init();

    }

    private void init() {

        Scanner mainStdin = new Scanner(System.in);

        String next;
        while (!(next = mainStdin.nextLine()).equalsIgnoreCase("exit()")) {
            // try to parse a variable from the input and add it.
            if (Variable.VariableParser.tryAddVariable(next) != null) {
                continue;
            }

            CommandParser.runCommand(next);

        }
    }

    public static Main getInstance() {
        return instance;
    }

    public LStack getLStack() {
        return LStack;
    }

}

